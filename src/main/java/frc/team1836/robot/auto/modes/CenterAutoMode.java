package frc.team1836.robot.auto.modes;

import frc.team1836.robot.auto.AutoModeBase;
import frc.team1836.robot.auto.AutoModeEndedException;
import frc.team1836.robot.auto.actions.DrivePathAction;
import frc.team1836.robot.auto.actions.WaitAction;
import frc.team1836.robot.auto.paths.CenterAuthPath;

public class CenterAutoMode extends AutoModeBase {

	@Override
	protected void routine() throws AutoModeEndedException {
		CenterAuthPath path = new CenterAuthPath();
		runAction(new DrivePathAction(path.getPath()));
	}
}
