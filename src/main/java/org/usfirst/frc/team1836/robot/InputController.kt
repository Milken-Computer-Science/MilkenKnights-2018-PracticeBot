package org.usfirst.frc.team1836.robot

import org.usfirst.frc.team1836.robot.subsystems.Climber
import org.usfirst.frc.team1836.robot.subsystems.Drive
import org.usfirst.frc.team1836.robot.subsystems.Shooter
import org.usfirst.frc.team1836.robot.util.DriveHelper

object InputController {

    private var reverseState = 1

    fun updateInputs() {
        reverseState = if (Inputs.reverseButton.isPressed) -reverseState else reverseState
        val move: Double = Inputs.driverJoystick.getRawAxis(1) * reverseState
        val turn: Double = if (Inputs.straightButton.isHeld) 0.0 else -Inputs.driverJoystick.getRawAxis(2)
        val climberSpeed: Double = if (Inputs.climberFwdButton.isHeld) Constants.Climb.CLIMBER_SPEED else 0.0
        Drive.getInstance()
                .setVelocitySetpoint(DriveHelper.cheesyDrive(turn, move, Inputs.cheezyButton.isHeld))
        Shooter.getInstance().setSetpointRpm(Inputs.operatorJoystick.getRawAxis(1))
        Climber.getInstance().setOpenLoop(climberSpeed)
    }


}
