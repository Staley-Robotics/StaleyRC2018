package org.usfirst.frc.team4959.robot.commands.autoCommands.drive;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the robot from moving during autonomous
 */
public class Stopper extends Command {
	
	private DriveTrain driveTrain;

	public Stopper() {
		driveTrain = Robot.driveTrain;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.arcadeDrive(0, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		driveTrain.arcadeDrive(0, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		driveTrain.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
