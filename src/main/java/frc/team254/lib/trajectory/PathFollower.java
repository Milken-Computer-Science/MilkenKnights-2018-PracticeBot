package frc.team254.lib.trajectory;

import frc.team1836.robot.Constants;
import frc.team254.lib.trajectory.io.LeftCSVSerializer;
import frc.team254.lib.trajectory.io.RightCSVSerializer;

public class PathFollower {

	protected TrajectoryFollower lFollower;
	protected TrajectoryFollower rFollower;
	protected Path mPath;

	public PathFollower(Path mPath, double distTol, double angTol) {
		this.mPath = mPath;
		lFollower = new TrajectoryFollower(mPath.getLeftWheelTrajectory(), distTol, angTol);
		lFollower.configure(Constants.DRIVE.DRIVE_FOLLOWER_P, Constants.DRIVE.DRIVE_FOLLOWER_V,
				Constants.DRIVE.DRIVE_FOLLOWER_A,
				Constants.DRIVE.DRIVE_FOLLOWER_ANG);
		rFollower = new TrajectoryFollower(mPath.getRightWheelTrajectory(), distTol, angTol);
		rFollower.configure(Constants.DRIVE.DRIVE_FOLLOWER_P, Constants.DRIVE.DRIVE_FOLLOWER_V,
				Constants.DRIVE.DRIVE_FOLLOWER_A,
				-Constants.DRIVE.DRIVE_FOLLOWER_ANG);
	}


	public double getLeftVelocity(double dist, double vel, double angle) {
		return lFollower.calculate(dist, vel, angle, "Left");
	}

	public double getRightVelocity(double dist, double vel, double angle) {
		return rFollower.calculate(dist, vel, angle, "Right");
	}



	public void saveLogTrajectory() {
		LeftCSVSerializer lserializer = new LeftCSVSerializer();
		RightCSVSerializer rserializer = new RightCSVSerializer();
		lserializer.writeFile(Constants.Log.LEFT_PATH_LOG_DIR, lserializer.serialize(
				new Path(mPath.getName(), new Trajectory.Pair(lFollower.getLog(), rFollower.getLog()))));
		rserializer.writeFile(Constants.Log.RIGHT_PATH_LOG_DIR, rserializer.serialize(
				new Path(mPath.getName(), new Trajectory.Pair(lFollower.getLog(), rFollower.getLog()))));
	}


	public boolean getFinished() {
		return lFollower.isFinishedTrajectory() && rFollower.isFinishedTrajectory();
	}

	public boolean onTarget() {
		return lFollower.onTarget() && rFollower.onTarget();
	}


}
