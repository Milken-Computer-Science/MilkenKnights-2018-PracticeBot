package frc.team254.lib.trajectory;

import frc.team254.lib.trajectory.Trajectory.Segment;

/**
 * DRIVE + Feedforward controller for following a Trajectory.
 *
 * @author Jared341
 */
public class TrajectoryFollower {

	double Dt;
	private double kp_;
	private double kv_;
	private double kAng_;
	private double ka_;
	private double last_error_;
	private double last_Ang_error;
	private double current_heading = 0;
	private Trajectory profile_;
	private Segment[] logSegments;
	private double lastTime;
	private double _DistTol;
	private double _AngTol;
	private int current_segment = 0;


	public TrajectoryFollower(Trajectory profile, double distTol, double angTol) {
		profile_ = profile;
		_DistTol = distTol;
		_AngTol = angTol;
		logSegments = new Segment[profile_.getNumSegments()];
		Dt = System.nanoTime();
	}

	public void configure(double kp, double kv, double ka, double kAng) {
		kp_ = kp;
		kv_ = kv;
		ka_ = ka;
		kAng_ = kAng;
	}

	public void reset() {
		last_error_ = 0.0;
	}


	/*
	 * @param gyroHeading is the gyro heading to account for angle
	 */
	public double calculate(double distance_so_far, double vel, double gyroHeading) {
		current_segment = (int) (customRound((System.nanoTime() - Dt) * 1e-9) / 0.005);
		if (current_segment < profile_.getNumSegments()) {

			Trajectory.Segment segment = profile_.getSegment(current_segment);
			double error = segment.pos - distance_so_far;
			double angError = segment.heading - gyroHeading;
			double desired = (angError * kAng_) + segment.vel;
			double output = desired + kp_ * error + kv_ * (vel - segment.vel) + ka_ * segment.acc;
			last_Ang_error = angError;
			last_error_ = error;
			current_heading = segment.heading;

			double jerk, accel, changeTime;
			if (current_segment != 0) {
				changeTime = (System.nanoTime() - lastTime) * 1e-9;
				accel = (vel - logSegments[current_segment - 1].vel) / changeTime;
				jerk = (accel - logSegments[current_segment - 1].acc) / changeTime;
			} else {
				changeTime = 0;
				accel = 0;
				jerk = 0;
			}

			lastTime = System.nanoTime();
			logSegments[current_segment] = new Segment(distance_so_far, vel, accel, jerk, gyroHeading,
					changeTime, segment.x, segment.y);
			return output;
		} else {
			return 0;
		}
	}

	public double customRound(double num) {
		return Math.round(num * 200) / 200.0;
	}

	public double getHeading() {
		return current_heading;
	}

	public boolean isFinishedTrajectory() {
		return current_segment >= profile_.getNumSegments();
	}

	public boolean onTarget() {
		return last_error_ < _DistTol && last_Ang_error < _AngTol;
	}

	public Trajectory getLog() {
		return new Trajectory(logSegments);
	}

}
