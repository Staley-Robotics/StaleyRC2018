package org.usfirst.frc.team4959.robot.util;

/*
 * Contains the different states that the subsystems in the robot can be in. 
 */

public class States {
	
	public static ElevatorStates elevatorState;
	public static ElevatorPosStates elevatorPosState;
	public static ShifterStates shifterState;
	public static IntakeClawStates intakeClawState;
	
	// Tells us if the elevator is currently being controlled by player's joystick or the PID Controller
	public static enum ElevatorStates {
		joystickControl,
		pidControl;
	}
	
	// Tells us if the shifter is in low gear or high gear
	public static enum ShifterStates {
		low,
		high;
	}
	
	// Tells us if the in-take claw is open or closed
	public static enum IntakeClawStates {
		open,
		closed;
	}
	
	public static enum ElevatorPosStates {
		bottom, 
		switchPos, 
		scaleLow, scaleMid, scaleHigh,
		userControl;
	}
	
	// Resets all of the states
	public static void resetStates() {
		elevatorState = ElevatorStates.joystickControl;
		elevatorPosState = ElevatorPosStates.userControl;
		shifterState = ShifterStates.low;
		intakeClawState = IntakeClawStates.closed;
	}
}
