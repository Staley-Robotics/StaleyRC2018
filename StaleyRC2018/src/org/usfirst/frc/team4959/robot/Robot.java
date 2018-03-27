/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4959.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.AutoBrettV5;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.CenterToSwitch;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.LeftToSwitch;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.LeftToScale;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.RightToSwitch;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.RightToScale;
import org.usfirst.frc.team4959.robot.commands.Elevator.DisableSoftLimits;
import org.usfirst.frc.team4959.robot.commands.Elevator.EnableSoftLimits;
import org.usfirst.frc.team4959.robot.commands.Elevator.ZeroElevator;
import org.usfirst.frc.team4959.robot.commands.Intake.SetPivotPosition;
import org.usfirst.frc.team4959.robot.subsystems.Climber;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4959.robot.subsystems.Elevator;
import org.usfirst.frc.team4959.robot.subsystems.Intake;
import org.usfirst.frc.team4959.robot.subsystems.LightDriveLEDController;
import org.usfirst.frc.team4959.robot.subsystems.Pneumatics;
import org.usfirst.frc.team4959.robot.util.AutoControl;
import org.usfirst.frc.team4959.robot.util.CollisionDetection;
import org.usfirst.frc.team4959.robot.util.LiveVariableStory;
import org.usfirst.frc.team4959.robot.util.States;

import com.mach.LightDrive.Color;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 * 
 * Tyler sux at building.
 */
public class Robot extends TimedRobot {
	
	@SuppressWarnings("unused")
	private final String TAG = ("Robot" + ": ");
	
	public static boolean isEnabled = false;

	// ***** Subsystems *****
	public static DriveTrain driveTrain;
	public static OI m_oi;
	public static Pneumatics pneumatics;
	public static Intake intake;
	public static Elevator elevator;
	public static Climber climber;
//	public static LightDriveLEDController ldrive;
	private UsbCamera camera;
	
	CollisionDetection collisionDetection;
	
	public static boolean pivotControl;
	public static SetPivotPosition lowerPivotForAuto;

	Command m_autonomousCommand;
	public static SendableChooser<String> m_chooser = new SendableChooser<>();
	public static SendableChooser<AutoControl.ToScalePreferences> scalePreferenceChooser = new SendableChooser<>();
	public static SendableChooser<AutoControl.ToSwitchPreferences> switchPreferenceChooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		driveTrain = new DriveTrain();
		pneumatics = new Pneumatics();
		intake = new Intake();
		elevator = new Elevator();
		climber = new Climber();
//		ldrive = new LightDriveLEDController();
		
		States.resetStates();

		elevator.zeroPosition();
		
		AutoControl.setDefaultModes();

		// Add a list of autonomous modes to choose from to the Smart Dashboard
		m_chooser.addDefault("Delay", "Delay");
		m_chooser.addObject("Auto Brett V5", "AutoBrettV5");
		m_chooser.addObject("Center To Switch", "CenterToSwitch");
		m_chooser.addObject("Left To Scale", "LeftToScale");
		m_chooser.addObject("Left To Switch", "LeftToSwitch");
		m_chooser.addObject("Right To Scale", "RightToScale");
		m_chooser.addObject("Right To Switch", "RightToSwitch");
		SmartDashboard.putData("Auto mode", m_chooser);
		
		scalePreferenceChooser.addDefault("Can't Go To Switch", AutoControl.ToScalePreferences.noGoToSwitch);
		scalePreferenceChooser.addObject("Can Go To Switch", AutoControl.ToScalePreferences.canGoToSwitch);
		SmartDashboard.putData("To Scale Preference", scalePreferenceChooser);
		
		switchPreferenceChooser.addDefault("Can't Go To Scale", AutoControl.ToSwitchPreferences.noGoToScale);
		switchPreferenceChooser.addObject("Can Go To Scale", AutoControl.ToSwitchPreferences.canGoToScale);
		SmartDashboard.putData("To Switch Auto Prefence", switchPreferenceChooser);
		
		SmartDashboard.putData("Gyro", new GyroTurning(90, 6));
		
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(360, 270);
		camera.setFPS(15);
		
		SmartDashboard.putData("Zero Elevator", new ZeroElevator());
		isEnabled = false;
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		isEnabled = false;
		//LiveVariableStory.pos = 0;
		elevator.stopElevator();
		
