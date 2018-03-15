package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.AutoControl;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Start on the right side of the field and place the power 
 * cube in the right side of the scale if we own it, otherwise
 * cross the auto line.
 *
 * DriveTurn(inches, power, turn, time) 
 * GyroTurning(angle)
 * SetElevatorPosition(height)
 * Delay(time)
 */
public class RightToScale extends CommandGroup {

	public RightToScale() {
		
		// If right scale is ours
		if (PlateColorChecker.rightScaleColor()) {
			// ***** Place the cube in the right scale *****
			addSequential(new DriveTurn(30, 0.5, 0, 1)); // Slow start to not jerk the robot
			addParallel(new SetElevatorPosition(40000));
			addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 1406), 0.8, 0, 4)); // Goes straight to decision point
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION)); // TODO change to High Scale for comp
			addSequential(new DriveTurn(20, 0.6, -0.72, 3)); // small turn right
			addSequential(new DriveTurn(15, 0.6, 0.4, 3)); // correction left to straight
			addSequential(new DriveTurn(55, 0.8, 0, 4)); // Drive to scale
			addSequential(new DriveTurn(65, 0.5, 0.58, 5)); // turn into scale
			addSequential(new DriveTurn(2, 0.65, 0, 2));
			addSequential(new DriveTurn(1, -0.3, 0, 1));
			addSequential(new Delay(0.5));
			addSequential(new AutoDropSequence()); // Drop cube into scale
			
			// ***** Try to place a second cube in the switch *****
//			if (PlateColorChecker.rightSwitchColor()) {
//				addSequential(new DriveTurn(60, 0.4, 0.6, 4)); // Drive into cube
//				addSequential(new AutoPickupSequence()); // Pick up cube
//				addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION)); // raises elevator to switch hight
//				addSequential(new Delay(1));
//				addSequential(new AutoDropSequence()); // Drops cube in switch
//			}
//			else {
//				addSequential(new DriveTurn(60, 0.4, 0.6, 4)); // Drive into cube
//				addSequential(new AutoPickupSequence()); // Pick up cube
//				addParallel(new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION)); // Turn around while raising elevator
//				addSequential(new GyroTurning(90, 3));
//				addSequential(new AutoDropSequence()); // Drop into scale
//			}
		}
		
		// If left scale is ours
		else {
			if (AutoControl.toScalePreference == AutoControl.ToScalePreferences.canGoToSwitch) {
				if (PlateColorChecker.rightSwitchColor()) {
					// ***** Place a cube in the right switch *****
					addSequential(new DriveTurn(10, 0.8, 0, 1)); // Drives straight
					addSequential(new DriveTurn(2, -0.4, 0, 1)); // Jerkin
					addSequential(new DriveTurn(60, 0.9, 0, 3));
					addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION));
					addSequential(new DriveTurn(28, 0.7, 0.88, 2)); // Drive while turning left
					addSequential(new Delay(0.3));
					addSequential(new DriveTurn(12, 0.6, 0, 1));
					addSequential(new AutoDropSequence());
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
