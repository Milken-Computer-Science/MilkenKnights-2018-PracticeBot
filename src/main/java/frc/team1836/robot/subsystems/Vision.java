package frc.team1836.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1836.robot.util.drivers.SpectrumJeVois;
import frc.team1836.robot.util.loops.Loop;
import frc.team1836.robot.util.loops.Looper;

public class Vision extends Subsystem {

	private static Vision mInstance = new Vision();
	SpectrumJeVois jevoisCam;

	private Vision() {
		jevoisCam = new SpectrumJeVois();
		jevoisCam.startCameraStream1();

	}

	public static Vision getInstance() {
		return mInstance;
	}

	@Override
	public void outputToSmartDashboard() {
		//System.out.println(jevoisCam.isVisionOnline());
		SmartDashboard.putNumber("Target X: ", jevoisCam.getTx());
		SmartDashboard.putNumber("Target Y: ", jevoisCam.getTy());
		SmartDashboard.putNumber("Target Area: ", jevoisCam.getTa());
		SmartDashboard.putBoolean("Target Acquired: ", jevoisCam.getTv());
		//System.out.println("X: " + jevoisCam.getTx() + " Y: " + jevoisCam.getTy() + " A:" + jevoisCam.getTa() + " V: " + jevoisCam.getTv());
		/* Output Below
		wrote 5/5 bytes, cmd: ping
    JeVois Connection Good!
		true
		false
*/
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
				synchronized (Vision.this) {

				}

			}

			@Override
			public void onLoop(double timestamp) {
				synchronized (Vision.this) {

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
