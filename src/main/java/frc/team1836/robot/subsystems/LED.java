package frc.team1836.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;
import com.ctre.phoenix.Util;
import frc.team1836.robot.Constants;
import frc.team1836.robot.util.led.HsvToRgb;
import frc.team1836.robot.util.led.MovingAverage;
import frc.team1836.robot.util.loops.Loop;
import frc.team1836.robot.util.loops.Looper;

public class LED extends Subsystem {

	private static LED mInstance = new LED();
	private CANifier ledStrip;
	public float Hue;
	public float Saturation;
	public float Value;

	private static float _rgb[] = new float[3];

	private MovingAverage _averageR = new MovingAverage(10);
	private MovingAverage _averageG = new MovingAverage(10);
	private MovingAverage _averageB = new MovingAverage(10);

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

	public void setHSV(float x, float y){

		/* Calculate theta in degrees */
		float theta = (float)Math.atan2(x, y) * 180f / (float)Math.PI;
		/* Take the magnitude and cap it at '1'.  This will be our saturation (how far away from white we want to be) */
		float saturation = (float)Math.sqrt(x * x + y * y);
		saturation = Util.Cap(saturation, 1);
		/* Pick a value of '1', how far away from black we want to be. */
		Hue = theta;
		Saturation = saturation;
		Value = 1; /* scale down for brightness */
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

				if (Saturation > 1) {
					Saturation = 1;
				}
				if (Saturation < 0)
					Saturation = 0;

				if (Value > 1)
					Value = 1;
				if (Value < 0)
					Value = 0;

				/* Convert to HSV to RGB */
				_rgb = HsvToRgb.Convert(Hue, Saturation, Value);

				_rgb[0] = _averageR.Process(_rgb[0]);
				_rgb[1] = _averageG.Process(_rgb[1]);
				_rgb[2] = _averageB.Process(_rgb[2]);
		System.out.println(_rgb[0]);
				/* Update CANifier's LED strip */
				ledStrip.SetLEDOutput(_rgb[0], LEDChannel.LEDChannelA);
				ledStrip.SetLEDOutput(_rgb[1], LEDChannel.LEDChannelB);
				ledStrip.SetLEDOutput(_rgb[2], LEDChannel.LEDChannelC);



			/*	ledStrip.SetLEDOutput(0.0 / 255.0, LEDChannel.LEDChannelB); //RED
				ledStrip.SetLEDOutput(216.0 / 255.0, LEDChannel.LEDChannelA); //GREEN
				ledStrip.SetLEDOutput(255.0 / 255.0, LEDChannel.LEDChannelC); //BLUE */

			}

			@Override
			public void onStop(double timestamp) {
				stop();
			}
		};
		enabledLooper.register(mLoop);
	}
}
