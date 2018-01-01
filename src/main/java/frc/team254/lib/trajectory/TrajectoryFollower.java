package frc.team254.lib.trajectory;

import frc.team1836.robot.util.state.TrajectoryStatus;
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
	private int last_seg = 0;
	private boolean ranOnce = false;

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
	 * @param heading is the gyro heading to account for angle
	 */
	public TrajectoryStatus calculate(double dist, double vel, double heading) {
		current_segment = (int) (customRound((System.nanoTime() - Dt) * 1e-9) / 0.005);
		if (current_segment < profile_.getNumSegments()) {
			Trajectory.Segment segment = profile_.getSegment(current_segment);
			double error = segment.pos - dist;
			double angError = segment.heading - heading;
			double desired = (angError * kAng_) + segment.vel;
			double output = desired + kp_ * error + kv_ * (segment.vel - vel) + ka_ * segment.acc;
			last_Ang_error = angError;
			last_error_ = error;
			current_heading = segment.heading;

			double jerk, accel, changeTime;
			if (current_segment != 0 && ranOnce) {
				changeTime = (System.nanoTime() - lastTime) * 1e-9;
				accel = (vel - logSegments[last_seg].vel) / changeTime;
				jerk = (accel - logSegments[last_seg].acc) / changeTime;
			} else {
				changeTime = 0;
				accel = 0;
				jerk = 0;
			}
			last_seg = current_segment;
			lastTime = System.nanoTime();
			logSegments[current_segment] = new Segment(dist, vel, accel, jerk, heading, changeTime, segment.x, segment.y);
			ranOnce = true;

			return new TrajectoryStatus(segment, error, segment.vel - vel, angError, output);
		} else {
			return new TrajectoryStatus(new Segment(0, 0, 0, 0, 0, 0, 0, 0), 0, 0, 0, 0);
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
