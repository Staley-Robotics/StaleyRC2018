package org.usfirst.frc.team4959.robot.util;

/*
 * Holds constants for values used around the robot code
 */

public class Constants {
	// Intake speeds for taking in and sending out
	public static final double INTAKE_IN_SPEED = 0.4;
	public static final double INTAKE_OUT_SPEED = -0.4;
	
	// Set elevations for the elevator
	public static final double ELEVATOR_BOTTOM_ELEVATION = 3000;
	public static final double ELEVATOR_SWITCH_ELEVATION = 235000;
	public static final double ELEVATOR_LOW_SCALE_ELEVATION = 565000;
	public static final double ELEVATOR_MID_SCALE_ELEVATION = 600000;	//not in use 
	public static final double ELEVATOR_HIGH_SCALE_ELEVATION = 675000;
	
	// Soft limits for how far the elevator can go 
	public static final int FWD_SOFT_LIMIT = 680000;
	public static final int REV_SOFT_LIMIT = -4000;
	
	// ***** Encoder Constants *****
//	public static final double ENCODER_DISTANCE_PER_PULSE = 0.056; // Pulses per revolution of encoders low gear
	public static final double ENCODER_DISTANCE_PER_PULSE = 0.201; // High gear
	public static final double PULSE_PER_INCH = 13.2;
	public static final double TICKS_PER_REVOLUTION = 20;
	
	// Physical constants for the drive train
	public static final double WHEEL_DIAMETER = 6;
	public static final double GEAR_RATIO = 0.1;
}
