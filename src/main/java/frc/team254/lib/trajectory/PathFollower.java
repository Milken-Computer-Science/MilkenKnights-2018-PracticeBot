package frc.team254.lib.trajectory;

import frc.team1836.robot.Constants;
import frc.team1836.robot.util.state.TrajectoryStatus;
import frc.team254.lib.trajectory.io.CSVSerializer;

public class PathFollower {

	protected TrajectoryFollower lFollower;
	protected TrajectoryFollower rFollower;
	protected Path mPath;

	public PathFollower(Path mPath, double distTol, double angTol) {
		this.mPath = mPath;
		lFollower = new TrajectoryFollower(mPath.getLeftWheelTrajectory(), distTol, angTol);
		lFollower
				.configure(Constants.DRIVE.DRIVE_FOLLOWER_P, Constants.DRIVE.DRIVE_FOLLOWER_V, Constants.DRIVE.DRIVE_FOLLOWER_A, Constants.DRIVE.DRIVE_FOLLOWER_ANG);
		rFollower = new TrajectoryFollower(mPath.getRightWheelTrajectory(), distTol, angTol);
		rFollower
				.configure(Constants.DRIVE.DRIVE_FOLLOWER_P, Constants.DRIVE.DRIVE_FOLLOWER_V, Constants.DRIVE.DRIVE_FOLLOWER_A, -Constants.DRIVE.DRIVE_FOLLOWER_ANG);
	}


	public TrajectoryStatus getLeftVelocity(double dist, double vel, double angle) {
		return lFollower.calculate(dist, vel, angle);
	}

	public TrajectoryStatus getRightVelocity(double dist, double vel, double angle) {
		return rFollower.calculate(dist, vel, angle);
	}


	public void saveLogTrajectory() {
		CSVSerializer serializer = new CSVSerializer();
		Path loggedPath = new Path(mPath.getName(), new Trajectory.Pair(lFollower.getLog(), rFollower.getLog()));
		serializer.writeFile(Constants.Log.LEFT_PATH_LOG_DIR, serializer.serialize(loggedPath));
		serializer.writeFile(Constants.Log.RIGHT_PATH_LOG_DIR, serializer.serializeRight(loggedPath));
	}


	public boolean getFinished() {
		return lFollower.isFinishedTrajectory() && rFollower.isFinishedTrajectory();
	}

	public boolean onTarget() {
		return lFollower.onTarget() && rFollower.onTarget();
	}


}
