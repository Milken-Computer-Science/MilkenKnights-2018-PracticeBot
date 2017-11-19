package frc.team1836.robot.subsystems;

import frc.team1836.robot.Constants;
import frc.team1836.robot.util.MkCANTalon;
import frc.team1836.robot.util.loops.Loop;
import frc.team1836.robot.util.loops.Looper;

public class Climber extends Subsystem {

	private static Climber climber;
	private MkCANTalon climberTalon;

	public Climber() {
		climberTalon = new MkCANTalon(Constants.Hardware.CLIMBER_TALON_ID, true, 0, 0, 0);
		climberTalon.setPrint(false);
	}

	public static Climber getInstance() {
		if (climber == null) {
			climber = new Climber();
		}
		return climber;
	}

	public void setOpenLoop(double val) {
		climberTalon.set(val);
	}

	@Override
	public void outputToSmartDashboard() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void zeroSensors() {

	}

	@Override
	public void registerEnabledLoops(Looper enabledLooper) {
		Loop mLoop = new Loop() {
			@Override
			public void onStart(double timestamp) {
				synchronized (Climber.this) {

				}

			}

			@Override
			public void onLoop(double timestamp) {

				synchronized (Climber.this) {
				}

			}

			@Override
			public void onStop(double timestamp) {
				stop();
			}
		};
		enabledLooper.register(mLoop);
	}

}
