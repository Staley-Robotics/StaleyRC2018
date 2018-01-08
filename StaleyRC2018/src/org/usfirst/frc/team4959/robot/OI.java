/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4959.robot;
//Change?

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick xboxController;
	public static Joystick xboxControllertwo;
	
	public OI() {
		
		// ***** xbox Controller *****
		xboxController = new Joystick(RobotMap.XBOX_PORT);
		xboxControllertwo = new Joystick(RobotMap.XBOX_TWO_PORT);
		
		
		
	}
	
	public double getUp() {
		return xboxController.getPOV(1);
	}
	
	public double getDown() {
		return xboxController.getPOV(5);
	}
	
	// Returns the value of the Left Trigger on the XBOX Controller
	public double getLeftTrigger() {
		return xboxController.getRawAxis(RobotMap.LEFT_TRIGGER);
	}
	
	// Returns the value of the Right Trigger on the XBOX Controller
	public double getRightTrigger() {
		return xboxController.getRawAxis(RobotMap.RIGHT_TRIGGER);
	}
	
	// Returns the X-Axis value of the Left Stick on the XBOX Controller
	public double getLeftStickX() {
		return xboxController.getRawAxis(RobotMap.LEFT_X_AXIS);
	}
}
