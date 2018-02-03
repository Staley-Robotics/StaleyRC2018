package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 * 
 * DriveTurn(Inches, Power, Turn, Time)
 */
public class AutoBrettV5 extends CommandGroup {

	public AutoBrettV5() {
		addSequential(new DriveTurn(40, 0.7, 0, 2));
	}
}
