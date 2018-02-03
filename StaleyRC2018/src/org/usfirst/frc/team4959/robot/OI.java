/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4959.robot;
//Change?

import org.usfirst.frc.team4959.robot.commands.Climber.RunWinchMotor;
import org.usfirst.frc.team4959.robot.commands.Drive.ShifterToggle;
import org.usfirst.frc.team4959.robot.commands.Intake.IntakePistonToggle;
import org.usfirst.frc.team4959.robot.commands.Intake.RunIntake;
import org.usfirst.frc.team4959.robot.commands.elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick xboxController;
	public static Joystick xboxControllertwo;
	
	SetElevatorPosition bottomPosition = new SetElevatorPosition(Constants.ELEVATOR_BOTTOM_ELEVATION);
	SetElevatorPosition switchPosition = new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION);
	SetElevatorPosition scalePosition = new SetElevatorPosition(Constants.ELEVATOR_SCALE_ELEVATION);

	public OI() {

		// ***** xbox Controller *****
		xboxController = new Joystick(RobotMap.XBOX_PORT);
		xboxControllertwo = new Joystick(RobotMap.XBOX_TWO_PORT);

		// Toggle for shifting between high and low gear
		Button shifterToggle = new JoystickButton(xboxController, RobotMap.B_BUTTON);
		shifterToggle.whenPressed(new ShifterToggle());
		
		// Brings in power cubes
		Button intake = new JoystickButton(xboxControllertwo, RobotMap.RIGHT_TRIGGER);
		intake.whileHeld(new RunIntake(Constants.INTAKE_IN_SPEED));
		
		// Spits the cube back out like a quitter
		Button reverseIntake = new JoystickButton(xboxControllertwo, RobotMap.LEFT_TRIGGER);
		reverseIntake.whileHeld(new RunIntake(Constants.INTAKE_OUT_SPEED));
		
		Button intakePistonToggle = new JoystickButton(xboxControllertwo, RobotMap.LEFT_BUMPER);
		intakePistonToggle.whenPressed(new IntakePistonToggle());

		Button bottomElevation = new JoystickButton(xboxControllertwo, RobotMap.A_BUTTON);
		bottomElevation.cancelWhenPressed(switchPosition);
		bottomElevation.cancelWhenPressed(scalePosition);
		bottomElevation.whenPressed(bottomPosition);
		
		Button switchElevation = new JoystickButton(xboxControllertwo, RobotMap.B_BUTTON);
		switchElevation.cancelWhenPressed(bottomPosition);
		switchElevation.cancelWhenPressed(scalePosition);
		switchElevation.whenPressed(switchPosition);
		
		Button scaleElevation = new JoystickButton(xboxControllertwo, RobotMap.Y_BUTTON);
		scaleElevation.cancelWhenPressed(bottomPosition);
		scaleElevation.cancelWhenPressed(switchPosition);
		scaleElevation.whenPressed(scalePosition);
		
		Button stopElevator = new JoystickButton(xboxControllertwo, RobotMap.X_BUTTON);
		stopElevator.cancelWhenPressed(bottomPosition);
		stopElevator.cancelWhenPressed(switchPosition);
		stopElevator.cancelWhenPressed(scalePosition);
		
		Button winch = new JoystickButton(xboxControllertwo, RobotMap.RIGHT_BUMPER);
		winch.whileHeld(new RunWinchMotor(1));
	}
	
	// ***** Getters for X-Box controller(s) raw axis *****

	// Controller 1
	public double getLeftStickXCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_X_AXIS);
	}
	
	public double getLeftStickYCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_Y_AXIS);
	}
	
	public double getRightStickYCont1() {
		return xboxController.getRawAxis(RobotMap.RIGHT_Y_AXIS);
	}
	
	public double getLeftTriggerCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_TRIGGER);
	}
	
	public double getRightTriggerCont1() {
		return xboxController.getRawAxis(RobotMap.RIGHT_TRIGGER);
	}

	// Controller 2
	public double getRightStickYCont2() {
		return xboxControllertwo.getRawAxis(RobotMap.RIGHT_Y_AXIS);
	}

	public double getLeftStickYCont2() {
		return xboxControllertwo.getRawAxis(5);
	}
}
