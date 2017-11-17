package com.team254.lib.trajectory;

import com.team254.lib.trajectory.io.JavaSerializer;
import org.usfirst.frc.team1836.robot.Constants;

public class PathFollower {

    protected TrajectoryFollower lFollower;
    protected TrajectoryFollower rFollower;
    protected Path mPath;

    public PathFollower(Path mPath, double distTol, double angTol) {
        this.mPath = mPath;
        lFollower = new TrajectoryFollower(mPath.getLeftWheelTrajectory(), distTol, angTol);
        lFollower.configure(Constants.DRIVE.DRIVE_FOLLOWER_P, Constants.DRIVE.DRIVE_FOLLOWER_D,
                Constants.DRIVE.DRIVE_FOLLOWER_ANG);
        rFollower = new TrajectoryFollower(mPath.getRightWheelTrajectory(), distTol, angTol);
        rFollower.configure(Constants.DRIVE.DRIVE_FOLLOWER_P, Constants.DRIVE.DRIVE_FOLLOWER_D,
                Constants.DRIVE.DRIVE_FOLLOWER_ANG);
    }


    public double getLeftVelocity(double dist, double vel, double angle) {
        return lFollower.calculate(dist, vel, angle);
    }

    public double getRightVelocity(double dist, double vel, double angle) {
        return rFollower.calculate(dist, vel, angle);
    }


    public void saveLogTrajectory() {
        JavaSerializer serializer = new JavaSerializer();
        serializer.writeFile(Constants.Log.PATH_LOG_DIR, serializer.serialize(
                new Path(mPath.getName(), new Trajectory.Pair(lFollower.getLog(), rFollower.getLog()))));
    }


    public boolean getFinished() {
        return lFollower.isFinishedTrajectory() && rFollower.isFinishedTrajectory();
    }

    public boolean onTarget() {
        return lFollower.onTarget() && rFollower.onTarget();
    }


}
