package frc.team1836.robot.subsystems;

import static frc.team1836.robot.Constants.DRIVE;
import static frc.team1836.robot.Constants.Hardware;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1836.robot.util.MkMath;
import frc.team1836.robot.util.Util;
import frc.team1836.robot.util.drivers.MkCANTalon;
import frc.team1836.robot.util.drivers.MkGyro;
import frc.team1836.robot.util.logging.ReflectingCSVWriter;
import frc.team1836.robot.util.loops.Loop;
import frc.team1836.robot.util.loops.Looper;
import frc.team1836.robot.util.state.DriveSignal;
import frc.team1836.robot.util.state.TrajectoryStatus;
import frc.team254.lib.trajectory.Path;
import frc.team254.lib.trajectory.PathFollower;
import frc.team254.lib.trajectory.Trajectory.Segment;
import java.util.Arrays;

public class Drive extends Subsystem {

	private static Drive mInstance = new Drive();
	private final MkCANTalon leftfwdtalon, leftbacktalon, rightfwdtalon, rightbacktalon;
	private DriveDebugOutput mDebug = new DriveDebugOutput();
	private ReflectingCSVWriter<DriveDebugOutput> mCSVWriter;
	private MkGyro navX;
	private DriveControlState mDriveControlState;
	private PathFollower mPathFollower;
	private double leftSetpoint = 0.0;
	private double rightSetpoint = 0.0;
	private TrajectoryStatus leftStatus;
	private TrajectoryStatus rightStatus;

	private Drive() {
		navX = new MkGyro(new AHRS(SPI.Port.kMXP));
		leftfwdtalon = new MkCANTalon(Hardware.LEFT_FWD_TALON_ID);
		leftbacktalon = new MkCANTalon(Hardware.LEFT_BACK_TALON_ID);
		rightbacktalon = new MkCANTalon(Hardware.RIGHT_BACK_TALON_ID);
		rightfwdtalon = new MkCANTalon(Hardware.RIGHT_FWD_TALON_ID);

		leftfwdtalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		leftfwdtalon.setF(DRIVE.DRIVE_F);
		leftfwdtalon.setP(DRIVE.DRIVE_P);
		leftfwdtalon.setI(DRIVE.DRIVE_I);
		leftfwdtalon.setD(DRIVE.DRIVE_D);
		leftfwdtalon.setInverted(true);

		rightfwdtalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightfwdtalon.setF(DRIVE.DRIVE_F);
		rightfwdtalon.setP(DRIVE.DRIVE_P);
		rightfwdtalon.setI(DRIVE.DRIVE_I);
		rightfwdtalon.setD(DRIVE.DRIVE_D);

		mCSVWriter = new ReflectingCSVWriter<>("/home/lvuser/DRIVE-LOGS.csv", DriveDebugOutput.class);

		leftfwdtalon.changeControlMode(TalonControlMode.Speed);
		rightfwdtalon.changeControlMode(TalonControlMode.Speed);
		leftbacktalon.changeControlMode(TalonControlMode.Follower);
		rightbacktalon.changeControlMode(TalonControlMode.Follower);
		leftbacktalon.set(Hardware.LEFT_FWD_TALON_ID);
		rightbacktalon.set(Hardware.RIGHT_FWD_TALON_ID);
		mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
		mDebug.leftPosition = 0;
		mDebug.rightVelocity = 0;
		mDebug.leftVelocity = 0;
		leftStatus = new TrajectoryStatus(new Segment(0, 0, 0, 0, 0, 0, 0, 0), 0, 0, 0, 0);
		rightStatus = new TrajectoryStatus(new Segment(0, 0, 0, 0, 0, 0, 0, 0), 0, 0, 0, 0);
	}

	public static Drive getInstance() {
		return mInstance;
	}

	private static boolean usesTalonVelocityControl(DriveControlState state) {
		return ((state == DriveControlState.VELOCITY_SETPOINT) || (state
				== DriveControlState.PATH_FOLLOWING));
	}

	@Override
	public void writeToLog() {
		mCSVWriter.write();
	}

	@Override
	public void outputToSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder Position", leftfwdtalon.getPosition());
		SmartDashboard.putNumber("Right Encoder Position", rightfwdtalon.getPosition());

		SmartDashboard.putNumber("NavX Yaw", navX.getFullYaw());
		SmartDashboard.putNumber("Left PercentVBus",
				leftfwdtalon.getOutputVoltage() / leftfwdtalon.getBusVoltage());
		SmartDashboard.putNumber("Right PercentVBus",
				rightfwdtalon.getOutputVoltage() / rightfwdtalon.getBusVoltage());

