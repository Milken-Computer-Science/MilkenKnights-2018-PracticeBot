package frc.team1836.robot.util.state;

public class GyroState {


	private double yaw;
	private double pitch;
	private double roll;
	private double rate;

	public GyroState(double yaw, double pitch, double roll, double rate) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		this.rate = rate;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double roll) {
		this.roll = roll;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Yaw: " + yaw + " Pitch: " + pitch + " Roll: " + roll + "Rate: " + rate;
	}
}
