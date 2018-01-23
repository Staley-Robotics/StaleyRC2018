package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSwitch extends CommandGroup {
	String gameData;

    public RightSwitch() {
//    	gameData = Robot.getGameData();
//		// DriveTurn(Inches, Power, Turn, Time)
//		// For when Right switch on our side is our alliance's
//		if (gameData.charAt(0) == 'R') {
//			// Drive Forward
//			addSequential(new DriveTurn(108, 0.75, 0, 5));
//			addSequential(new Delay(1.0));
////			// Drop power cube into switch
//			addSequential(new DriveTurn(-30, -0.75, 0, 2));
//		}
//
//		else {
			addSequential(new DriveTurn(60, 0.99, 0, 2)); // Goes straight
			addSequential(new DriveTurn(50, 0.8, 0.65, 3)); // Moves forward while turning right
			addSequential(new DriveTurn(42, 0.8, -0.69, 3)); // Moves forward while turning left to straighten back out
			addSequential(new DriveTurn(30, 0.9, 0, 2));
		//}

    }
}
