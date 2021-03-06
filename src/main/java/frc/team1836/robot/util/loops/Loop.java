package frc.team1836.robot.util.loops;

/**
 * Interface for loops, which are routine that run periodically in the frc.team1836.robot code (such
 * as periodic gyroscope calibration, etc.)
 */
public interface Loop {

	public void onStart(double timestamp);

	public void onLoop(double timestamp);

	public void onStop(double timestamp);
}
