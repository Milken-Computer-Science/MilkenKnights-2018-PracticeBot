package frc.team1836.robot.util.input;


import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
import frc.team1836.robot.Constants;

/**
 * CANTalon Wrapper for either Drive Talons or Rotational Talons PID Values for rotational talons
 * must be configured but not necessarily used
 */
public class MkCANTalon extends TalonSRX {

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

    public MkCANTalon(int deviceNumber, boolean rotation, double kP, double kI, double kD) {
        super(deviceNumber);
        resetConfig();
        configEncoderCodesPerRev(codesPerRev);
        this.rotation = rotation;
        this.wheelDiameter = 0;
        setPID(kP, kI, kD);
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
        if (setPrint) {
            System.out.println("Mode: " + getControlMode().toString() + " Value: " + val);
        }
    }

    public void setPrint(boolean val) {
        this.setPrint = val;
    }

}
