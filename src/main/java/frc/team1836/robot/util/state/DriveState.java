package frc.team1836.robot.util.state;

import frc.team1836.robot.subsystems.Drive.DriveControlState;
import frc.team1836.robot.util.MkMath;

public class DriveState {

	TalonState leftState;
	TalonState rightState;
	DriveControlState controlState;
	private GyroState gyroState;

	public DriveState(TalonState leftState, TalonState rightState, DriveControlState controlState,
			GyroState gyroState) {
		this.leftState = leftState;
		this.rightState = rightState;
		this.controlState = controlState;
		this.gyroState = gyroState;
	}

	public TalonState getLeftState() {
		return leftState;
	}

	public void setLeftState(TalonState leftState) {
		this.leftState = leftState;
	}

	public TalonState getRightState() {
		return rightState;
	}

	public void setRightState(TalonState rightState) {
		this.rightState = rightState;
	}

	public DriveControlState getControlState() {
		return controlState;
	}

	public void setControlState(DriveControlState controlState) {
		this.controlState = controlState;
	}

	public GyroState getGyroState() {
		return gyroState;
	}

	public void setGyroState(GyroState gyroState) {
		this.gyroState = gyroState;
	}

	public double getAngle() {
		return gyroState.getYaw();
	}

	public double getLeftVelocity() {
		return -MkMath.nativeUnitsPer100MstoInchesPerSec(leftState.getVel());
	}

	public double getRightVelocity() {
		return MkMath.nativeUnitsPer100MstoInchesPerSec(rightState.getVel());
	}

	public double getLeftPosition() {
		return -MkMath.nativeUnitsToInches(leftState.getPos());
	}

	public double getRightPosition() {
		return MkMath.nativeUnitsToInches(rightState.getPos());
	}

	public double getLeftError() {
		return -MkMath.nativeUnitsToInches(leftState.getSetpoint() - leftState.getPos());
	}

	public double getRightError() {
		return MkMath.nativeUnitsToInches(rightState.getSetpoint() - rightState.getPos());
	}

	public double getAvgPosition() {
		return MkMath.nativeUnitsToInches((-leftState.getPos() + rightState.getPos()) / 2);
	}

	public double getAvgVelocity() {
		return MkMath.nativeUnitsToInches((-leftState.getVel() + rightState.getVel()) / 2);
	}
}
