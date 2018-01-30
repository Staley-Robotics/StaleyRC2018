package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts from the center and goes to whichever scale we own to put a power cube
 * in it.
 * 
 * DriveTurn(inches, power, turn, time)
 * GyroTurning(angle)
 */
public class CenterScale extends CommandGroup {

	public CenterScale() {
		
		// If right scale is ours
		if (PlateColorChecker.rightScaleColor()) {
			addSequential(new DriveTurn(50, 0.9, 0.7, 3)); // Drives out to the right with DriveTurn
			addSequential(new DriveTurn(50, 0.9, -0.7, 3)); // Turns back in a left direction to straighten back out
			addSequential(new DriveTurn(50, 0.9, 0, 3));// Drives straight to get to the scale
			// Possible adjustments to line up with scale
			// Drop power cube onto the scale
			// Back off from the scale
		}

		// If left scale is ours
		else {
			addSequential(new DriveTurn(50, 0.9, -0.7, 3)); // Drives out to the right with DriveTurn
			addSequential(new DriveTurn(50, 0.9, 0.7, 3)); // Drive and turn to the right direction to straighten back out
			addSequential(new DriveTurn(50, 0.9, 0, 3)); // Drives straight to get to the scale
			// Possible adjustments to line up with the scale
			// Drop power cube onto the scale
			// Back off from the scale
		}
	}
}
