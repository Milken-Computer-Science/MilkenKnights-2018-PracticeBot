package frc.team1836.robot.subsystems;

import frc.team1836.robot.Constants;
import frc.team1836.robot.Inputs;
import frc.team1836.robot.util.drivers.MkButton;
import frc.team1836.robot.util.drivers.MkJoystick;
import frc.team1836.robot.util.loops.Loop;
import frc.team1836.robot.util.loops.Looper;
import frc.team1836.robot.util.state.Button;
import frc.team1836.robot.util.state.DriveHelper;
import java.util.ArrayList;

public class Controller extends Subsystem {

	private static Controller mInstance = new Controller();
	private int reverseState = 1;
	public final MkJoystick driverJoystick;
	ArrayList<MkButton> useableButtons = new ArrayList<MkButton>();

	@Override
	public void outputToSmartDashboard() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void zeroSensors() {

	}

	public static Controller getInstance() {
		return mInstance;
	}

	private Controller() {

		driverJoystick = new MkJoystick(Constants.Input.DRIVE_STICK);
		for (Button button : Constants.Input.buttonList) {
			useableButtons.add(driverJoystick.getButton(button.getNum(), button.getName()));
		}


	}

	public void registerEnabledLoops(Looper enabledLooper) {
		Loop mLoop = new Loop() {
			@Override
			public void onStart(double timestamp) {

			}

			@Override
			public void onLoop(double timestamp) {

			}

			@Override
			public void onStop(double timestamp) {
				stop();
			}
		};
		enabledLooper.register(mLoop);
	}

	public void updateInputs() {
		reverseState = Inputs.reverseButton.isPressed() ? -reverseState : reverseState;
		double move = -Inputs.driverJoystick.getRawAxis(1) * reverseState;
		double turn = Inputs.straightButton.isHeld() ? 0 : -Inputs.driverJoystick.getRawAxis(2);
		if (Math.abs(move) > Constants.DRIVE.JOY_TOL || Math.abs(turn) > Constants.DRIVE.JOY_TOL) {
			Drive.getInstance()
					.setVelocitySetpoint(DriveHelper.cheesyDrive(move, turn, Inputs.cheezyButton.isHeld()));
		} else {
			Drive.getInstance()
					.setVelocitySetpoint(DriveHelper.cheesyDrive(0, 0, Inputs.cheezyButton.isHeld()));
		}
		LED.getInstance().setHSV((float) Inputs.driverJoystick.getRawAxis(0),
				(float) Inputs.driverJoystick.getRawAxis(1));
	}
}
