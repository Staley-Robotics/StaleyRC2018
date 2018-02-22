package org.usfirst.frc.team4959.robot.commands.Pneumatics;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the compressor automatically
 */
public class RunCompressor extends Command {
	
	private final String TAG = (this.getName() + ": ");

	public RunCompressor() {
		requires(Robot.pneumatics);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.pneumatics.runCompressor();
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
