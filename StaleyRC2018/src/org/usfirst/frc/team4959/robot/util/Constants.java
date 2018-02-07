package org.usfirst.frc.team4959.robot.util;

public class Constants {
	// Intake speeds for taking in and sending out
	public static final double INTAKE_IN_SPEED = -0.4;
	public static final double INTAKE_OUT_SPEED = 0.7;
	
	// Set elevations for the elevator
	public static final double ELEVATOR_BOTTOM_ELEVATION = 0;
	public static final double ELEVATOR_SWITCH_ELEVATION = 20000;
	public static final double ELEVATOR_LOW_SCALE_ELEVATION = 100000;
	public static final double ELEVATOR_MID_SCALE_ELEVATION = 120000; 
	public static final double ELEVATOR_HIGH_SCALE_ELEVATION = 140000;
	
	// Soft limits for how far the elevator can go
	public static final int FWD_SOFT_LIMIT = 20000; // Adjust this later on
	public static final int REV_SOFT_LIMIT = 0;
	
	// ***** Encoder Constants *****
	public static final double ENCODER_DISTANCE_PER_PULSE = 210; // Pulses per revolution of encoders
	public static final double TICKS_PER_REVOLUTION = 20;
	public static final double WHEEL_DIAMETER = 6;
	public static final double GEAR_RATIO = 1/15;
}
