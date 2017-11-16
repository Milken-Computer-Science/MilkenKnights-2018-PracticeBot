package org.usfirst.frc.team1836.robot;

import org.usfirst.frc.team1836.robot.util.InterpolatingDouble;
import org.usfirst.frc.team1836.robot.util.PolynomialRegression;

/**
 * Stores all Hard-coded numbers subject to change
 */
public final class Constants {

	public static class Hardware {

		public static final int LEFT_FWD_TALON_ID = 1;
		public static final int LEFT_BACK_TALON_ID = 2;
		public static final int RIGHT_FWD_TALON_ID = 8;
		public static final int RIGHT_BACK_TALON_ID = 7;
		public static final int SHOOTER_TALON_ID = 5;
		public static final int CLIMBER_TALON_ID = 6;
		public static final boolean SHOOTER_REVERSE = false;
		public static final boolean SHOOTER_SENSOR_REVERSE = false;
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
		public static final int CHEESE_BUTTON = 2;
		public static final int STRAIGHT_BUTTON = 3;

		public static final int GEAR_PICKUP_BUTTON = 2;
		public static final int GEAR_STOW_BUTTON = 3;
		public static final int GEAR_PLACE_BUTTON = 4;
		public static final int ROLLER_IN_BUTTON = 10;
		public static final int ROLLER_OUT_BUTTON = 5;
		public static final int CLIMBER_FWD_BUTTON = 11;
		public static final int GEAR_MANUAL_BUTTON = 1;
		public static final int GEAR_RESET_BUTTON = 6;
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


	public static class Shooter {

		public static final double kP = 0;
		public static final double kI = 0;
		public static final double kD = 0;
		public static final double kF = (1023) / ((4096) * (4000 / 60 / 10));

		public static PolynomialRegression kFlywheelAutoAimPolynomial;
		public static double[][] kFlywheelDistanceRpmValues = {
				// At champs 4/27
				{ 90.0, 2890.0 },
				{ 95.0, 2940.0 },
				{ 100.0, 2990.0 },
				{ 105.0, 3025.0 },
				{ 110.0, 3075.0 },
				{ 115.0, 3125.0 },
				{ 120.0, 3175.0 },
				{ 125.0, 3225.0 },
				{ 130.0, 3275.0 },
		};

		static {
			kFlywheelAutoAimPolynomial = new PolynomialRegression(kFlywheelDistanceRpmValues, 2);
		}

	}


	public static class Auto {

		public static final double CENTER_AUTO_DISTANCE = 90;
		public static final double CENTER_AUTO_SPEED = 0.6;
		public static final double CENTER_AUTO_TIME = 3.75;
		public static final double BASELINE_TIME = 4;
	}


	public static class Climb {

		public static final double CLIMBER_SPEED = 0.95;
	}

	public static class Log {

		public static final String PATH_LOG_DIR = "/home/lvuser/PATH_LOG.java";
	}

}
