/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4959.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.AutoBrettV5;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.CenterSwitch;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.LeftSwitch;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.LeftToScale;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.RightSwitch;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoModes.RightToScale;
import org.usfirst.frc.team4959.robot.subsystems.Climber;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4959.robot.subsystems.Elevator;
import org.usfirst.frc.team4959.robot.subsystems.Intake;
import org.usfirst.frc.team4959.robot.subsystems.Pneumatics;
import org.usfirst.frc.team4959.robot.util.CollisionDetection;
import org.usfirst.frc.team4959.robot.util.States;

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

	// ***** Subsystems *****
	public static DriveTrain driveTrain;
	public static OI m_oi;
	public static Pneumatics pneumatics;
	public static Intake intake;
	public static Elevator elevator;
	public static Climber climber;
	
	CollisionDetection collisionDetection;
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

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
		
		States.resetStates();

		driveTrain.resetNavx();
		
		// Add a list of autonomous modes to choose from to the Smart Dashboard
		m_chooser.addDefault("Delay", new Delay(15));
		m_chooser.addObject("Auto Brett V5", new AutoBrettV5());
		m_chooser.addObject("Left Switch", new LeftSwitch());
		m_chooser.addObject("CenterToSwitch", new CenterSwitch());
		m_chooser.addObject("Right Switch", new RightSwitch());
		m_chooser.addObject("Right To Scale", new RightToScale());
		m_chooser.addObject("Left To Scale", new LeftToScale());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		elevator.stopElevator();
	}

	@Override
	public void disabledPeriodic() {
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
		driveTrain.shifterOff();
		intake.closeIntake();
		elevator.zeroPosition();
		driveTrain.resetNavx();

		m_autonomousCommand = m_chooser.getSelected();

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
		//System.out.print(driveTrain.getTrueAngle());
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		driveTrain.resetNavx();
		driveTrain.resetEncoders();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		collisionDetection = new CollisionDetection();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		SmartDashboard.putNumber("Left Encoder: ", driveTrain.getLeftEncoderDistance());
		SmartDashboard.putNumber("Right Encoder: ", driveTrain.getRightEncoderDistance());
		SmartDashboard.putNumber("Elevator Encoder: ", elevator.getPosition());
		SmartDashboard.putNumber("Gyro Yaw: ", driveTrain.getYaw());
		SmartDashboard.putString("Elevator State: ", States.elevatorState.toString());
		SmartDashboard.putString("Shifter State: ", States.shifterState.toString());
		SmartDashboard.putString("Intake Claw State: ", States.intakeClawState.toString());
		SmartDashboard.putString("Elevator Position State: ", States.elevatorPosState.toString());
		SmartDashboard.putNumber("Elevator Motor Power: ", elevator.getMotorPower());
		SmartDashboard.putBoolean("NavX Connection: ", driveTrain.isNavxConnected());
		
		if ( collisionDetection.collisionDetector()) {
			m_oi.xboxController.setRumble(RumbleType.kRightRumble, 0.5);
			m_oi.xboxController.setRumble(RumbleType.kLeftRumble, 0.5);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.add(driveTrain);
	}
}
