package frc.team1836.robot.util.state;

import com.ctre.phoenix.MotorControl.SmartMotorController.TalonControlMode;

public class TalonState {

	private double vel;
	private double pos;
	private double error;



	private double setpoint;
	private TalonControlMode controlMode;

	public TalonState(double pos, double vel, double setpoint, double error, TalonControlMode controlMode) {
		this.controlMode = controlMode;
		this.pos = pos;
		this.vel = vel;
		this.error = error;
		this.setpoint = setpoint;

	}

	public double getVel() {
		return vel;
	}
	public double getSetpoint() {
		return setpoint;
	}

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public void setVel(double vel) {
		this.vel = vel;
	}

	public double getPos() {
		return pos;
	}

	public void setPos(double pos) {
		this.pos = pos;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public TalonControlMode getControlMode() {
		return controlMode;
	}

	public void setControlMode(TalonControlMode controlMode) {
		this.controlMode = controlMode;
	}



}
