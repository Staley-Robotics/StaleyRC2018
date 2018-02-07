package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 * 
 * DriveTurn(Inches, Power, Turn, Time)
 */
public class AutoBrettV5 extends CommandGroup {

	public AutoBrettV5() {
		addSequential(new DriveTurn(55, 0.7, -0.23, 2));
	}
}
