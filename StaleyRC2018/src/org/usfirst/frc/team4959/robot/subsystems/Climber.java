package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for our climber system to climb the run at the end of the game
 */
public class Climber extends Subsystem {
	private Victor winch;
	
	public Climber() {
		winch = new Victor(RobotMap.WINCH_MOTOR_PORT);
	}

    public void initDefaultCommand() {
        
    }
    
    public void runWinch(double power) {
    	winch.set(power);
    }
}

