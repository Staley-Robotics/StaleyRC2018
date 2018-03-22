package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.AutoControl;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start on the left side of the field and place the power 
 * cube in the left side of the scale, otherwise cross the
 * auto line.
 *
 * DriveTurn(inches, power, turn, time) 
 * GyroTurning(angle)
 * SetElevatorPosition(height)
 * Delay(time)
 */
public class LeftToScale extends CommandGroup {
	
	public LeftToScale() {
		
		// If Left scale is ours
		if (PlateColorChecker.leftScaleColor()) {
			// ***** Place a cube in the left scale *****
			addSequential(new DriveTurn(30, 0.5, 0, 1)); // Slow start to not jerk the robot
			addParallel(new SetElevatorPosition(40000));
			addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 133), 0.8, 0, 4)); // Goes straight to decision point
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION)); // TODO change to High Scale for comp
			addSequential(new DriveTurn(15, 0.6, 0.7, 3)); // small turn left
			addSequential(new DriveTurn(15, 0.6, -0.4, 3)); // correction right to straight
			addSequential(new DriveTurn(55, 0.8, 0, 4)); // Drive to scale
			addSequential(new DriveTurn(65, 0.5, -0.62, 5)); // turn into scale
			addSequential(new DriveTurn(20, 0.65, 0, 2));
			addSequential(new DriveTurn(1, -0.3, 0, 1));
			addSequential(new Delay(0.5));
			addSequential(new AutoDropSequence()); // Drop cube into scale
			
			// Backup sequence
			addSequential(new DriveTurn(40, -0.4, 0, 3));
			addSequential(new SetElevatorPosition(Constants.ELEVATOR_BOTTOM_ELEVATION));
			
			// ***** Try to place a second cube in the switch *****
//			addSequential(new DriveTurn(15, -0.6, 0, 2)); 
//			addSequential(new DriveTurn(30, -0.6, -0.8, 2));
//			addSequential(new DriveTurn(30, 0.5, 0, 1)); // Slow start to not jerk the robot
//			addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 135), 0.8, 0, 4)); // Goes straight to decision point
//			addParallel(new SetElevatorPosition(Constants.ELEVATOR_MID_SCALE_ELEVATION)); // TODO change to High Scale for comp
//			addSequential(new DriveTurn(33, 0.8, 0, 4));
//			addSequential(new DriveTurn(70, 0.6, -0.48, 5));
//			addSequential(new Delay(0.3));
//			addSequential(new DriveTurn(30, 0.4, 0, 1));
//			addSequential(new Delay(0.5));
//			addSequential(new AutoDropSequence());
//			addSequential(new DriveTurn(15, -0.6, 0, 2));
//			addSequential(new DriveTurn(30, -0.6, -0.8, 2));
		}
		
		// If right scale is ours
		else {
			// ***** Place a cube in the right scale *****
//			addSequential(new DriveTurn(30, 0.5, 0, 1)); // Slow start to not jerk the robot
//			addParallel(new SetElevatorPosition(40000));
//			addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 100), 0.8, 0, 4)); // Goes straight to decision point
//			addSequential(new GyroTurning(90, 1.3));
//			addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 100), 0.8, 0, 4));
//			addSequential(new GyroTurning(-90, 1.3));
//			addSequential(new DriveTurn(40, 0.7, 0, 4));
//			addSequential(new AutoDropSequence());
//			
//			// Backup sequence
//			addSequential(new DriveTurn(40, -0.4, 0, 3));
//			addSequential(new SetElevatorPosition(Constants.ELEVATOR_BOTTOM_ELEVATION));
			
			if (AutoControl.toScalePreference == AutoControl.ToScalePreferences.canGoToSwitch) {
				if (PlateColorChecker.leftSwitchColor()) {
					addSequential(new LeftToSwitch());
				} else {
					// ***** Cross the auto line *****
					addSequential(new AutoBrettV5());
				}
			} else {
				// ***** Cross the auto line *****
				addSequential(new AutoBrettV5());
			}
		}
	}
}
