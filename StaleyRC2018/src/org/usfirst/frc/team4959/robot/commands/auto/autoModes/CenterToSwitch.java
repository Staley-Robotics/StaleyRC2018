package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drops a power cube at whichever side of the switch belongs to us
 */
public class CenterToSwitch extends CommandGroup {
	String gameData;

    public CenterToSwitch() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();

        // Drive forward
    	
    	
//    	// Left switch side
    	if(gameData.charAt(0) == 'R') {
    		// Turn towards indicated power cube
    		System.out.println("R");
    	}
    	// Right switch side
    	else {
    		// Turn towards indicated power cube
    		System.out.println("");
    	}
    }
}
