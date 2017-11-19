package org.usfirst.frc.team1836.robot.util;

public class DriveHelper {

	private static final double kThrottleDeadband = 0;
	private static final double kWheelDeadband = 0;

	public static DriveSignal cheesyDrive(double throttle, double wheel, boolean isSlow) {

		double leftMotorSpeed;
		double rightMotorSpeed;

		double moveValue = limit(throttle);
		double rotateValue = limit(wheel);

		if (isSlow) {
			moveValue = moveValue / 2;
			rotateValue = rotateValue / 2;
		}

		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			} else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}
		return new DriveSignal(leftMotorSpeed, rightMotorSpeed);
	}


	protected static double limit(double num) {
		if (num > 1.0) {
			return 1.0;
		}
		if (num < -1.0) {
			return -1.0;
		}
		return num;
	}

	public double handleDeadband(double val, double deadband) {
		return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	}
}
