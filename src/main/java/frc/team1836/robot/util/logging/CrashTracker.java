package frc.team1836.robot.util.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

/**
 * Tracks start-up and caught crash events, logging them to a file which dosn't roll over
 */
public class CrashTracker {

	private static final UUID RUN_INSTANCE_UUID = UUID.randomUUID();

	public static void logRobotStartup() {
		logMarker("frc.team1836.robot startup");
	}

	private static void logMarker(String mark) {
		logMarker(mark, null);
	}

	private static void logMarker(String mark, Throwable nullableException) {

		try (PrintWriter writer = new PrintWriter(
				new FileWriter("/home/lvuser/crash_tracking.txt", true))) {
			writer.print(RUN_INSTANCE_UUID.toString());
			writer.print(", ");
			writer.print(mark);
			writer.print(", ");
			writer.print(new Date().toString());

			if (nullableException != null) {
				writer.print(", ");
				nullableException.printStackTrace(writer);
			}

			writer.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logRobotConstruction() {
		logMarker("frc.team1836.robot startup");
	}

	public static void logRobotInit() {
		logMarker("frc.team1836.robot init");
	}

	public static void logTeleopInit() {
		logMarker("teleop init");
	}

	public static void logAutoInit() {
		logMarker("auto init");
	}

	public static void logDisabledInit() {
		logMarker("disabled init");
	}

	public static void logThrowableCrash(Throwable throwable) {
		logMarker("Exception", throwable);
	}
}
