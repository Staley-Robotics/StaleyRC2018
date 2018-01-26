package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	
	private Victor motor1;
	private Victor motor2;
	
	private Encoder encoder;
	
	public Elevator() {
		motor1 = new Victor(RobotMap.ELEVATOR_MOTOR_ONE_PORT);
		motor2 = new Victor(RobotMap.ELEVATOR_MOTOR_TWO_PORT);
		
		encoder = new Encoder(RobotMap.ELEVATOR_ENCODER_PORT_ONE, RobotMap.ELEVATOR_ENCODER_PORT_TWO, false, Encoder.EncodingType.k4X);
		
		encoder.reset();
//		encoder.setDistancePerPulse(distancePerPulse);
	}

    public void initDefaultCommand() {
        
    }
    
    public void powerElevator(double power) {
    	motor1.set(power);
    	motor2.set(-power);
    }
    
}

