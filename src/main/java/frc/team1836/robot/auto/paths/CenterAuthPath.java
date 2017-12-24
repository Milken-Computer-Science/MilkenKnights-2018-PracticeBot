package frc.team1836.robot.auto.paths;

import frc.team254.lib.trajectory.Path;
import frc.team254.lib.trajectory.PathGenerator;
import frc.team254.lib.trajectory.TrajectoryGenerator;
import frc.team254.lib.trajectory.WaypointSequence;

public class CenterAuthPath {

	public Path getPath() {
		double ti = System.nanoTime();
		TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
		config.dt = .005;
		config.max_acc = 10.0;
		config.max_jerk = 60.0;
		config.max_vel = 15.0;

		final double kWheelbaseWidth = 25.5 / 12;

		// Path name must be a valid Java class name.
		final String path_name = "AutoPath";
		WaypointSequence p = new WaypointSequence(10);
		p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
		p.addWaypoint(new WaypointSequence.Waypoint(7.0, 0, 0));
		p.addWaypoint(new WaypointSequence.Waypoint(14.0, 1.0, Math.PI / 12.0));

		Path pa = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);
		System.out.println(Math.round((System.nanoTime() - ti)) * 1e-6 + " MS");
		return pa;
	}
}
