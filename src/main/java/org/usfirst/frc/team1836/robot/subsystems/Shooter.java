package org.usfirst.frc.team1836.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1836.robot.Constants;
import org.usfirst.frc.team1836.robot.loops.Loop;
import org.usfirst.frc.team1836.robot.loops.Looper;
import org.usfirst.frc.team1836.robot.util.MkCANTalon;
import org.usfirst.frc.team1836.robot.util.ReflectingCSVWriter;

public class Shooter extends Subsystem {

	private static Shooter mShooter;
	private double mSetpointRpm;
	private ShooterDebugOutput mDebug = new ShooterDebugOutput();
	private ReflectingCSVWriter<ShooterDebugOutput> mCSVWriter;
	private MkCANTalon shooterTalon;

	public Shooter() {
		mCSVWriter = new ReflectingCSVWriter<ShooterDebugOutput>("/home/lvuser/SHOOTER-LOGS.csv",
				ShooterDebugOutput.class);

		shooterTalon = new MkCANTalon(Constants.Hardware.SHOOTER_TALON_ID, true, Constants.Shooter.kP,
				Constants.Shooter.kI, Constants.Shooter.kD);
		shooterTalon.setF(Constants.Shooter.kF);
		shooterTalon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		shooterTalon.reverseOutput(Constants.Hardware.SHOOTER_REVERSE);
		shooterTalon.reverseSensor(Constants.Hardware.SHOOTER_SENSOR_REVERSE);
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
	}

	public static Shooter getInstance() {
		if (mShooter == null) {
			mShooter = new Shooter();
		}
		return mShooter;
	}

	public void setSetpointRpm(double mSetpointRpm) {
		shooterTalon.set(mSetpointRpm);
	}

	@Override
	public void writeToLog() {
		mCSVWriter.write();
	}

	@Override
	public void outputToSmartDashboard() {
		SmartDashboard.putNumber("Shooter Native Velocity", shooterTalon.getEncVelocity());
		SmartDashboard.putNumber("Shooter RPS", shooterTalon.getMkVelocity());
	}

	@Override
	public void stop() {
		shooterTalon.set(0);
	}

	@Override
	public void zeroSensors() {

	}

	@Override
	public void registerEnabledLoops(Looper enabledLooper) {
		enabledLooper.register(new Loop() {
			@Override
			public void onStart(double timestamp) {
				synchronized (Shooter.this) {

				}
			}

			@Override
			public void onLoop(double timestamp) {
				synchronized (Shooter.this) {
					update(timestamp);
					mCSVWriter.add(mDebug);
				}
			}

			@Override
			public void onStop(double timestamp) {
				mCSVWriter.flush();
			}
		});
	}

	public void update(double timestamp) {
		mDebug.timestamp = timestamp;
		mDebug.output = shooterTalon.getOutputVoltage() / shooterTalon.getBusVoltage();
		mDebug.rpm = shooterTalon.getMkVelocity();
		mDebug.setpoint = mSetpointRpm;
	}

	public void setmSetpointRpm(double range) {
		Constants.Shooter.kFlywheelAutoAimPolynomial.predict(range);
	}

	public static class ShooterDebugOutput {

		public double timestamp;
		public double setpoint;
		public double rpm;
		public double output;
	}


}
