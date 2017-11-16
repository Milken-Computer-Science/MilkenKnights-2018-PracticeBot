package org.usfirst.frc.team1836.robot.util;


/**
 * A drivetrain command consisting of the left, right motor settings and whether the brake mode is
 * enabled.
 */
public class DriveSignal {

	public static DriveSignal NEUTRAL = new DriveSignal(0, 0);
	protected double mLeftMotor;
	protected double mRightMotor;

	public DriveSignal(double left, double right) {
		mLeftMotor = left;
		mRightMotor = right;
	}


	public double getLeft() {
		return mLeftMotor;
	}

	public double getRight() {
		return mRightMotor;
	}

	@Override
	public String toString() {
		return "L: " + mLeftMotor + ", R: ";
	}
}
