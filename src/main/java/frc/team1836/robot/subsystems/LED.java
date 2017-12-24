package frc.team1836.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;
import frc.team1836.robot.Constants;
import frc.team1836.robot.util.loops.Loop;
import frc.team1836.robot.util.loops.Looper;

public class LED extends Subsystem {

	private static LED mInstance = new LED();
	private CANifier ledStrip;

	public LED() {
		ledStrip = new CANifier(Constants.Hardware.LED_STRIP_ID);
		ledStrip.EnablePWMOutput(0, true);
		ledStrip.EnablePWMOutput(1, true);
		ledStrip.EnablePWMOutput(2, true);
		ledStrip.EnablePWMOutput(3, true);
		//ledStrip.SetLEDOutput(0.5, LEDChannel.LEDChannelB);

	}

	public static LED getInstance() {
		return mInstance;
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

	public int[] getRGB(final String rgb) {
		final int[] ret = new int[3];
		for (int i = 0; i < 3; i++) {
			ret[i] = Integer.parseInt(rgb.substring(i * 2, i * 2 + 2), 16);
		}
		return ret;
	}

	@Override
	public void registerEnabledLoops(Looper enabledLooper) {
		Loop mLoop = new Loop() {
			@Override
			public void onStart(double timestamp) {

			}

			@Override
			public void onLoop(double timestamp) {

				ledStrip.SetLEDOutput(0.0 / 255.0, LEDChannel.LEDChannelB); //RED
				ledStrip.SetLEDOutput(216.0 / 255.0, LEDChannel.LEDChannelA); //GREEN
				ledStrip.SetLEDOutput(255.0 / 255.0, LEDChannel.LEDChannelC); //BLUE

			}

			@Override
			public void onStop(double timestamp) {
				stop();
			}
		};
		enabledLooper.register(mLoop);
	}
}