		if (mDriveControlState == DriveControlState.PATH_FOLLOWING
				|| mDriveControlState == DriveControlState.VELOCITY_SETPOINT) {
			SmartDashboard.putNumber("Left Encoder Velocity", leftfwdtalon.getSpeed());
			SmartDashboard.putNumber("Right Encoder Velocity", rightfwdtalon.getSpeed());
			SmartDashboard.putNumber("Left Encoder Talon Error", leftfwdtalon.getError());
			SmartDashboard.putNumber("Right Encoder Talon Error", rightfwdtalon.getError());
			SmartDashboard.putNumber("Left Encoder Talon Setpoint", leftSetpoint);
			SmartDashboard.putNumber("Right Encoder Talon Setpoint", rightSetpoint);
		}
		if (mDriveControlState == DriveControlState.PATH_FOLLOWING) {
			SmartDashboard.putNumber("Left Desired Velocity",
					MkMath.normalAbsoluteAngleDegrees(leftStatus.getSeg().heading));
			SmartDashboard.putNumber("Left Desired Velocity", leftStatus.getSeg().vel);
			SmartDashboard.putNumber("Left Desired Position", leftStatus.getSeg().pos);
			SmartDashboard.putNumber("Left Position Error", leftStatus.getPosError());
			SmartDashboard.putNumber("Left Desired Velocity Error", leftStatus.getVelError());
			SmartDashboard.putNumber("Heading Error", leftStatus.getAngError());

			SmartDashboard.putNumber("Right Desired Velocity", leftStatus.getSeg().vel);
			SmartDashboard.putNumber("Right Desired Position", leftStatus.getSeg().pos);
			SmartDashboard.putNumber("Right Position Error", leftStatus.getPosError());
			SmartDashboard.putNumber("Right Desired Velocity Error", leftStatus.getVelError());
		}
	}

	public void stop() {
		setOpenLoop(DriveSignal.NEUTRAL);
	}

	public void zeroSensors() {
		resetEncoders();
		navX.zeroYaw();
	}

	public void registerEnabledLoops(Looper enabledLooper) {
		Loop mLoop = new Loop() {
			@Override
			public void onStart(double timestamp) {
				synchronized (Drive.this) {
					setOpenLoop(DriveSignal.NEUTRAL);
					setVelocitySetpoint(0, 0);
					zeroSensors();
				}

			}

			@Override
			public void onLoop(double timestamp) {

				synchronized (Drive.this) {
					mDebug.leftOutput = leftfwdtalon.getOutputVoltage() / leftfwdtalon.getBusVoltage();
					mDebug.rightOutput = rightfwdtalon.getOutputVoltage() / rightfwdtalon.getBusVoltage();
					mDebug.rightPosition = rightfwdtalon.getPosition();
					mDebug.leftPosition = leftfwdtalon.getPosition();
					mDebug.leftVelocity = leftfwdtalon.getSpeed();
					mDebug.rightVelocity = rightfwdtalon.getSpeed();
					mDebug.leftSetpoint = leftfwdtalon.getSetpoint();
					mDebug.rightSetpoint = rightfwdtalon.getSetpoint();
					mDebug.timestamp = timestamp;
					mDebug.controlMode = mDriveControlState.toString();
					mDebug.heading = navX.getFullYaw();
					switch (mDriveControlState) {
						case OPEN_LOOP:
							zeroTrajectoryStatus();
							return;
						case VELOCITY_SETPOINT:
							zeroTrajectoryStatus();
							return;
						case PATH_FOLLOWING:
							updatePathFollower(timestamp);
							mDebug.leftDesiredPos = leftStatus.getSeg().pos;
							mDebug.leftDesiredVel = leftStatus.getSeg().vel;
							mDebug.rightDesiredPos = rightStatus.getSeg().pos;
							mDebug.rightDesiredVel = rightStatus.getSeg().vel;
							mDebug.desiredHeading = leftStatus.getSeg().heading;
							mDebug.headingError = leftStatus.getAngError();
							mDebug.leftVelError = leftStatus.getVelError();
							mDebug.leftPosError = leftStatus.getPosError();
							mDebug.rightVelError = rightStatus.getVelError();
							mDebug.rightPosError = rightStatus.getPosError();
							mDebug.desiredX = (leftStatus.getSeg().x + rightStatus.getSeg().x) / 2;
							mDebug.desiredY = (leftStatus.getSeg().y + rightStatus.getSeg().y) / 2;
							return;
						default:
							System.out.println("Unexpected drive control state: " + mDriveControlState);
							break;
					}
					mCSVWriter.add(mDebug);
				}

			}

			@Override
			public void onStop(double timestamp) {
				stop();
				mCSVWriter.flush();
			}
		};
		enabledLooper.register(mLoop);
	}

	private void zeroTrajectoryStatus() {
		mDebug.leftDesiredPos = 0;
		mDebug.leftDesiredVel = 0;
		mDebug.rightDesiredPos = 0;
		mDebug.rightDesiredVel = 0;
		mDebug.desiredHeading = 0;
		mDebug.headingError = 0;
		mDebug.leftVelError = 0;
		mDebug.leftPosError = 0;
		mDebug.rightVelError = 0;
		mDebug.rightPosError = 0;
		mDebug.desiredX = 0;
		mDebug.desiredY = 0;
	}

	public synchronized void setOpenLoop(DriveSignal signal) {
		if (mDriveControlState != DriveControlState.OPEN_LOOP) {
			leftfwdtalon.changeControlMode(TalonControlMode.PercentVbus);
			rightfwdtalon.changeControlMode(TalonControlMode.PercentVbus);
			mDriveControlState = DriveControlState.OPEN_LOOP;
		}
		leftfwdtalon.set(signal.getLeft());
		rightfwdtalon.set(signal.getRight());
	}

	private synchronized void setVelocitySetpoint(double left_inches_per_sec,
			double right_inches_per_sec) {
		configureTalonsForSpeedControl();
		mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
		updateVelocitySetpoint(left_inches_per_sec, right_inches_per_sec);
	}

	private synchronized void resetEncoders() {
		leftfwdtalon.setEncPosition(0);
		leftfwdtalon.setPosition(0);
		rightfwdtalon.setPosition(0);
		rightfwdtalon.setEncPosition(0);
		leftbacktalon.setPosition(0);
		leftbacktalon.setPosition(0);
	}

	public synchronized void setVelocitySetpoint(DriveSignal sig) {
		configureTalonsForSpeedControl();
		mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
		updateVelocitySetpoint(sig.getLeft() * DRIVE.MAX_VEL, sig.getRight() * DRIVE.MAX_VEL);
	}

	private void configureTalonsForSpeedControl() {
		if (!usesTalonVelocityControl(mDriveControlState)) {
			leftfwdtalon.changeControlMode(TalonControlMode.Speed);
			rightfwdtalon.changeControlMode(TalonControlMode.Speed);
		}
	}

	private synchronized void updateVelocitySetpoint(double left_inches_per_sec,
			double right_inches_per_sec) {
		if (usesTalonVelocityControl(mDriveControlState)) {
			leftfwdtalon.set(MkMath.InchesPerSecToUnitsPer100Ms(left_inches_per_sec));
			rightfwdtalon.set(MkMath.InchesPerSecToUnitsPer100Ms(right_inches_per_sec));
			leftSetpoint = left_inches_per_sec;
			rightSetpoint = right_inches_per_sec;
		}
	}

	public boolean trajectoryFinished() {
		return mPathFollower.onTarget() & mPathFollower.getFinished();
	}

	public void setPathFollower(Path mPath) {
		mPathFollower = new PathFollower(mPath, DRIVE.DRIVE_FOLLOWER_DIST_TOL,
				DRIVE.DRIVE_FOLLOWER_ANG_TOL);
		mDriveControlState = DriveControlState.PATH_FOLLOWING;
	}

	private void updatePathFollower(double timestamp) {
		TrajectoryStatus leftUpdate = mPathFollower
				.getLeftVelocity(leftfwdtalon.getPosition(), leftfwdtalon.getSpeed(),
						Math.toRadians(navX.getFullYaw()));
		TrajectoryStatus rightUpdate = mPathFollower
				.getRightVelocity(rightfwdtalon.getPosition(), rightfwdtalon.getSpeed(),
						Math.toRadians(navX.getFullYaw()));
		updateVelocitySetpoint(leftUpdate.getOutput(), rightUpdate.getOutput());
		leftStatus = leftUpdate;
		rightStatus = rightUpdate;
	}

	public boolean checkSystem() {
		System.out.println("Testing DRIVE.---------------------------------");
		final double kCurrentThres = 0.5;
		final double kRpmThres = 300;

		leftfwdtalon.changeControlMode(TalonControlMode.Voltage);
		leftbacktalon.changeControlMode(TalonControlMode.Voltage);
		rightfwdtalon.changeControlMode(TalonControlMode.Voltage);
		rightbacktalon.changeControlMode(TalonControlMode.Voltage);

		leftfwdtalon.set(0.0);
		leftbacktalon.set(0.0);
		rightfwdtalon.set(0.0);
		rightbacktalon.set(0.0);

		rightfwdtalon.set(-6.0f);
		Timer.delay(4.0);
		final double currentRightMaster = rightfwdtalon.getOutputCurrent();
		final double rpmRightMaster = rightfwdtalon.getSpeed();
		rightfwdtalon.set(0.0f);

		Timer.delay(2.0);

		rightbacktalon.set(-6.0f);
		Timer.delay(4.0);
		final double currentRightSlave = rightbacktalon.getOutputCurrent();
		final double rpmRightSlave = rightfwdtalon.getSpeed();
		rightbacktalon.set(0.0f);

		Timer.delay(2.0);

		leftfwdtalon.set(6.0f);
		Timer.delay(4.0);
		final double currentLeftMaster = leftfwdtalon.getOutputCurrent();
		final double rpmLeftMaster = leftfwdtalon.getSpeed();
		leftfwdtalon.set(0.0f);

		Timer.delay(2.0);

		leftbacktalon.set(6.0f);
		Timer.delay(4.0);
		final double currentLeftSlave = leftbacktalon.getOutputCurrent();
		final double rpmLeftSlave = leftfwdtalon.getSpeed();
		leftbacktalon.set(0.0);

		rightfwdtalon.changeControlMode(TalonControlMode.PercentVbus);
		leftfwdtalon.changeControlMode(TalonControlMode.PercentVbus);

		rightbacktalon.changeControlMode(TalonControlMode.Follower);
		rightbacktalon.set(Hardware.RIGHT_FWD_TALON_ID);

		leftbacktalon.changeControlMode(TalonControlMode.Follower);
		leftbacktalon.set(Hardware.LEFT_FWD_TALON_ID);

		System.out.println(
				"Drive Right Master Current: " + currentRightMaster + " Drive Right Slave Current: "
						+ currentRightSlave);
		System.out.println(
				"Drive Left Master Current: " + currentLeftMaster + " Drive Left Slave Current: "
						+ currentLeftSlave);
		System.out.println(
				"Drive RPM RMaster: " + rpmRightMaster + " RSlave: " + rpmRightSlave + " LMaster: "
						+ rpmLeftMaster + " LSlave: " + rpmLeftSlave);

		boolean failure = false;

		if (currentRightMaster < kCurrentThres) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Master Current Low !!!!!!!!!!");
		}

		if (currentRightSlave < kCurrentThres) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Slave Current Low !!!!!!!!!!");
		}

		if (currentLeftMaster < kCurrentThres) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Master Current Low !!!!!!!!!!");
		}

		if (currentLeftSlave < kCurrentThres) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Slave Current Low !!!!!!!!!!");
		}

		if (!Util.allCloseTo(Arrays.asList(currentRightMaster, currentRightSlave), currentRightMaster,
				5.0)) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Currents Different !!!!!!!!!!");
		}

		if (!Util
				.allCloseTo(Arrays.asList(currentLeftMaster, currentLeftSlave), currentLeftSlave, 5.0)) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Currents Different !!!!!!!!!!!!!");
		}

		if (rpmRightMaster < kRpmThres) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Master RPM Low !!!!!!!!!!!!!!!!!!!");
		}

		if (rpmLeftMaster < kRpmThres) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Master RPM Low !!!!!!!!!!!!!!!!!!!");
		}

		if (!Util.allCloseTo(Arrays.asList(rpmRightMaster, rpmRightSlave, rpmLeftMaster, rpmLeftSlave),
				rpmRightMaster, 250)) {
			failure = true;
			System.out.println("!!!!!!!!!!!!!!!!!!! Drive RPMs different !!!!!!!!!!!!!!!!!!!");
		}
		if (failure) {
			System.out.println("FAILED");
		} else {
			System.out.println("SUCCESS");
		}
		return !failure;
	}

	public enum DriveControlState {
		OPEN_LOOP, // Open loop PercentVBus Control
		VELOCITY_SETPOINT, // Velocity Control in Manual or other form
		PATH_FOLLOWING, // Path following in Auto - Uses Talon Velocity Control
	}

	public static class DriveDebugOutput {

		public double timestamp;
		public double leftOutput;
		public double rightOutput;
		public String controlMode;
		public double leftSetpoint;
		public double rightSetpoint;
		public double leftPosition;
		public double rightPosition;
		public double leftVelocity;
		public double rightVelocity;
		public double heading;
		public double desiredHeading;
		public double headingError;
		public double leftDesiredVel;
		public double leftDesiredPos;
		public double leftPosError;
		public double leftVelError;
		public double rightDesiredVel;
		public double rightDesiredPos;
		public double rightPosError;
		public double rightVelError;
		public double desiredX;
		public double desiredY;
	}
}
