package org.usfirst.frc.team4959.robot.util;

public class Constants {
	// Intake speeds for taking in and sending out
	public static final double INTAKE_IN_SPEED = 0.4;
	public static final double INTAKE_OUT_SPEED = -0.7;
	
	// Set elevations for the elevator
	public static final double ELEVATOR_BOTTOM_ELEVATION = 6;
	public static final double ELEVATOR_SWITCH_ELEVATION = 30;
	public static final double ELEVATOR_SCALE_ELEVATION = 60;
	
	// Soft limits for how far the elevator can go
	public static final double FWD_SOFT_LIMIT = 1000; // Adjust this later on
	public static final double REV_SOFT_LIMIT = 0;
	
	// Pulses per revolution of encoders
	public static final double ENCODER_DISTANCE_PER_PULSE_POSITIVE = 185;
	public static final double ENCODER_DISTANCE_PER_PULSE_NEGATIVE = 175;
}
