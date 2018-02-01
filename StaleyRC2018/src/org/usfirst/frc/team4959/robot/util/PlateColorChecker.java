package org.usfirst.frc.team4959.robot.util;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

/*
 * Tells us what plates are what color
 */

public class PlateColorChecker {
	/**
	 * @return True if left switch is ours
	 * @return False if right switch is ours
	 */
	public static boolean leftSwitchColor() {
		System.out.println("Left Switch: " + (messageToCharArray()[0] == 'L'));
		return messageToCharArray()[0] == 'L';
	}
	
	/**
	 * @return True if right switch is ours
	 * @return False if left switch is ours
	 */
	public static boolean rightSwitchColor() {
		System.out.println("Right Switch: " + (messageToCharArray()[0] == 'R'));
		return messageToCharArray()[0] == 'R';
	}
	
	/**
	 * @return True if left scale is ours
	 * @return False if right scale is ours
	 */
	public static boolean leftScaleColor() {
		System.out.println("Left Scale: " + (messageToCharArray()[1] == 'L'));
		return messageToCharArray()[1] == 'L';
	}

	/**
	 * @return True if right scale is ours
	 * @return False if left scale is ours
	 */
	public static boolean rightScaleColor() {
		System.out.println("Right Scale: " + (messageToCharArray()[1] == 'R'));
		return messageToCharArray()[1] == 'R';
	}
	
	/**
	 * @return a character array containing with switches belong to use and which scale is ours
	 */
	public static char[] messageToCharArray() {
		return DriverStation.getInstance().getGameSpecificMessage().toCharArray();
	}
}
