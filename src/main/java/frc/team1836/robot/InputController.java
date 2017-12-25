package frc.team1836.robot;

import frc.team1836.robot.subsystems.Drive;
import frc.team1836.robot.subsystems.LED;
import frc.team1836.robot.util.state.DriveHelper;

public class InputController {

	private int reverseState = 1;

	public void updateInputs() {
		reverseState = Inputs.reverseButton.isPressed() ? -reverseState : reverseState;
		double move = -Inputs.driverJoystick.getRawAxis(1) * reverseState;
		double turn = Inputs.straightButton.isHeld() ? 0 : -Inputs.driverJoystick.getRawAxis(2);
		if (Math.abs(move) > Constants.DRIVE.JOY_TOL || Math.abs(turn) > Constants.DRIVE.JOY_TOL) {
			Drive.getInstance().setVelocitySetpoint(DriveHelper.cheesyDrive(move, turn, Inputs.cheezyButton.isHeld()));
		} else {
			Drive.getInstance().setVelocitySetpoint(DriveHelper.cheesyDrive(0, 0, Inputs.cheezyButton.isHeld()));
		}
		LED.getInstance().setHSV((float) Inputs.driverJoystick.getRawAxis(0), (float) Inputs.driverJoystick.getRawAxis(1));
	}


}
