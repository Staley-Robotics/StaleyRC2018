package org.usfirst.frc.team4959.robot.commands.auto.autoModes;

import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.auto.autoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drops a power cube at whichever side of the switch belongs to us.
 * 
 * DriveTurn(inches, power, turn, time)
 * GyroTurning(angle)
 */
public class CenterSwitch extends CommandGroup {

	public CenterSwitch() {
		addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION)); // Raises elevator to position to drop into switch

		// If right switch is ours
		if (PlateColorChecker.rightSwitchColor()) {
			addSequential(new DriveTurn(FieldDimensions.ROBOT_SIZE, 0.8, 0, 1)); // Drives straight
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_LOW_SCALE_ELEVATION));
			addSequential(new DriveTurn(50, 0.4, 0.65, 3)); // Moves forward while turning right
			addSequential(new DriveTurn(42, 0.4, -0.69, 3)); // Moves forward while turning left to straighten back out
			addSequential(new DriveTurn(20, 0.8, 0, 1)); // Drives straight
			addSequential(new AutoDropSequence());
			addSequential(new DriveTurn(-(FieldDimensions.ROBOT_SIZE), 0.8, 0, 1)); // Backs up
			addSequential(new GyroTurning(90, 1));
			addSequential(new DriveTurn(30, 0.6, -0.4, 2)); // Moves forward while turning left
			addSequential(new DriveTurn(30, 0.8, 0, 2)); // Drives straight
		}
		
		// If left switch is ours
		else {
			addSequential(new DriveTurn(FieldDimensions.ROBOT_SIZE, 0.8, 0, 1)); // Drives straight
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_LOW_SCALE_ELEVATION));
			addSequential(new DriveTurn(50, 0.4, -0.65, 3)); // Moves forward while turning left
			addSequential(new DriveTurn(42, 0.4, 0.69, 3)); // Moves forward while turning right to straighten back out
			addSequential(new DriveTurn(20, 0.8, 0, 1)); // Drives straight
			addSequential(new AutoDropSequence());
			addSequential(new DriveTurn(-(FieldDimensions.ROBOT_SIZE), 0.8, 0, 1)); // Backs up
			addSequential(new GyroTurning(90, 1));
			addSequential(new DriveTurn(30, 0.6, 0.4, 2)); // Moves forward while turning right
			addSequential(new DriveTurn(30, 0.8, 0, 2)); // Drives straight
		}
	}
}
