package org.usfirst.frc.team1836.robot;

import org.usfirst.frc.team1836.robot.util.MkButton;
import org.usfirst.frc.team1836.robot.util.MkJoystick;

public class Inputs {
    public static final MkJoystick driverJoystick = new MkJoystick(Constants.Input.DRIVE_STICK);
    public static final MkJoystick operatorJoystick = new MkJoystick(Constants.Input.OPERATOR_STICK);
    public static final MkButton reverseButton =
            driverJoystick.getButton(Constants.Input.REVERSE_BUTTON);
    public static final MkButton straightButton =
            driverJoystick.getButton(Constants.Input.STRAIGHT_BUTTON);
    public static final MkButton cheezyButton =
            driverJoystick.getButton(Constants.Input.CHEESE_BUTTON);
    public static final MkButton gearPickupButton =
            operatorJoystick.getButton(Constants.Input.GEAR_PICKUP_BUTTON);
    public static final MkButton gearStowButton =
            operatorJoystick.getButton(Constants.Input.GEAR_STOW_BUTTON);
    public static final MkButton gearPlaceButton =
            operatorJoystick.getButton(Constants.Input.GEAR_PLACE_BUTTON);
    public static final MkButton rollerInButton =
            operatorJoystick.getButton(Constants.Input.ROLLER_IN_BUTTON);
    public static final MkButton rollerOutButton =
            operatorJoystick.getButton(Constants.Input.ROLLER_OUT_BUTTON);
    public static final MkButton climberFwdButton =
            operatorJoystick.getButton(Constants.Input.CLIMBER_FWD_BUTTON);
    public static final MkButton gearManualButton =
            operatorJoystick.getButton(Constants.Input.GEAR_MANUAL_BUTTON);
    public static final MkButton gearResetButton =
            operatorJoystick.getButton(Constants.Input.GEAR_RESET_BUTTON);
}
