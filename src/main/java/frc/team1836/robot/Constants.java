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
		public static final int CLIMBER_TALON_ID = 6;
		public static final boolean LEFT_FWD_TALON_SENSOR_REVERSE = true;
		public static final boolean RIGHT_FWD_TALON_SENSOR_REVERSE = false;
		public static final boolean LEFT_FWD_TALON_REVERSE = false;
		public static final boolean LEFT_BACK_TALON_REVERSE = false;
		public static final boolean RIGHT_FWD_TALON_REVERSE = false;
		public static final boolean RIGHT_BACK_TALON_REVERSE = false;
		public static double kLooperDt = 0.005;
	}
	
	public static class Input {

		public static final int DRIVE_STICK = 0;
		public static final int OPERATOR_STICK = 1;
		public static final int REVERSE_BUTTON = 1;
		public static final int SLOW_BUTTON = 2;
		public static final int STRAIGHT_BUTTON = 3;
		public static final int CLIMBER_FWD_BUTTON = 11;
	}

	public static class DRIVE {

		public static final double WHEEL_DIAMETER = 4;
		public static final double MAX_VEL = ((30) / (WHEEL_DIAMETER * Math.PI)) * 60; // RPM
		public static final double DRIVE_A = MAX_VEL * 0.75;
		public static final double DRIVE_V = MAX_VEL * 0.75;
		public static final int DRIVE_I_ZONE = 0;
		public static final double DRIVE_P = 0;
		public static final double DRIVE_I = 0;
		public static final double DRIVE_D = 0;
		public static final double DRIVE_F = 1.0461748973;

		public static final double DRIVE_FOLLOWER_P = 0;
		public static final double DRIVE_FOLLOWER_D = 0;
		public static final double DRIVE_FOLLOWER_ANG = 0;
		public static final double DRIVE_FOLLOWER_TOL = 1.5;
		public static final double DRIVE_FOLLOWER_ANG_TOL = 1.5;

		public static final int CODES_PER_REV = 4096;
	}

	public static class Climb {

		static final double CLIMBER_SPEED = 0.95;
	}

	public static class Log {

		public static final String PATH_LOG_DIR = "/home/lvuser/PATH_LOG.java";
	}

}
