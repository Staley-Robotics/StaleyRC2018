package org.usfirst.frc.team4959.robot.commands.Shifter;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
 
/**
 * Shifts the gearbox into low
 */
public class ShifterOff extends Command {

	public ShifterOff() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.pneumatics.shifterOff();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Shifter off end");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
