package frc.team1836.robot.auto.paths;

import frc.team1836.robot.Constants.DRIVE;
import frc.team254.lib.trajectory.Path;
import frc.team254.lib.trajectory.PathGenerator;
import frc.team254.lib.trajectory.TrajectoryGenerator;
import frc.team254.lib.trajectory.WaypointSequence;

public class CreatePath {

	public Path getPath(double[][] points) {
		double ti = System.nanoTime();
		TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
		config.dt = .005;
		config.max_acc = 19;
		config.max_jerk = 18;
		config.max_vel = DRIVE.MAX_VEL;
		final double kWheelbaseWidth = 30;
		final String path_name = "AutoPath";
		WaypointSequence p = new WaypointSequence(10);
		for (double[] point : points) {
			p.addWaypoint(new WaypointSequence.Waypoint(point[0], point[1], Math.toRadians(point[2])));
			System.out.println("X: " + point[0] + " Y: " + point[1] + " Angle: " + Math.toRadians(point[2]));
		}
		Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
		System.out.println(Math.round((System.nanoTime() - ti)) * 1e-6 + " MS");
		return path;
	}
}
