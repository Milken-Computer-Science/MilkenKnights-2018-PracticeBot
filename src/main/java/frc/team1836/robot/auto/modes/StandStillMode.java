package frc.team1836.robot.auto.modes;


import frc.team1836.robot.auto.AutoModeBase;
import frc.team1836.robot.auto.AutoModeEndedException;

/**
 * Fallback for when all autonomous modes do not work, resulting in a frc.team1836.robot standstill
 */
public class StandStillMode extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        System.out.println("Starting Stand Still Mode... Done!");
    }
}
