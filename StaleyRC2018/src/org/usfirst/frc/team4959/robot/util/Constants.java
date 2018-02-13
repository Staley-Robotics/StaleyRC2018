package org.usfirst.frc.team4959.robot.util;

/*
 * Holds constants for values used around the robot code
 */

public class Constants {
	// Intake speeds for taking in and sending out
	public static final double INTAKE_IN_SPEED = -0.4;
	public static final double INTAKE_OUT_SPEED = 0.2;
	
	// Set elevations for the elevator
	public static final double ELEVATOR_BOTTOM_ELEVATION = -1000;
	public static final double ELEVATOR_SWITCH_ELEVATION = 235000;
	public static final double ELEVATOR_LOW_SCALE_ELEVATION = 555000;
	public static final double ELEVATOR_MID_SCALE_ELEVATION = 755000;	//not in use 
	public static final double ELEVATOR_HIGH_SCALE_ELEVATION = 672000;
	
	// Soft limits for how far the elevator can go (Not in use)
	public static final int FWD_SOFT_LIMIT = 20000; // Adjust this later on
	public static final int REV_SOFT_LIMIT = -5000;
	
	// ***** Encoder Constants *****
//	public static final double ENCODER_DISTANCE_PER_PULSE = 0.07575; // Pulses per revolution of encoders
	public static final double ENCODER_DISTANCE_PER_PULSE = 0.056; // Pulses per revolution of encoders
	public static final double PULSE_PER_INCH = 13.2;
	public static final double TICKS_PER_REVOLUTION = 20;
	
	// Physical constants for the drive train
	public static final double WHEEL_DIAMETER = 6;
	public static final double GEAR_RATIO = 0.1;
}
