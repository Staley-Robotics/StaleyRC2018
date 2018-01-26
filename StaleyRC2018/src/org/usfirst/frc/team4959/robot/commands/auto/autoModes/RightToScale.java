package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start on the right side of the field and place the power 
 * cube in the correct side of the scale.
 *
 * DriveTurn(Inches, Power, Turn, Time) 
 * GyroTurning(Angle)
 */
public class RightToScale extends CommandGroup {

	public RightToScale() {

		// Gets to decision point
		addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 35), 0.99, 0, 2)); // Goes straight

		// If right scale is ours
		if (PlateColorChecker.rightScaleColor()) {
			addSequential(new DriveTurn(15, 0.5, -0.4, 3)); // Turns and drives towards the scale.
			addSequential(new DriveTurn(15, 0.5, 0.4, 3)); // Straightens out and touches scale
			// Place the power cube
			addSequential(new DriveTurn(-30, -0.5, 0, 2)); // Back off the scale
		}
		// If left scale is ours
		else {
			addSequential(new DriveTurn(20, 0.8, -0.6, 5)); // Turn left
			addSequential(new DriveTurn(60, 1, 0, 5)); // Drive to the right side of the scale
			addSequential(new GyroTurning(90)); // Turn towards the scale
			// Place the power cube
			addSequential(new DriveTurn(-30, -0.5, 0, 2)); // Back off the scale
		}
	}
}
