/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4959.robot;
//Change?

import org.usfirst.frc.team4959.robot.commands.Drive.ShifterToggle;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.commands.Elevator.ZeroElevator;
import org.usfirst.frc.team4959.robot.commands.Intake.CloseIntake;
import org.usfirst.frc.team4959.robot.commands.Intake.ExpandIntake;
import org.usfirst.frc.team4959.robot.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private final String TAG = ("OI" + ": ");
	
	public static Joystick xboxController;
	public static Joystick xboxControllertwo;
	
	SetElevatorPosition bottomPosition = new SetElevatorPosition(Constants.ELEVATOR_BOTTOM_ELEVATION);
	SetElevatorPosition switchPosition = new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION);
	SetElevatorPosition lowScalePosition = new SetElevatorPosition(Constants.ELEVATOR_LOW_SCALE_ELEVATION);
	SetElevatorPosition midScalePosition = new SetElevatorPosition(Constants.ELEVATOR_MID_SCALE_ELEVATION);
	SetElevatorPosition highScalePosition = new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION);

	public OI() {

		// ***** X-Box Controller *****
		xboxController = new Joystick(RobotMap.XBOX_PORT);
		xboxControllertwo = new Joystick(RobotMap.XBOX_TWO_PORT);

		// Toggle for shifting between high and low gear
		Button shifterToggle = new JoystickButton(xboxController, RobotMap.B_BUTTON);
		shifterToggle.whenPressed(new ShifterToggle());
		
		Button closeIntake = new JoystickButton(xboxControllertwo, RobotMap.RIGHT_BUMPER);
		closeIntake.whenPressed(new CloseIntake());
		
		Button expandIntake = new JoystickButton(xboxControllertwo, RobotMap.LEFT_BUMPER);
		expandIntake.whenPressed(new ExpandIntake());

		// Sets the elevator's position to bottom elevation and cancels any other elevation command occuring
		Button bottomElevation = new JoystickButton(xboxControllertwo, RobotMap.A_BUTTON);
		bottomElevation.cancelWhenPressed(switchPosition);
		bottomElevation.cancelWhenPressed(lowScalePosition);
		bottomElevation.cancelWhenPressed(midScalePosition);
		bottomElevation.cancelWhenPressed(highScalePosition);
		bottomElevation.whenPressed(bottomPosition);
		
		// Sets the elevator's position to switch elevation and cancels any other elevation command occuring
		Button switchElevation = new JoystickButton(xboxControllertwo, RobotMap.X_BUTTON);
		switchElevation.cancelWhenPressed(bottomPosition);
		switchElevation.cancelWhenPressed(lowScalePosition);
		switchElevation.cancelWhenPressed(midScalePosition);
		switchElevation.cancelWhenPressed(highScalePosition);
		switchElevation.whenPressed(switchPosition);
		
		// Sets the elevator's position to low scale elevation and cancels any other elevation command occuring
		Button lowScaleElevation = new JoystickButton(xboxControllertwo, RobotMap.B_BUTTON);
		lowScaleElevation.cancelWhenPressed(bottomPosition);
		lowScaleElevation.cancelWhenPressed(switchPosition);
		lowScaleElevation.cancelWhenPressed(midScalePosition);
		lowScaleElevation.cancelWhenPressed(highScalePosition);
		lowScaleElevation.whenPressed(lowScalePosition);
		
		// Sets the elevator's position to high scale elevation and cancels any other elevation command occuring
		Button highScaleElevation = new JoystickButton(xboxControllertwo, RobotMap.Y_BUTTON);
		highScaleElevation.cancelWhenPressed(bottomPosition);
		highScaleElevation.cancelWhenPressed(switchPosition);
		highScaleElevation.cancelWhenPressed(lowScalePosition);
		highScaleElevation.cancelWhenPressed(midScalePosition);
		highScaleElevation.whenPressed(highScalePosition);
		
		// Stops any elevation command to allow player to use joy-stick control on the elevator. 
		Button stopElevator = new JoystickButton(xboxControllertwo, RobotMap.START_BUTTON);
		stopElevator.cancelWhenPressed(bottomPosition);
		stopElevator.cancelWhenPressed(switchPosition);
		stopElevator.cancelWhenPressed(lowScalePosition);
		stopElevator.cancelWhenPressed(midScalePosition);
		stopElevator.cancelWhenPressed(highScalePosition);
		
		// Sets the encoder on the elevator to 0
		Button zeroElevator = new JoystickButton(xboxControllertwo, RobotMap.BACK_BUTTON);
		zeroElevator.whenPressed(new ZeroElevator());
	}
	
	public void cancelPositionCommands() {
		bottomPosition.cancel();
		switchPosition.cancel();
		lowScalePosition.cancel();
		midScalePosition.cancel();
		highScalePosition.cancel();
		System.out.println("Canceled Commands");
	}
	
	// ***** Getters for X-Box controller(s) raw axis *****

	// Controller 1
	/**
	 * Returns the current x-axis value of the left joystick on controller 1
	 * 
	 * @return x-axis value of left joystick on controller 1
	 */
	public double getLeftStickXCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_X_AXIS);
	}
	
	/**
	 * Returns the current y-axis value of the left joystick on controller 1
	 * 
	 * @return y-axis value of left joystick on controller 1
	 */
	public double getLeftStickYCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_Y_AXIS);
	}
	
	/**
	 * Returns the current y-axis value of the right joystick on controller 1
	 * 
	 * @return y-axis value of the right joystick on controller 1
	 */
	public double getRightStickYCont1() {
		return xboxController.getRawAxis(RobotMap.RIGHT_Y_AXIS);
	}
	
	/**
	 * Returns the current value of the left trigger on controller 1
	 * 
	 * @return value of left trigger on controller 1
	 */
	public double getLeftTriggerCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_TRIGGER);
	}
	
	/**
	 * Returns the current value of the right trigger on controller 1
	 * 
	 * @return value of right trigger on controller 1
	 */
	public double getRightTriggerCont1() {
		return xboxController.getRawAxis(RobotMap.RIGHT_TRIGGER);
	}

	// Controller 2
	/**
	 * Returns the value of the x-axis of the left joystick on controller 2
	 * 
	 * @return x-axis value of left joystick on controller 2
	 */
	public double getLeftStickXCont2() {
		return xboxControllertwo.getRawAxis(RobotMap.LEFT_X_AXIS);
	}
	
	/**
	 * Returns the value of the y-axis of the left joystick on controller 2
	 * 
	 * @return y-axis value of left joystick on controller 2
	 */
	public double getLeftStickYCont2() {
		return xboxControllertwo.getRawAxis(RobotMap.LEFT_Y_AXIS);
	}
	
	/**
	 * Returns the value of the y-axis of the right joystick on controller 2
	 * 
	 * @return y-axis value of right joystick on controller 2
	 */
	public double getRightStickYCont2() {
		return xboxControllertwo.getRawAxis(RobotMap.RIGHT_Y_AXIS);
	}
	
	/**
	 * Returns the value of the left trigger on controller 2
	 * 
	 * @return value of left trigger on controller 2
	 */
	public double getLeftTriggerCont2() {
		return xboxControllertwo.getRawAxis(RobotMap.LEFT_TRIGGER);
	}
	
	/**
	 * Returns the value of the right trigger on controller 2
	 * 
	 * @return value of right trigger on controller 2
	 */
	public double getRightTriggerCont2() {
		return xboxControllertwo.getRawAxis(RobotMap.RIGHT_TRIGGER);
	}
}
