package frc.team1836.robot.auto.modes;

import frc.team1836.robot.auto.AutoModeBase;
import frc.team1836.robot.auto.AutoModeEndedException;
import frc.team1836.robot.auto.actions.DrivePathAction;
import frc.team1836.robot.auto.actions.WaitAction;

public class CenterAutoMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new DrivePathAction());
    }
}
