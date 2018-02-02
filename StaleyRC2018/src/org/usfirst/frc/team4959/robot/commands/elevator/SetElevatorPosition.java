package org.usfirst.frc.team4959.robot.commands.elevator;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the elevators position to the set point.
 */
public class SetElevatorPosition extends Command {

	private double pos;
	private double threshold = 200;
	private boolean thresholdController;
	private static boolean endController = false;

	public SetElevatorPosition(double pos) {
		this.pos = pos;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (pos > Robot.elevator.getPosition()) {
			thresholdController = true;
		} else {
			thresholdController = false;
		}
		Robot.elevator.setPosition(pos);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("SetElevatorPosition:Execute: trying to go to position.");
		System.out.println("Position: " + Robot.elevator.getPosition() + " / " + pos);
		System.out.println("Talon is Alive: " + Robot.elevator.isAlive());
		System.out.println("Talon Speed: " + Robot.elevator.getSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		if (thresholdController) {
//			return (Robot.elevator.getPosition() >= (pos + threshold)) || (Robot.elevator.getPosition() >= (pos - threshold));
//		}
//		else {
//			return (Robot.elevator.getPosition() <= (pos + threshold) || (Robot.elevator.getPosition() <= (pos - threshold)));
//		}
		return endController;
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
