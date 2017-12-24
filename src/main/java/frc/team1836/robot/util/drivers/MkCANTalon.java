package frc.team1836.robot.util.drivers;


import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
import frc.team1836.robot.Constants;

/**
 * CANTalon Wrapper for either Drive Talons or Rotational Talons PID Values for rotational talons
 * must be configured but not necessarily used
 */
public class MkCANTalon extends TalonSRX {

	private final int codesPerRev = Constants.DRIVE.CODES_PER_REV;
	private int reverse = 1;

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
		super.set(val * reverse);
	}

	@Override
	public void reverseOutput(boolean out) {
		if (out) {
			reverse = -1;
		}
	}

	@Override
	public double getSpeed() {
		return nativeUnitsPer100MstoInchesPerSec(super.getSpeed() * reverse);
	}

	@Override
	public double getPosition() {
		return nativeUnitsToInches(super.getPosition() * reverse);
	}

	@Override
	public double getError() {
		return nativeUnitsToInches(super.getError() * reverse);
	}

	@Override
	public double getSetpoint() {
		return nativeUnitsToInches(super.getSetpoint() * reverse);
	}

	private double nativeUnitsToInches(double units) {
		return (units / Constants.DRIVE.CODES_PER_REV) * (Constants.DRIVE.CIRCUMFERENCE);
	}

	private double InchesToNativeUnits(double in) {
		return (Constants.DRIVE.CODES_PER_REV) * (in / Constants.DRIVE.CIRCUMFERENCE);
	}

	private double nativeUnitsPer100MstoInchesPerSec(double vel) {
		return 10 * nativeUnitsToInches(vel);
	}

	private double InchesPerSecToUnitsPer100Ms(double vel) {
		return InchesToNativeUnits(vel) / 10;
	}


}
