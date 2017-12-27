package frc.team1836.robot.util.drivers;


import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
import frc.team1836.robot.Constants;

/**
 * CANTalon Wrapper for either Drive Talons or Rotational Talons PID Values for rotational talons
 * must be configured but not necessarily used
 */
public class MkCANTalon extends TalonSRX {

	private final int codesPerRev = Constants.DRIVE.CODES_PER_REV;

	public MkCANTalon(int deviceNumber, double wheelDiameter) {
		super(deviceNumber);
		resetConfig();
		configEncoderCodesPerRev(codesPerRev);
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

	@Override
	public void set(double val) {
		super.set(val);
	}

	@Override
	public double getError() {
		return nativeUnitsToInches(super.getError());
	}

	@Override
	public double getPosition() {
		return nativeUnitsToInches(super.getPosition());
	}

	@Override
	public double getSpeed() {
		return nativeUnitsPer100MstoInchesPerSec(super.getSpeed());
	}

	@Override
	public double getSetpoint() {
		return nativeUnitsPer100MstoInchesPerSec(super.getSetpoint());
	}

	private double nativeUnitsPer100MstoInchesPerSec(double vel) {
		return 10 * nativeUnitsToInches(vel);
	}

	private double nativeUnitsToInches(double units) {
		return (units / Constants.DRIVE.CODES_PER_REV) * (Constants.DRIVE.CIRCUMFERENCE);
	}

	private double InchesPerSecToUnitsPer100Ms(double vel) {
		return InchesToNativeUnits(vel) / 10;
	}

	private double InchesToNativeUnits(double in) {
		return (Constants.DRIVE.CODES_PER_REV) * (in / Constants.DRIVE.CIRCUMFERENCE);
	}


}
