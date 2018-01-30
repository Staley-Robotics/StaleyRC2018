package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 */
public class AutoBrettV5 extends CommandGroup {

	public AutoBrettV5() {
		// DriveTurn(Inches, Power, Turn, Time)
		addSequential(new DriveTurn(40, 0.7, 0.5, 3));
		System.out.println("Printing" + Robot.gameData);

	}
}
