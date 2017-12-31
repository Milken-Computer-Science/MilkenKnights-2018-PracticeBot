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
		public static double kLooperDt = 0.005;
	}

	public static class Input {

		public static final int DRIVE_STICK = 0;
		public static final int REVERSE_BUTTON = 1;
		public static final int SLOW_BUTTON = 2;
		public static final int STRAIGHT_BUTTON = 3;
	}

	public static class DRIVE {

		public static final double JOY_TOL = 0.05;
		public static final double WHEEL_DIAMETER = 4;
		public static final double CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
		public static final double RPM_MAX = 840.0;
		public static final double MAX_VEL = (RPM_MAX / 60) * (CIRCUMFERENCE); // Inches per second
		public static final double DRIVE_P = 1.0 * ((0.1 * 1023.0) / (300.00));
		public static final double DRIVE_I = DRIVE_P / 100.0;
		public static final double DRIVE_D = 15 * DRIVE_P;
		public static final double DRIVE_F = (1023.0 / ((RPM_MAX / 60.0 / 10.0) * 4096.0));

		public static final double DRIVE_FOLLOWER_P = 1.52;
		public static final double DRIVE_FOLLOWER_V = 0;
		public static final double DRIVE_FOLLOWER_A = 0;
		public static final double DRIVE_FOLLOWER_ANG = 0;
		public static final double DRIVE_FOLLOWER_DIST_TOL = 0.1;
		public static final double DRIVE_FOLLOWER_ANG_TOL = 0.1;

		public static final int CODES_PER_REV = 4096;
	}

	public static class Log {

		public static final String LEFT_PATH_LOG_DIR = "/home/lvuser/LEFT_PATH_LOG.java";
		public static final String RIGHT_PATH_LOG_DIR = "/home/lvuser/RIGHT_PATH_LOG.java";
	}

}
