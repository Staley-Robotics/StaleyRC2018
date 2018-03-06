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
	
	// Sets preference for if we let it go to switch during a Scale auto if we don't own the desired scale
	public static enum ToScalePreferences {
		canGoToSwitch, noGoToSwitch;
	}
	
	// Sets preference for if we let it go to scale during a switch auto if we don't own the desired switch
	public static enum ToSwitchPreferences {
		canGoToScale, noGoToScale;
	}
	
	public static void setDefaultModes() {
		autoMode = AutoModes.delay;
		
		toScalePreference = ToScalePreferences.noGoToSwitch;
		toSwitchPreference = ToSwitchPreferences.noGoToScale;
	}
	
	public static void setAutomode (AutoModes chosenMode) {
		autoMode = chosenMode;
	}
	
	public static void setToScalePreference (ToScalePreferences preference) {
		toScalePreference = preference;
	}
	
	public static void setToSwitchPreference (ToSwitchPreferences preference) {
		toSwitchPreference = preference;
	}
}
