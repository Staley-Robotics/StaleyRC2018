package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.Constants;
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
			addSequential(new DriveTurn(50, 0.9, 0.7, 2)); // Drives out to the right with DriveTurn
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION));
			addSequential(new DriveTurn(50, 0.9, -0.7, 2)); // Turns back in a left direction to straighten back out
			addSequential(new DriveTurn(50, 0.9, 0, 2));// Drives straight to get to the scale
			// Possible adjustments to line up with scale
			addSequential(new AutoDropSequence());
			// Back off from the scale
		}

		// If left scale is ours
		else {
			addSequential(new DriveTurn(50, 0.9, -0.7, 2)); // Drives out to the right with DriveTurn
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION));
			addSequential(new DriveTurn(50, 0.9, 0.7, 2)); // Drive and turn to the right direction to straighten back out
			addSequential(new DriveTurn(50, 0.9, 0, 2)); // Drives straight to get to the scale
			// Possible adjustments to line up with the scale
			addSequential(new AutoDropSequence());
			// Back off from the scale
		}
	}
}
