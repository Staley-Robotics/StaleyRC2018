package org.usfirst.frc.team4959.robot.commands.autoModes;

import org.usfirst.frc.team4959.robot.commands.autoCommands.drive.DriveDistance;
import org.usfirst.frc.team4959.robot.commands.autoCommands.drive.GyroTurning;
import org.usfirst.frc.team4959.robot.commands.autoCommands.drive.Stopper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 */
public class AutoBrettV5 extends CommandGroup {

	public AutoBrettV5() {
		addSequential(new DriveDistance(30));
	}
}
