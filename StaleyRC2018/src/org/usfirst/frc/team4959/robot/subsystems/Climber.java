package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for our climber system to climb the run at the end of the game
 */
public class Climber extends Subsystem {
	private Victor winchOne;
	private Victor winchTwo;
	
	public Climber() {
		winchOne = new Victor(RobotMap.WINCH_MOTOR_PORT_ONE);
		winchTwo = new Victor(RobotMap.WINCH_MOTOR_PORT_TWO);
	}

    public void initDefaultCommand() {
        
    }
    
    public void runWinch(double power) {
    	winchOne.set(power);
    	winchTwo.set(power);
    }
}

