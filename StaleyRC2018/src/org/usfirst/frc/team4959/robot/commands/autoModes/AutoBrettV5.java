package org.usfirst.frc.team4959.robot.commands.autoModes;

import org.usfirst.frc.team4959.robot.commands.autoCommands.drive.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.autoCommands.drive.GyroTurning;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 */
public class AutoBrettV5 extends CommandGroup {

	public AutoBrettV5() {
		addSequential(new DriveTurn(45, 0.7, 0, 8));
	}
}
