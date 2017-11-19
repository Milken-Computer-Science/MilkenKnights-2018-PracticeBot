package org.usfirst.frc.team1836.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1836.robot.auto.AutoModeBase;
import org.usfirst.frc.team1836.robot.auto.AutoModeExecuter;
import org.usfirst.frc.team1836.robot.auto.modes.StandStillMode;
import org.usfirst.frc.team1836.robot.loops.Looper;
import org.usfirst.frc.team1836.robot.subsystems.Climber;
import org.usfirst.frc.team1836.robot.subsystems.Drive;
import org.usfirst.frc.team1836.robot.subsystems.Shooter;
import org.usfirst.frc.team1836.robot.util.CrashTracker;

import java.util.Arrays;

public class Robot extends IterativeRobot {

	private final SubsystemManager mSubsystemManager = new SubsystemManager(
			Arrays.asList(Drive.getInstance(), Climber.getInstance(), Shooter.getInstance()));
	private Looper mEnabledLooper = new Looper();
	private SendableChooser<AutoModeBase> chooser = new SendableChooser<>();
	private AutoModeExecuter mAutoModeExecuter = null;
	private UsbCamera mCamera;
	@Override
	public void robotInit() {
		try {
			CrashTracker.logRobotInit();
			mSubsystemManager.registerEnabledLoops(mEnabledLooper);
			mCamera = CameraServer.getInstance().startAutomaticCapture();
			mCamera.setResolution(320,240);
			mCamera.setFPS(30);
			mCamera.setExposureAuto();
			mCamera.setWhiteBalanceAuto();
			chooser.addObject("No Auto", new StandStillMode());
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
			InputController.updateInputs();
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
