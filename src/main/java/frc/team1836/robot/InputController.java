package frc.team1836.robot;

import frc.team1836.robot.subsystems.Drive;
import frc.team1836.robot.util.DriveHelper;

public class InputController {

    private int reverseState = 1;

    public void updateInputs() {
        reverseState = Inputs.reverseButton.isPressed() ? -reverseState : reverseState;
        double move = -Inputs.driverJoystick.getRawAxis(1) * reverseState;
        double turn = Inputs.straightButton.isHeld() ? 0 : -Inputs.driverJoystick.getRawAxis(2);
        Drive.getInstance()
                .setOpenLoop(DriveHelper.cheesyDrive(turn, move, Inputs.cheezyButton.isHeld()));
    }


}
