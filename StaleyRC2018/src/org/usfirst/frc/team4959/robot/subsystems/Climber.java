package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.commands.Climber.RunClimberMotor;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for our climber system to climb the run at the end of the game
 */
public class Climber extends Subsystem {
	
	@SuppressWarnings("unused")
	private final String TAG = (this.getName() + ": ");
	
	private Victor winchOne;
	private Victor winchTwo;
	
	public Climber() {
		winchOne = new Victor(RobotMap.CLIMBER_MOTOR_PORT_ONE);
		winchTwo = new Victor(RobotMap.CLIMBER_MOTOR_PORT_TWO);
	}

    public void initDefaultCommand() {
        setDefaultCommand(new RunClimberMotor());
    }
    
    /**
     * Runs the climber motors at the given power. 
     * 
     * @param power Power to send to climber motors
     */
    public void runClimber(double power) {
    	winchOne.set(power);
    	winchTwo.set(power);
    }
}

