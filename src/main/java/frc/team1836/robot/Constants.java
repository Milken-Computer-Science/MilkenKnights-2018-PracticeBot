package frc.team1836.robot;

/**
 * Stores all Hard-coded numbers subject to change
 */
public final class Constants {

	public static class Hardware {

		public static final int LEFT_FWD_TALON_ID = 1;
		public static final int LEFT_BACK_TALON_ID = 2;
		public static final int RIGHT_FWD_TALON_ID = 8;
		public static final int RIGHT_BACK_TALON_ID = 7;
		public static final int LED_STRIP_ID = 30;
		public static final boolean LEFT_FWD_TALON_SENSOR_REVERSE = false;
		public static final boolean RIGHT_FWD_TALON_SENSOR_REVERSE = false;
		public static double kLooperDt = 0.005;
	}

	public static class Input {

		public static final int DRIVE_STICK = 0;
		public static final int REVERSE_BUTTON = 1;
		public static final int SLOW_BUTTON = 2;
		public static final int STRAIGHT_BUTTON = 3;
	}

	public static class DRIVE {

		public static final double WHEEL_DIAMETER = 4;
		public static final double CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
		public static final double LEFT_MAX_VEL = (670 / 60) * (CIRCUMFERENCE); // Inches per second
		public static final double RIGHT_MAX_VEL = (700 / 60) * (CIRCUMFERENCE); // Inches per second
		public static final double DRIVE_A = LEFT_MAX_VEL * 0.75;
		public static final double DRIVE_V = LEFT_MAX_VEL * 0.75;
		public static final int DRIVE_I_ZONE = 0;
		public static final double DRIVE_P = 0 * ((0.1 * 1023.0) / (700.0));
		public static final double DRIVE_I = 0;
		public static final double DRIVE_D = 0 * ((0.1 * 1023.0) / (400.0));
		public static final double DRIVE_F = (1023.0 / ((670.0 / 60.0 / 10.0) * 4096.0));

		public static final double DRIVE_FOLLOWER_P = 0;
		public static final double DRIVE_FOLLOWER_V = 0;
		public static final double DRIVE_FOLLOWER_A = 0;
		public static final double DRIVE_FOLLOWER_ANG = 0;
		public static final double DRIVE_FOLLOWER_DIST_TOL = 1.5;
		public static final double DRIVE_FOLLOWER_ANG_TOL = 1.5;

		public static final int CODES_PER_REV = 4096;
	}

	public static class Log {

		public static final String LEFT_PATH_LOG_DIR = "/home/lvuser/LEFT_PATH_LOG.java";
		public static final String RIGHT_PATH_LOG_DIR = "/home/lvuser/RIGHT_PATH_LOG.java";
	}

}
