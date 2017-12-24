package frc.team1836.robot;

import frc.team1836.robot.util.drivers.MkButton;
import frc.team1836.robot.util.drivers.MkJoystick;

public class Inputs {

    public static final MkJoystick driverJoystick = new MkJoystick(Constants.Input.DRIVE_STICK);
    // public static final MkJoystick operatorJoystick = new MkJoystick(Constants.input.OPERATOR_STICK);
    public static final MkButton reverseButton =
            driverJoystick.getButton(Constants.Input.REVERSE_BUTTON);
    public static final MkButton straightButton =
            driverJoystick.getButton(Constants.Input.STRAIGHT_BUTTON);
    public static final MkButton cheezyButton =
            driverJoystick.getButton(Constants.Input.SLOW_BUTTON);
//    public static final MkButton climberFwdButton =
    //          operatorJoystick.getButton(Constants.input.CLIMBER_FWD_BUTTON);
}
