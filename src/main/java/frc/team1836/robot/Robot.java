package frc.team1836.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1836.robot.auto.AutoModeBase;
import frc.team1836.robot.auto.AutoModeExecuter;
import frc.team1836.robot.auto.modes.CenterAutoMode;
import frc.team1836.robot.auto.modes.StandStillMode;
import frc.team1836.robot.subsystems.Drive;
import frc.team1836.robot.subsystems.LED;
import frc.team1836.robot.subsystems.Vision;
import frc.team1836.robot.util.logging.CrashTracker;
import frc.team1836.robot.util.loops.Looper;

import java.util.Arrays;

public class Robot extends IterativeRobot {

	private final SubsystemManager mSubsystemManager = new SubsystemManager(
			Arrays.asList(Drive.getInstance(), Vision.getInstance(), LED.getInstance()));
	private Looper mEnabledLooper = new Looper();
	private SendableChooser<AutoModeBase> chooser = new SendableChooser<>();
	private AutoModeExecuter mAutoModeExecuter = null;
	private InputController controller;

	@Override
	public void robotInit() {
		try {
			CrashTracker.logRobotInit();
			mSubsystemManager.registerEnabledLoops(mEnabledLooper);
			controller = new InputController();
			chooser.addObject("No Auto", new StandStillMode());
			chooser.addObject("Center Auto", new CenterAutoMode());
			SmartDashboard.putData("Auto mode", chooser);
		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
		mSubsystemManager.zeroSensors();
	}

	public void disabledInit() {
		try {
			CrashTracker.logDisabledInit();

			if (mAutoModeExecuter != null) {
				mAutoModeExecuter.stop();
			}
			mAutoModeExecuter = null;

			mEnabledLooper.stop();

			mSubsystemManager.stop();


		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}

	@Override
	public void autonomousInit() {
		try {
			CrashTracker.logAutoInit();
			System.out.println("Auto start timestamp: " + Timer.getFPGATimestamp());
			if (mAutoModeExecuter != null) {
				mAutoModeExecuter.stop();
			}
			mSubsystemManager.zeroSensors();
			mAutoModeExecuter = null;
			mEnabledLooper.start();
			mAutoModeExecuter = new AutoModeExecuter();
			mAutoModeExecuter.setAutoMode(chooser.getSelected());
			mAutoModeExecuter.start();
		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}

	@Override
	public void teleopInit() {
		try {
			CrashTracker.logTeleopInit();
			mEnabledLooper.start();
		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}

	public void testInit() {
		try {
			Drive.getInstance().checkSystem();
			mEnabledLooper.start();
		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}

	}

	@Override
	public void disabledPeriodic() {
		allPeriodic();
	}

	@Override
	public void autonomousPeriodic() {
		allPeriodic();
	}

	@Override
	public void teleopPeriodic() {
		try {
			controller.updateInputs();
			allPeriodic();
		} catch (Throwable t) {
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}

	private void allPeriodic() {
		mSubsystemManager.outputToSmartDashboard();
		mSubsystemManager.writeToLog();
		mEnabledLooper.outputToSmartDashboard();

	}

}