		//m_oi.cancelPositionCommands();
		//elevator.disableTalonPIDsetPoint();
	}

	@Override
	public void disabledPeriodic() {
//		ldrive.updateLEDs();
		
		isEnabled = false;
		LiveVariableStory.pos = Robot.elevator.getPosition();
		
		SmartDashboard.putNumber("Left Encoder: ", driveTrain.getLeftEncoderDistance());
		SmartDashboard.putNumber("Right Encoder: ", driveTrain.getRightEncoderDistance());
		SmartDashboard.putNumber("Elevator Encoder: ", elevator.getPosition());
		SmartDashboard.putNumber("Intake Pivot Encoder: ", intake.getPivotEncoderDistance());
		SmartDashboard.putNumber("Gyro Yaw: ", driveTrain.getYaw());
		SmartDashboard.putBoolean("NavX Connection: ", driveTrain.isNavxConnected());
		SmartDashboard.putString("Elevator Control State: ", States.elevatorControlState.toString());
		SmartDashboard.putString("Shifter State: ", States.shifterState.toString());
		SmartDashboard.putString("Intake Claw State: ", States.intakeClawState.toString());
		SmartDashboard.putString("Elevator Position State: ", States.elevatorPosState.toString());
		SmartDashboard.putNumber("First Elevator Motor Power: ", elevator.getTalonOneMotorPower());
		SmartDashboard.putNumber("Second Elevator Motor Power: ", elevator.getTalonTwoMotorPower());
		SmartDashboard.putString("Elevator Fwd Soft Limit State: ", States.elevatorFwdSoftLimitState.toString());
		SmartDashboard.putString("Elevator Rev Soft Limit State: ", States.elevatorRevSoftLimitState.toString());
		SmartDashboard.putBoolean("Is Enabled: ", isEnabled);
		SmartDashboard.putBoolean("Limit Switch", elevator.getLimitSwitch());
		
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		isEnabled = true;
		
		driveTrain.shifterOn();
		intake.closeIntake();
		elevator.zeroPosition();
//		lowerPivotForAuto = new SetPivotPosition(intake.getPivotEncoderDistance() - 404, -1);
		
		// Sets preferences for secondary options in auto
		AutoControl.setToScalePreference(scalePreferenceChooser.getSelected());
		
		// Sets the selected auto mode
		if (m_chooser.getSelected().equalsIgnoreCase("Delay")) {
			AutoControl.setAutomode(AutoControl.AutoModes.delay);
		} if (m_chooser.getSelected().equalsIgnoreCase("AutoBrettV5")) {
			AutoControl.setAutomode(AutoControl.AutoModes.autoBrettV5);
		} else if (m_chooser.getSelected().equalsIgnoreCase("CenterToSwitch")) {
			AutoControl.setAutomode(AutoControl.AutoModes.centerToSwitch);
		} else if (m_chooser.getSelected().equalsIgnoreCase("LeftToScale")) {
			AutoControl.setAutomode(AutoControl.AutoModes.leftToScale);
		} else if (m_chooser.getSelected().equalsIgnoreCase("LeftToSwitch")) {
			AutoControl.setAutomode(AutoControl.AutoModes.leftToSwitch);
		} else if (m_chooser.getSelected().equalsIgnoreCase("RightToScale")) {
			AutoControl.setAutomode(AutoControl.AutoModes.rightToScale);
		} else if (m_chooser.getSelected().equalsIgnoreCase("RightToSwitch")) {
			AutoControl.setAutomode(AutoControl.AutoModes.rightToSwitch);
		}
		
		if (switchPreferenceChooser.getSelected() == AutoControl.ToSwitchPreferences.canGoToScale) {
			AutoControl.toSwitchPreference = AutoControl.ToSwitchPreferences.canGoToScale;
		} else {
			AutoControl.toSwitchPreference = AutoControl.ToSwitchPreferences.noGoToScale;
		}
		
		if (scalePreferenceChooser.getSelected() == AutoControl.ToScalePreferences.canGoToSwitch) {
			AutoControl.toScalePreference = AutoControl.ToScalePreferences.canGoToSwitch;
		} else {
			AutoControl.toScalePreference = AutoControl.ToScalePreferences.noGoToSwitch;
		}
						
		System.out.println("Chosen Mode AutoInit: " + m_chooser.getSelected().toString());

		// Sets the autonomousCommand to the selected auto mode
		if (AutoControl.autoMode == AutoControl.AutoModes.delay) {
			m_autonomousCommand = new Delay(15);
			System.out.println("Running Delay");
		} else if (AutoControl.autoMode == AutoControl.AutoModes.autoBrettV5) {
			m_autonomousCommand = new AutoBrettV5();
			System.out.println("Running AutoBrettV5");
		} else if (AutoControl.autoMode == AutoControl.AutoModes.centerToSwitch) {
			m_autonomousCommand = new CenterToSwitch();
			System.out.println("Running Center To Switch");
		} else if (AutoControl.autoMode == AutoControl.AutoModes.leftToScale) {
			m_autonomousCommand = new LeftToScale();
			System.out.println("Running Left To Scale");
		} else if (AutoControl.autoMode == AutoControl.AutoModes.leftToSwitch) {
			m_autonomousCommand = new LeftToSwitch();
			System.out.println("Running Left To Switch");
		} else if (AutoControl.autoMode == AutoControl.AutoModes.rightToScale) {
			m_autonomousCommand = new RightToScale();
			System.out.println("Running Right To Scale");
		} else if (AutoControl.autoMode == AutoControl.AutoModes.rightToSwitch) {
			m_autonomousCommand = new RightToSwitch();
			System.out.println("Running Right To Switch");
		}
//		pivotControl = true;
		
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
//		if (pivotControl) {
//			lowerPivotForAuto.start();
//			pivotControl = false;
////			(new DisableSoftLimits()).start();
//		}
		
//		ldrive.updateLEDs();
		SmartDashboard.putNumber("Elevator Encoder: ", elevator.getPosition());
	}

	@Override
	public void teleopInit() {
		isEnabled = true;
		
		intake.zeroPivotEncoder();
		
//		m_oi.cancelPositionCommands();

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		driveTrain.resetEncoders();
		

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
//		elevator.disableTalonPIDsetPoint();
		collisionDetection = new CollisionDetection();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		SmartDashboard.putNumber("Left Encoder: ", driveTrain.getLeftEncoderDistance());
		SmartDashboard.putNumber("Right Encoder: ", driveTrain.getRightEncoderDistance());
		SmartDashboard.putNumber("Elevator Encoder: ", elevator.getPosition());
		SmartDashboard.putNumber("Intake Pivot Encoder: ", intake.getPivotEncoderDistance());
		SmartDashboard.putNumber("Gyro Yaw: ", driveTrain.getYaw());
		SmartDashboard.putBoolean("NavX Connection: ", driveTrain.isNavxConnected());
		SmartDashboard.putString("Elevator Control State: ", States.elevatorControlState.toString());
		SmartDashboard.putString("Shifter State: ", States.shifterState.toString());
		SmartDashboard.putString("Intake Claw State: ", States.intakeClawState.toString());
		SmartDashboard.putString("Elevator Position State: ", States.elevatorPosState.toString());
		SmartDashboard.putNumber("First Elevator Motor Power: ", elevator.getTalonOneMotorPower());
		SmartDashboard.putNumber("Second Elevator Motor Power: ", elevator.getTalonTwoMotorPower());
		SmartDashboard.putString("Elevator Fwd Soft Limit State: ", States.elevatorFwdSoftLimitState.toString());
		SmartDashboard.putString("Elevator Rev Soft Limit State: ", States.elevatorRevSoftLimitState.toString());
		SmartDashboard.putBoolean("Is Enabled: ", isEnabled);
		SmartDashboard.putBoolean("Limit Switch", elevator.getLimitSwitch());
		
		SmartDashboard.putNumber("TalonSRX One Current: ", elevator.getTalonOneCurrent());
		SmartDashboard.putNumber("TalonSRX Two Current: ", elevator.getTalonTwoCurrent());
		
		SmartDashboard.putData(new DisableSoftLimits());
		SmartDashboard.putData(new EnableSoftLimits());
		
//		ldrive.updateLEDs();

		if (collisionDetection.collisionDetector()) {
			m_oi.xboxController.setRumble(RumbleType.kRightRumble, 0.75);
			m_oi.xboxController.setRumble(RumbleType.kLeftRumble, 0.8);
		} else {
			m_oi.xboxController.setRumble(RumbleType.kRightRumble, 0);
			m_oi.xboxController.setRumble(RumbleType.kLeftRumble, 0);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		isEnabled = true;

		LiveWindow.add(driveTrain);
	}
}
