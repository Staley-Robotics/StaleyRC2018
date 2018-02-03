package org.usfirst.frc.team4959.robot.commands.Elevator;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.util.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the elevator using user input of the left joy-stick on controller 2
 */
public class MoveElevator extends Command {

	public MoveElevator() {
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (States.elevatorState == States.ElevatorStates.joystickControl) {
			if (Robot.m_oi.getLeftStickYCont2() > 0.15 || Robot.m_oi.getLeftStickYCont2() < -0.15) {
				Robot.elevator.moveElevator(Robot.m_oi.getLeftStickYCont2());
			}
			else {
				Robot.elevator.stopElevator();
			}
		}
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
