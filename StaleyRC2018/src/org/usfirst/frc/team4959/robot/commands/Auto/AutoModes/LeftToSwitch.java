package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;


import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start on the left side of the field and aim to put a cube in the left side of
 * the switch. 
 * TODO: If the switch is not on our side, place the power cube in the
 * correct scale side.
 * 
 * DriveTurn(inches, power, turn, time) 
 * GyroTurning(angle)
 * SetElevatorPosition(height)
 * Delay(time)
 */
public class LeftToSwitch extends CommandGroup {
	
	public LeftToSwitch() {
		addParallel(new SetElevatorPosition(30000)); // Raises elevator to position to move forward without dragging
		addSequential(new Delay(0.1));

		// If left switch is ours
		if (PlateColorChecker.leftSwitchColor()) {
			// ***** Place a cube in the left switch *****
			addSequential(new DriveTurn(10, 0.8, 0, 1)); // Drives straight
			addSequential(new DriveTurn(2, -0.4, 0, 1)); // Jerkin
			addSequential(new DriveTurn(60, 0.9, 0, 3));
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION));
			addSequential(new DriveTurn(28, 0.7, -0.88, 2));
			addSequential(new Delay(0.3));
			addSequential(new DriveTurn(12, 0.6, 0, 1));
			addSequential(new AutoDropSequence());
		}
		
		// If right switch is ours
		else {
			// ***** Cross the auto line *****
			addSequential(new AutoBrettV5());

			// ***** Try to place a cube in the scale *****
//			// If left scale is ours
//			if (PlateColorChecker.leftScaleColor()) {
//				addSequential(new LeftToScale());
//			}
//
//			// If right scale is ours
//			else {
//				addSequential(new DriveTurn(FieldDimensions.HALF_DS_TO_SWITCH, 0.99, 0, 1)); // Goes straight
//				addSequential(new DriveTurn(50, 0.8, -0.75, 3)); // Moves forward while turning left
//				addSequential(new DriveTurn(42, 0.8, 0.75, 3)); // Moves forward while turning right to straighten back out
//				addSequential(new DriveTurn(30, 0.9, 0, 0.5)); // Move past the switch
//				addParallel(new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION));
//				addSequential(new DriveTurn(30, 0.9, 0, 0.5)); // Move to between the switch and scale
//				addSequential(new DriveTurn(20, 0.8, 0.6, 1)); // Turn right
//				addSequential(new DriveTurn(60, 1, 0, 2)); // Drive to the right side of the scale
//				addSequential(new GyroTurning(-90, 1)); // Turn towards the scale
//				addSequential(new AutoDropSequence()); // Place the power cube
//				addSequential(new DriveTurn(-30, -0.5, 0, 2)); // Back off the scale
//			}
		}
	}
}
