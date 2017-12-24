package frc.team1836.robot.util.input;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class MkGyro implements PIDSource {

    private final AHRS navX;
    PIDSourceType pid_source_type = PIDSourceType.kDisplacement;
    private double offset;

    public MkGyro(final AHRS navX) {
        this.navX = navX;
    }

    public void zeroYaw() {
        offset = -navX.getYaw();
    }

    public boolean isConnected() {
        return navX.isConnected();
    }

    public double getYaw() {
        return navX.getYaw() + offset;
    }

    public double getRate() {
        return navX.getRate();
    }

    public double getRealYaw() {
        return navX.getYaw();
    }

    public double getPitch() {
        return navX.getPitch();
    }

    public double getRoll() {
        return navX.getRoll();
    }

    public boolean isMoving() {
        return navX.isMoving();
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pid_source_type;
    }

    @Override
    public void setPIDSourceType(PIDSourceType type) {
        pid_source_type = type;
    }

    @Override
    public double pidGet() {
        return navX.getYaw() + offset;
    }

}
