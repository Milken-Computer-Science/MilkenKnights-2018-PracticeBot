package org.usfirst.frc.team1836.robot.subsystems;

import static org.usfirst.frc.team1836.robot.Constants.DRIVE;
import static org.usfirst.frc.team1836.robot.Constants.Hardware;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.PathFollower;
import com.team254.lib.trajectory.TrajectoryFollower;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Arrays;
import org.usfirst.frc.team1836.robot.util.DriveSignal;
import org.usfirst.frc.team1836.robot.util.MkCANTalon;
import org.usfirst.frc.team1836.robot.util.MkGyro;
import org.usfirst.frc.team1836.robot.util.ReflectingCSVWriter;
import org.usfirst.frc.team1836.robot.util.Util;
import org.usfirst.frc.team1836.robot.util.loops.Loop;
import org.usfirst.frc.team1836.robot.util.loops.Looper;

public class Drive extends Subsystem {

	private static Drive mDrive;
	private final MkCANTalon leftfwdtalon, leftbacktalon, rightfwdtalon, rightbacktalon;
	private DriveDebugOutput mDebug = new DriveDebugOutput();
	private ReflectingCSVWriter<DriveDebugOutput> mCSVWriter;
	private MkGyro navX;
	private TrajectoryFollower trajFollower;
	private double trajectoryDist;
	private DriveControlState mDriveControlState;
	private PathFollower mPathFollower;

	private Drive() {
		navX = new MkGyro(new AHRS(SPI.Port.kMXP));
		leftfwdtalon = new MkCANTalon(Hardware.LEFT_FWD_TALON_ID, DRIVE.WHEEL_DIAMETER);
		leftbacktalon = new MkCANTalon(Hardware.LEFT_BACK_TALON_ID, DRIVE.WHEEL_DIAMETER);
		rightbacktalon = new MkCANTalon(Hardware.RIGHT_BACK_TALON_ID, DRIVE.WHEEL_DIAMETER);
		rightfwdtalon = new MkCANTalon(Hardware.RIGHT_FWD_TALON_ID, DRIVE.WHEEL_DIAMETER);

		leftfwdtalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftfwdtalon.reverseSensor(Hardware.LEFT_FWD_TALON_SENSOR_REVERSE);
		leftfwdtalon.configNominalOutputVoltage(+0.0f, -0.0f);
		leftfwdtalon.configPeakOutputVoltage(+12.0f, -12.0f);
		leftfwdtalon.setProfile(0);
		leftfwdtalon.setF(DRIVE.DRIVE_F);
		leftfwdtalon.setP(DRIVE.DRIVE_P);
		leftfwdtalon.setI(DRIVE.DRIVE_I);
		leftfwdtalon.setD(DRIVE.DRIVE_D);
		leftfwdtalon.setIZone(DRIVE.DRIVE_I_ZONE);
		leftfwdtalon.setMotionMagicCruiseVelocity(DRIVE.DRIVE_V);
		leftfwdtalon.setMotionMagicAcceleration(DRIVE.DRIVE_A);

		rightfwdtalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightfwdtalon.reverseSensor(Hardware.RIGHT_FWD_TALON_SENSOR_REVERSE);
		rightfwdtalon.configNominalOutputVoltage(+0.0f, -0.0f);
		rightfwdtalon.configPeakOutputVoltage(+12.0f, -12.0f);
		rightfwdtalon.setProfile(0);
		rightfwdtalon.setF(DRIVE.DRIVE_F);
		rightfwdtalon.setP(DRIVE.DRIVE_P);
		rightfwdtalon.setI(DRIVE.DRIVE_I);
		rightfwdtalon.setD(DRIVE.DRIVE_D);
		rightfwdtalon.setIZone(DRIVE.DRIVE_I_ZONE);
		rightfwdtalon.setMotionMagicCruiseVelocity(DRIVE.DRIVE_V);
		rightfwdtalon.setMotionMagicAcceleration(DRIVE.DRIVE_A);

		leftfwdtalon.reverseOutput(Hardware.LEFT_FWD_TALON_REVERSE);
		leftbacktalon.reverseOutput(Hardware.LEFT_BACK_TALON_REVERSE);
		rightfwdtalon.reverseOutput(Hardware.RIGHT_FWD_TALON_REVERSE);
		rightbacktalon.reverseOutput(Hardware.RIGHT_BACK_TALON_REVERSE);

		leftfwdtalon.setPrint(false);
		leftbacktalon.setPrint(false);
		rightfwdtalon.setPrint(false);
		rightbacktalon.setPrint(false);

		mCSVWriter = new ReflectingCSVWriter<DriveDebugOutput>("/home/lvuser/DRIVE-LOGS.csv",
				DriveDebugOutput.class);

		leftfwdtalon.changeControlMode(TalonControlMode.Speed);
		rightfwdtalon.changeControlMode(TalonControlMode.Speed);
		leftbacktalon.changeControlMode(TalonControlMode.Follower);
		rightbacktalon.changeControlMode(TalonControlMode.Follower);
		leftbacktalon.set(Hardware.LEFT_FWD_TALON_ID);
		rightbacktalon.set(Hardware.RIGHT_FWD_TALON_ID);
		mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
	}

	public static Drive getInstance() {
		if (mDrive == null) {
			mDrive = new Drive();
		}
		return mDrive;
	}

	@Override
	public void writeToLog() {
		mCSVWriter.write();
	}

