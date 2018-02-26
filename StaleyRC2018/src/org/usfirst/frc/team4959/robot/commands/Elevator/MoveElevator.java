package org.usfirst.frc.team4959.robot.commands.Elevator;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.Elevator;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.LiveVariableStory;
import org.usfirst.frc.team4959.robot.util.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the elevator using user input of the left joy-stick on controller 2
 */
public class MoveElevator extends Command {
	
	private final String TAG = (this.getName() + ": ");

	Elevator elevator;

	public MoveElevator() {
		requires(Robot.elevator);
		elevator = Robot.elevator;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (States.elevatorControlState == States.ElevatorControlStates.joystickControl) {
			if (Robot.m_oi.getLeftStickYCont2() > Constants.JOYSTICK_Y_AXIS_DEADZONE) {
				States.elevatorPosState = States.ElevatorPosStates.userControl; // Verifies that the robot is being controlled by the user
				elevator.moveElevator(Robot.m_oi.getLeftStickYCont2());
				System.out.println(TAG + " Joystickvalue Pos: " + Robot.m_oi.getLeftStickYCont2());
				LiveVariableStory.pos = elevator.getPosition();
				
				System.out.println(TAG + "Going up");
			} else if (Robot.m_oi.getLeftStickYCont2() < -Constants.JOYSTICK_Y_AXIS_DEADZONE) {
				States.elevatorPosState = States.ElevatorPosStates.userControl; // Verifies that the robot is being	 controlled by the user
				elevator.moveElevator(Robot.m_oi.getLeftStickYCont2());
				System.out.println(TAG + " Joystickvalue Neg: " + Robot.m_oi.getLeftStickYCont2());
				LiveVariableStory.pos = elevator.getPosition();
				
				System.out.println(TAG + "Going down");
			} else {
				States.elevatorPosState = States.ElevatorPosStates.positionHeld;
//				elevator.stopElevator();
				
				if (LiveVariableStory.pos < Constants.REV_SOFT_LIMIT) {
					LiveVariableStory.pos = 0;
				}
				elevator.setPosition(LiveVariableStory.pos); // To help maintain the elevator's position
				
//				System.out.println(TAG + "Staying put at "  + LiveVariableStory.pos);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		elevator.stopElevator();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		//System.out.println(TAG + "Move Elevator interupted");
		end();
	}
}
