package org.usfirst.frc.team4959.robot.util;

import org.usfirst.frc.team4959.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

public class CollisionDetection {
	AHRS navx = Robot.driveTrain.getNavx();
	
	final static double kCollisionThreshold_DeltaG = 0.25f;
		
	double last_world_linear_accel_x = 0;
	double last_world_linear_accel_y = 0;
	
	double curr_world_linear_accel_x;
	double currentJerkX;
	double curr_world_linear_accel_y;
	double currentJerkY;
	
	public CollisionDetection() {
		
	}
	
	public boolean collisionDetector() {
		curr_world_linear_accel_x = navx.getWorldLinearAccelX();
		currentJerkX = curr_world_linear_accel_x - last_world_linear_accel_x;
		last_world_linear_accel_x = curr_world_linear_accel_x;
		
		curr_world_linear_accel_y = navx.getWorldLinearAccelY();
		currentJerkY = curr_world_linear_accel_y - last_world_linear_accel_y;
		last_world_linear_accel_y = curr_world_linear_accel_y;
		
		if ( ( Math.abs(currentJerkX) > kCollisionThreshold_DeltaG) || ( Math.abs(currentJerkY) > kCollisionThreshold_DeltaG)) {
			return true;
		} else {
			return false;
		}
	}
}
