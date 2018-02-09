package org.usfirst.frc.team4959.robot.commands.Elevator;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.States;

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
		States.elevatorState = States.ElevatorStates.pidControl;

		if (pos == Constants.ELEVATOR_BOTTOM_ELEVATION)
			States.elevatorPosState = States.ElevatorPosStates.bottom;
		else if (pos == Constants.ELEVATOR_SWITCH_ELEVATION)
			States.elevatorPosState = States.ElevatorPosStates.switchPos;
		else if (pos == Constants.ELEVATOR_LOW_SCALE_ELEVATION)
			States.elevatorPosState = States.ElevatorPosStates.scaleLow;
		else if (pos == Constants.ELEVATOR_MID_SCALE_ELEVATION)
			States.elevatorPosState = States.ElevatorPosStates.scaleMid;
		else if (pos == Constants.ELEVATOR_HIGH_SCALE_ELEVATION)
			States.elevatorPosState = States.ElevatorPosStates.scaleHigh;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("SetElevatorPosition:Execute: trying to go to position.");
		System.out.println("Position: " + Robot.elevator.getPosition() + " / " + pos);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Robot.m_oi.getLeftStickYCont2() > 0.15 || Robot.m_oi.getLeftStickYCont2() < -0.15) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.stopElevator();
		Robot.elevator.moveElevator.pos = Robot.elevator.getPosition(); // Stores last position to maintain it during
																		// teleop
		States.elevatorState = States.ElevatorStates.joystickControl;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
