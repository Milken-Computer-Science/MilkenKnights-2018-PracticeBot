package frc.team1836.robot.auto.actions;

import frc.team1836.robot.subsystems.Drive;
import frc.team254.lib.trajectory.Path;

public class DrivePathAction implements Action {

	private Path mPath;

	public DrivePathAction(Path p) {
		this.mPath = p;

	}

	@Override
	public boolean isFinished() {
		return Drive.getInstance().trajectoryFinished();
	}

	@Override
	public void update() {
		// Nothing done here, controller updates in mEnabedLooper in robot
	}

	@Override
	public void done() {
		Drive.getInstance().logPath();
	}

	@Override
	public void start() {
		Drive.getInstance().setPathFollower(mPath);
		System.out.println("Start Path");
	}
}
