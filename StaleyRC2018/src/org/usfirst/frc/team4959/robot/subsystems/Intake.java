package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
	DoubleSolenoid intakeSolenoid = new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_PORT_ONE, RobotMap.INTAKE_SOLENOID_PORT_TWO);
	
    private Victor rightMotor;
    private Victor leftMotor;
    
    public Intake() {
    	rightMotor = new Victor(RobotMap.INTAKE_RIGHT_PORT);
    	leftMotor = new Victor(RobotMap.INTAKE_LEFT_PORT);
    }

    public void initDefaultCommand() {
        
    }
    
    public void succBoi(double power) {
    	leftMotor.set(power);
    	rightMotor.set(-power);
    }
    
    public void expandIntake() {
		intakeSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void closeIntake() {
		intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
}

