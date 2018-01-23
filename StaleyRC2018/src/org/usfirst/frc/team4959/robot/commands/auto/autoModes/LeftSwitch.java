package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.GyroTurning;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSwitch extends CommandGroup {
	String gameData;
	

	public LeftSwitch() {
		gameData = Robot.getGameData();
		// DriveTurn(Inches, Power, Turn, Time)
		// For when Left switch on our side is our alliance's
		if (gameData.charAt(0) == 'L') {
			// Drive Forward
			addSequential(new DriveTurn(120, 0.75, 0, 5));
			// Drop power cube into switch
		}

		else {
			addSequential(new DriveTurn(80, 0.8, -0.4, 5));
		}
	}
}
