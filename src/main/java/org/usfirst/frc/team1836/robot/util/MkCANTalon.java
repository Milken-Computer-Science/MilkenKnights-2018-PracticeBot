package org.usfirst.frc.team1836.robot.util;

import com.ctre.CANTalon;
import org.usfirst.frc.team1836.robot.Constants;

public class MkCANTalon extends CANTalon {

	private final double wheelDiameter;
	private final int codesPerRev = Constants.DRIVE.CODES_PER_REV;
	private final boolean rotation;
	private boolean setPrint = true;


	public MkCANTalon(int deviceNumber, double wheelDiameter) {
		super(deviceNumber);
		resetConfig();
		configEncoderCodesPerRev(codesPerRev);
		this.wheelDiameter = wheelDiameter;
		this.rotation = false;
	}

	private void resetConfig() {
		changeControlMode(TalonControlMode.PercentVbus);
		clearIAccum();
		ClearIaccum();
		clearMotionProfileHasUnderrun();
		clearMotionProfileTrajectories();
		clearStickyFaults();
		configMaxOutputVoltage(12);
		configNominalOutputVoltage(0, -0);
		configPeakOutputVoltage(12, -12);
		enableBrakeMode(false);
		enableZeroSensorPositionOnForwardLimit(false);
		enableZeroSensorPositionOnIndex(false, false);
		enableZeroSensorPositionOnReverseLimit(false);
		reverseOutput(false);
		reverseSensor(false);
		setPosition(0);
		setProfile(0);
	}

	public MkCANTalon(int deviceNumber, boolean rotation, double kP, double kI, double kD) {
		super(deviceNumber);
		resetConfig();
		configEncoderCodesPerRev(codesPerRev);
		this.rotation = rotation;
		this.wheelDiameter = 0;
		setPID(kP, kI, kD);
	}

	public double getMkError() {
		if (rotation) {
			return getMkSetpoint() - getMkPosition();
		} else {
			return (getError() / codesPerRev) * Math.PI * wheelDiameter;
		}
	}

	public double getMkSetpoint() {
		if (rotation) {
			return getSetpoint() * 360;
		} else {
			return getSetpoint() * Math.PI * wheelDiameter;
		}
	}

	public double getMkPosition() {
		if (rotation) {
			return getPosition() * 360;
		} else {
			return getPosition() * Math.PI * wheelDiameter;
		}
	}

	/*
	 * @return Degrees Per Second or Inches Per Second
	 */
	public double getMkVelocity() {
		if (rotation) {
			return getEncVelocity();
			// RPM * 1 min / 60 sec * 360 deg / 1 rotation
		} else {
			return ((getEncVelocity() * (Math.PI * wheelDiameter)) / 60);
			// RPM * (PI * wheelDiameter) * 1 min / 60 seconds
		}
	}

	public void setEncPosition(double pos) {
		if (rotation) {
			super.set(pos / 360);
		} else {
			super.set(pos / (Math.PI * wheelDiameter));
		}
	}

	@Override
	public void set(double val) {
		super.set(modeValue(val));
		if (setPrint) {
			System.out.println("Mode: " + getControlMode().toString() + " Value: " + modeValue(val));
		}
	}

	public double modeValue(double val) {
		if (getControlMode().equals(TalonControlMode.Speed)) {
			if (rotation) {
				return (val / 360) * 60;
			} else {
				return (val / (Math.PI * wheelDiameter)) * 60;
			}
		} else if (getControlMode().equals(TalonControlMode.MotionMagic)) {
			if (rotation) {
				return val / 360;
			} else {
				return (val / (Math.PI * wheelDiameter));
			}
		} else {
			return val;
		}
	}

	public void setPrint(boolean val) {
		this.setPrint = val;
	}

}
