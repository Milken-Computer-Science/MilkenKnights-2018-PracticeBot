package org.usfirst.frc.team1836.robot;

import org.usfirst.frc.team1836.robot.subsystems.Climber;
import org.usfirst.frc.team1836.robot.subsystems.Drive;
import org.usfirst.frc.team1836.robot.util.DriveHelper;

public class InputController {

	private static int reverseState = 1;

	public static void updateInputs() {
		reverseState = Inputs.reverseButton.isPressed() ? -reverseState : reverseState;
		double move = Inputs.driverJoystick.getRawAxis(1) * reverseState;
		double turn = Inputs.straightButton.isHeld() ? 0 : -Inputs.driverJoystick.getRawAxis(2);
		double climberSpeed = Inputs.climberFwdButton.isHeld() ? Constants.Climb.CLIMBER_SPEED : 0;
		Drive.getInstance()
				.setVelocitySetpoint(DriveHelper.cheesyDrive(turn, move, Inputs.cheezyButton.isHeld()));
		Shooter.getInstance().setSetpointRpm(Inputs.operatorJoystick.getRawAxis(1));
		Climber.getInstance().setOpenLoop(climberSpeed);
	}


}
