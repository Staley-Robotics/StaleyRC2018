package org.usfirst.frc.team4959.robot.commands.Drive;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sends Joystick values to DriveTrain. Substance.
 */
public class JoystickDrive extends Command {

	private DriveTrain driveTrain;

	public JoystickDrive() {
		requires(Robot.driveTrain);

		driveTrain = Robot.driveTrain;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		driveTrain.tankDrive(Robot.m_oi.getLeftStickY(), Robot.m_oi.getRightStickY());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
