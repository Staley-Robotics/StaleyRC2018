package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.commands.elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start on the left side of the field and place the power 
 * cube in the correct side of the scale.
 *
 * DriveTurn(Inches, Power, Turn, Time) 
 * GyroTurning(Angle)
 */
public class LeftToScale extends CommandGroup {

	public LeftToScale() {
		
		addParallel(new SetElevatorPosition(Constants.ELEVATOR_SCALE_ELEVATION)); // Raises elevator to position to drop into scale
		addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 35), 0.99, 0, 2)); // Goes straight to decision point

		// If Left scale is ours
		if (PlateColorChecker.leftScaleColor()) {
			addSequential(new DriveTurn(15, 0.5, 0.4, 2)); // Turns and drives towards the scale.
			addSequential(new DriveTurn(15, 0.5, -0.4, 2)); // Straightens out and touches scale
			addSequential(new AutoDropSequence()); // Place the power cube
			addSequential(new DriveTurn(-30, -0.5, 0, 2)); // Back off the scale
		}
		
		// If right scale is ours
		else {
			addSequential(new DriveTurn(20, 0.8, 0.6, 2.5)); // Turn right
			addSequential(new DriveTurn(60, 1, 0, 2)); // Drive to the right side of the scale
			addSequential(new GyroTurning(-90, 1)); // Turn towards the scale
			addSequential(new AutoDropSequence()); // Place the power cube
			addSequential(new DriveTurn(-30, -0.5, 0, 2)); // Back off the scale
		}
	}
}
