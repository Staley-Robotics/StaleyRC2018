package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.GyroTurning;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 * 
 * DriveTurn(Inches, Power, Turn, Time)
 */
public class AutoBrettV5 extends CommandGroup {

	public AutoBrettV5() {
//		addSequential(new DriveTurn(80, 0.9, 0, 3));
//		addSequential(new DriveStraight(60));
		addSequential(new GyroTurning(90, 2));
	}
}
