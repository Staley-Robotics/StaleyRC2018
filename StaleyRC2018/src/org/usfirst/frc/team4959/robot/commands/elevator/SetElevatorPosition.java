package org.usfirst.frc.team4959.robot.commands.elevator;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the elevator to a set point. 
 */
public class SetElevatorPosition extends Command {

	private double pos;

	public SetElevatorPosition(double pos) {
		this.pos = pos;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.setPosition(pos);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("SetElevatorPosition:Execute: trying to go to position.");
		System.out.println("Position: " + Robot.elevator.getPosition() + " / " + pos);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.stopElevator();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
