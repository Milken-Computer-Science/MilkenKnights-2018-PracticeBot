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
	private static float _rgb[] = new float[3];
	public float Hue;
	public float Saturation;
	public float Value;
	private CANifier ledStrip;
	private MovingAverage _averageR = new MovingAverage(10);
	private MovingAverage _averageG = new MovingAverage(10);
	private MovingAverage _averageB = new MovingAverage(10);

	public LED() {
		ledStrip = new CANifier(Constants.Hardware.LED_STRIP_ID);
		ledStrip.EnablePWMOutput(0, true);
		ledStrip.EnablePWMOutput(1, true);
		ledStrip.EnablePWMOutput(2, true);
		ledStrip.EnablePWMOutput(3, true);
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

	@Override
	public void registerEnabledLoops(Looper enabledLooper) {
		Loop mLoop = new Loop() {
			@Override
			public void onStart(double timestamp) {

			}

			@Override
			public void onLoop(double timestamp) {

				ledStrip.SetLEDOutput(_rgb[0], LEDChannel.LEDChannelA);
				ledStrip.SetLEDOutput(_rgb[1], LEDChannel.LEDChannelB);
				ledStrip.SetLEDOutput(_rgb[2], LEDChannel.LEDChannelC);

			}

			@Override
			public void onStop(double timestamp) {
				stop();
			}
		};
		enabledLooper.register(mLoop);
	}

	public void setHSV(float x, float y) {
	      /* Calculate theta in degrees */
		float theta = (float) Math.atan2(x, y) * 180f / (float) Math.PI;
		/* Take the magnitude and cap it at '1'.  This will be our saturation (how far away from white we want to be) */
		float saturation = (float) Math.sqrt(x * x + y * y);
		saturation = Util.Cap(saturation, 1);
		/* Pick a value of '1', how far away from black we want to be. */
		Hue = theta;
		Saturation = saturation;
		Value = 1; /* scale down for brightness */
	}

	public void updateValues() {
		if (Saturation > 1) {
			Saturation = 1;
		}
		if (Saturation < 0) {
			Saturation = 0;
		}

		if (Value > 1) {
			Value = 1;
		}
		if (Value < 0) {
			Value = 0;
		}

				/* Convert to HSV to RGB */
		_rgb = HsvToRgb.Convert(Hue, Saturation, Value);

		_rgb[0] = _averageR.Process(_rgb[0]);
		_rgb[1] = _averageG.Process(_rgb[1]);
		_rgb[2] = _averageB.Process(_rgb[2]);
	}
}
