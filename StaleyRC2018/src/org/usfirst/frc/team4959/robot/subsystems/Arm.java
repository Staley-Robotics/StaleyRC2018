package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.commands.Arm.RunArms;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The arm is a mechanism that raises and lowers the power cube that
 * is controlled by two motors at two pivot points.
 */
public class Arm extends Subsystem {

	private SpeedController topMotor;
	private SpeedController bottomMotor;
	
	public Arm() {
		bottomMotor = new Talon(RobotMap.BOTTOM_ARM_PORT);
		topMotor = new Talon(RobotMap.TOP_ARM_PORT);
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new RunArms());
	}

	// Gives power to the top motor to move the top part of the arm mechanism
    public void moveTopArm(double power) {
    	topMotor.set(power);
    }
    
	// Gives power to the bottom motor to move the bottom part of the arm mechanism
    public void moveBottomArm(double power) {
    	bottomMotor.set(power);
    }
}

