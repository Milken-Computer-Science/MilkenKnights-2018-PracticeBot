package frc.team254.lib.trajectory.io;


import frc.team254.lib.trajectory.Path;
import frc.team254.lib.trajectory.Trajectory;
import frc.team254.lib.trajectory.Trajectory.Segment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Serializes a Path to a simple space and CR separated text file.
 * 
 * @author Jared341
 */
public class LeftCSVSerializer implements IPathSerializer {

	/**
	 * Format: PathName NumSegments LeftSegment1 ... LeftSegmentN RightSegment1 ...
	 * RightSegmentN
	 * 
	 * Each segment is in the format: pos vel acc jerk heading dt x y
	 * 
	 * @param path
	 *            The path to serialize.
	 * @return A string representation.
	 */
	@Override
	public String serialize(Path path) {
		path.goLeft();
		String content = "Position,Velocity,Acceleration,Jerk,Heading,DeltaTime,X,Y\n";
		content += serializeTrajectory(path.getLeftWheelTrajectory());
        return content;
	}

	private String serializeTrajectory(Trajectory trajectory) {
		String content = "";
		for (int i = 0; i < trajectory.getNumSegments(); ++i) {
			Segment segment = trajectory.getSegment(i);
			content += String.format("%.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f\n", segment.pos, segment.vel, segment.acc,
					segment.jerk, segment.heading, segment.dt * i, segment.x, segment.y);
		}
		return content;
	}

    public boolean writeFile(String path, String data) {
        try {
            File file = new File(path);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

}