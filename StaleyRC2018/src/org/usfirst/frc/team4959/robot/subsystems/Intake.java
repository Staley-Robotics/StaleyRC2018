package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    private Talon rightMotor;
    private Talon leftMotor;
    
    public Intake() {
    	rightMotor = new Talon(RobotMap.INTAKE_RIGHT_PORT);
    	leftMotor = new Talon(RobotMap.INTAKE_LEFT_PORT);
    }

    public void initDefaultCommand() {
        
    }
    
    public void succBoi(double power) {
    	leftMotor.set(power);
    	rightMotor.set(-power);
    }
}

