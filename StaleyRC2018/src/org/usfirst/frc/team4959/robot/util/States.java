package org.usfirst.frc.team4959.robot.util;

public class States {
	
	public static ElevatorStates elevatorState;
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
	
	// Tells us if the intake claw is open or closed
	public static enum IntakeClawStates {
		open,
		closed;
	}
	
	// Resets all of the states
	public static void resetStates() {
		elevatorState = ElevatorStates.joystickControl;
		shifterState = ShifterStates.low;
		intakeClawState = IntakeClawStates.closed;
	}
}