	@Override
	public void outputToSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder Position", leftfwdtalon.getMkPosition());
		SmartDashboard.putNumber("Right Encoder Position", rightfwdtalon.getMkPosition());
		SmartDashboard.putNumber("Left Encoder Velocity", leftfwdtalon.getMkVelocity());
		SmartDashboard.putNumber("Right Encoder Velocity", rightfwdtalon.getMkVelocity());
		SmartDashboard.putNumber("NavX Yaw", navX.getYaw());
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
					switch (mDriveControlState) {
						case OPEN_LOOP:
							return;
						case VELOCITY_SETPOINT:
							return;
						case PATH_FOLLOWING:
							return;
						default:
							System.out.println("Unexpected drive control state: " + mDriveControlState);
							break;
					}
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

	public synchronized void setOpenLoop(DriveSignal signal) {
		if (mDriveControlState != DriveControlState.OPEN_LOOP) {
			leftfwdtalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			rightfwdtalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			mDriveControlState = DriveControlState.OPEN_LOOP;
		}
		leftfwdtalon.set(signal.getLeft());
		rightfwdtalon.set(-signal.getRight());
	}

	public synchronized void setVelocitySetpoint(double left_inches_per_sec,
			double right_inches_per_sec) {
		configureTalonsForSpeedControl();
		mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
		updateVelocitySetpoint(left_inches_per_sec, right_inches_per_sec);
	}

	private void configureTalonsForSpeedControl() {
		if (!usesTalonVelocityControl(mDriveControlState)) {
			leftfwdtalon.changeControlMode(CANTalon.TalonControlMode.Speed);
			rightfwdtalon.changeControlMode(CANTalon.TalonControlMode.Speed);
		}
	}

	private synchronized void updateVelocitySetpoint(double left_inches_per_sec,
			double right_inches_per_sec) {
		if (usesTalonVelocityControl(mDriveControlState)) {
			leftfwdtalon.set(left_inches_per_sec);
			rightfwdtalon.set(right_inches_per_sec);
		}
	}

	public synchronized void resetEncoders() {
		leftfwdtalon.setEncPosition(0);
		leftfwdtalon.setPosition(0);
		rightfwdtalon.setPosition(0);
		rightfwdtalon.setEncPosition(0);
		leftbacktalon.setPosition(0);
		leftbacktalon.setPosition(0);
	}

	protected static boolean usesTalonVelocityControl(DriveControlState state) {
		if (state == DriveControlState.VELOCITY_SETPOINT || state == DriveControlState.PATH_FOLLOWING) {
			return true;
		}
		return false;
	}

	public synchronized void setVelocitySetpoint(DriveSignal sig) {
		configureTalonsForSpeedControl();
		mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
		updateVelocitySetpoint(sig.getLeft() * DRIVE.MAX_VEL, sig.getLeft() * DRIVE.MAX_VEL);
	}

	public boolean trajectoryFinished() {
		return mPathFollower.onTarget() & mPathFollower.getFinished();
	}

	public void logPath() {
		mPathFollower.saveLogTrajectory();
	}

	public void setPathFollower(Path mPath) {
		mPathFollower = new PathFollower(mPath, DRIVE.DRIVE_FOLLOWER_TOL, DRIVE.DRIVE_FOLLOWER_ANG_TOL);
	}

	private void updatePathFollower(double timestamp) {
		double leftVel = mPathFollower
				.getLeftVelocity(leftfwdtalon.getMkPosition(), leftfwdtalon.getMkVelocity(), navX.getYaw());
		double rightVel = mPathFollower
				.getRightVelocity(rightfwdtalon.getMkPosition(), rightfwdtalon.getMkVelocity(),
						-navX.getYaw());
		//Negative to allow for turning to correct
		updateVelocitySetpoint(leftVel, rightVel);
	}

	public boolean checkSystem() {
		System.out.println("Testing DRIVE.---------------------------------");
		final double kCurrentThres = 0.5;
		final double kRpmThres = 300;

		leftfwdtalon.changeControlMode(CANTalon.TalonControlMode.Voltage);
		leftbacktalon.changeControlMode(CANTalon.TalonControlMode.Voltage);
		rightfwdtalon.changeControlMode(CANTalon.TalonControlMode.Voltage);
		rightbacktalon.changeControlMode(CANTalon.TalonControlMode.Voltage);

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

		rightfwdtalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		leftfwdtalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		rightbacktalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightbacktalon.set(Hardware.RIGHT_FWD_TALON_ID);

		leftbacktalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftbacktalon.set(Hardware.LEFT_FWD_TALON_ID);

		System.out.println(
				"Drive Right Master Current: " + currentRightMaster + " Drive Right Slave Current: "
						+ currentRightSlave);
		System.out.println(
				"Drive Left Master Current: " + currentLeftMaster + " Drive Left Slave Current: "
						+ currentLeftSlave);
		System.out
				.println("Drive RPM RMaster: " + rpmRightMaster + " RSlave: " + rpmRightSlave + " LMaster: "
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

		if (!Util.allCloseTo(Arrays.asList(currentLeftMaster, currentLeftSlave), currentLeftSlave,
				5.0)) {
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

		return !failure;
	}

	public enum DriveControlState {
		OPEN_LOOP, // Open loop PercentVBus Control
		VELOCITY_SETPOINT, // Velocity Control in Manual or other form
		PATH_FOLLOWING, // Path following in Auto - Uses Talon Velocity Control
	}

	public static class DriveDebugOutput {

		public double timestamp;
		public double output;
		public String controlMode;
		public double setpoint;
		public double position;
		public double velocity;
	}


}
