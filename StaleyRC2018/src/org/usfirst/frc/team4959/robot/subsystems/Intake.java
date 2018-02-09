package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.commands.Intake.RunIntake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that controls the intake system, including the motors on the claws and the piston that expands and closes the claw
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
        setDefaultCommand(new RunIntake());
    }
    
    // Runs intake motors. 
    public void succBoi(double power) {
    	leftMotor.set(-power);
    	rightMotor.set(-power);
    }
    
    // Retracts piston to inversely expand the intake
    public void expandIntake() {
		intakeSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
    // Extends piston to inversely close the intake
	public void closeIntake() {
		intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
}

