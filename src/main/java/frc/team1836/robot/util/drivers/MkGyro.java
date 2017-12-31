package frc.team1836.robot.util.drivers;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import java.util.ArrayList;

public class MkGyro implements PIDSource {

	private final AHRS navX;
	PIDSourceType pid_source_type = PIDSourceType.kDisplacement;
	private double offset;
	private ArrayList<Double[]> yawLog;

	public MkGyro(final AHRS navX) {
		this.navX = navX;
		yawLog = new ArrayList<>();
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

	public void logYaw(double timestamp) {
		timestamp = (customRound(timestamp));
		yawLog.add(new Double[]{timestamp, getYaw()});
		yawLog.remove(0);
	}

	public void getClosestYaw(double timestamp){
		timestamp = (customRound(timestamp));

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

	public double customRound(double num) {
		return Math.round(num * 200) / 200.0;
	}

}
