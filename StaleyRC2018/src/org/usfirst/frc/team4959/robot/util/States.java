package org.usfirst.frc.team4959.robot.util;

/*
 * Contains the different states that the subsystems in the robot can be in. 
 */

public class States {

	public static ElevatorControlStates elevatorControlState;
	public static ElevatorPosStates elevatorPosState;
	public static ShifterStates shifterState;
	public static IntakeClawStates intakeClawState;
	public static ElevatorFwdSoftLimitStates elevatorFwdSoftLimitState;
	public static ElevatorRevSoftLimitStates elevatorRevSoftLimitState;

	// Tells us if the elevator is currently being controlled by player's joystick
	// or the PID Controller
	public static enum ElevatorControlStates {
		joystickControl, pidControl;
	}

	// Tells us if the shifter is in low gear or high gear
	public static enum ShifterStates {
		low, high;
	}

	// Tells us if the in-take claw is open or closed
	public static enum IntakeClawStates {
		open, closed;
	}

	public static enum ElevatorPosStates {
		bottom, 
		switchPos,
		scaleLow, scaleMid, scaleHigh, 
		userControl, positionHeld;
	}
	
	public static enum ElevatorFwdSoftLimitStates {
		disabled, enabled;
	}
	
	public static enum ElevatorRevSoftLimitStates {
		disabled, enabled;
	}

	// Resets all of the states
	public static void resetStates() {
		elevatorControlState = ElevatorControlStates.joystickControl;
		elevatorPosState = ElevatorPosStates.userControl;
		shifterState = ShifterStates.high;
		intakeClawState = IntakeClawStates.closed;
		elevatorFwdSoftLimitState = ElevatorFwdSoftLimitStates.enabled;
		elevatorRevSoftLimitState = ElevatorRevSoftLimitStates.enabled;
	}
}
