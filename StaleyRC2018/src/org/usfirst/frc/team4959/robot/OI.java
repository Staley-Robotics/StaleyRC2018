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

	public double getLeftStickXCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_X_AXIS);
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

	public double getLeftStickYCont1() {
		return xboxController.getRawAxis(RobotMap.LEFT_Y_AXIS);
	}

	public double getRightStickYCont2() {
		return xboxControllertwo.getRawAxis(RobotMap.RIGHT_Y_AXIS);
	}

	public double getLeftStickYCont2() {
		return xboxControllertwo.getRawAxis(5);
	}
}
