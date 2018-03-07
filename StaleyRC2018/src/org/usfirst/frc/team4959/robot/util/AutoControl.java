package org.usfirst.frc.team4959.robot.util;

public class AutoControl {
	
	public static AutoModes autoMode;
	public static ToScalePreferences toScalePreference;
	public static ToSwitchPreferences toSwitchPreference;
	
	public static enum AutoModes {
		delay,
		autoBrettV5, 
		centerToSwitch,
		leftToScale, leftToSwitch,
		rightToScale, rightToSwitch;
	}
	
	public static enum ToScalePreferences {
		/*
		 * The robot is allowed to go to the nearby switch if owned if the desired scale is not owned
		 */
		canGoToSwitch, 
		
		/*
		 * The robot is not allowed to go to the nearby switch if the desired scale is not owned
		 */
		noGoToSwitch;
	}
	
	public static enum ToSwitchPreferences {
		/*
		 * The robot is allowed to go to the nearby scale if owned if the desired switch is not owned
		 */
		canGoToScale, 
		
		/*
		 * The robot is not allowed to go to the nearby scale if the desired switch is not owned
		 */
		noGoToScale;
	}
	
	// Sets the default states for autoMode and the preferences
	public static void setDefaultModes() {
		autoMode = AutoModes.delay;
		
		toScalePreference = ToScalePreferences.noGoToSwitch;
		toSwitchPreference = ToSwitchPreferences.noGoToScale;
	}
	
	// Sets the auto mode to be used
	public static void setAutomode (AutoModes chosenMode) {
		autoMode = chosenMode;
	}
	
	// Sets preference for if we let it go to switch during a scale auto if we don't own the desired scale
	public static void setToScalePreference (ToScalePreferences preference) {
		toScalePreference = preference;
	}
	
	// Sets preference for if we let it go to scale during a switch auto if we don't own the desired switch
	public static void setToSwitchPreference (ToSwitchPreferences preference) {
		toSwitchPreference = preference;
	}
}
