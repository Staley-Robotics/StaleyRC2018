package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.AutoPickupSequence;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.Constants;
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
	
	private final String TAG = (this.getName() + ": ");

	public RightToScale() {
		
		// If right scale is ours
		if (PlateColorChecker.rightScaleColor()) {
			addSequential(new DriveTurn(30, 0.5, 0, 1)); // Slow start to not jerk the robot
			addParallel(new SetElevatorPosition(40000));
			addSequential(new DriveTurn((FieldDimensions.DS_TO_SCALE - 133), 0.8, 0, 4)); // Goes straight to decision point
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_MID_SCALE_ELEVATION)); // TODO change to High Scale for comp
			addSequential(new DriveTurn(15, 0.6, -0.7, 3)); // small turn right
			addSequential(new DriveTurn(15, 0.6, 0.4, 3)); // correction left to straight
			addSequential(new DriveTurn(45, 0.8, 0, 4)); // Drive to scale
			addSequential(new DriveTurn(57, 0.5, 0.53, 5)); // turn into scale
			addSequential(new DriveTurn(1, -0.3, 0, 1));
			addSequential(new Delay(0.5));
			addSequential(new AutoDropSequence()); // Drop cube into scale
			addSequential(new DriveTurn(15, -0.6, 0, 2)); 
			addSequential(new DriveTurn(30, -0.6, 0.8, 2));
			if (PlateColorChecker.rightSwitchColor()) {
				addSequential(new DriveTurn(60, 0.4, 0.6, 4)); // Drive into cube
				addSequential(new AutoPickupSequence()); // Pick up cube
				addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION)); // raises elevator to switch hight
				addSequential(new Delay(1));
				addSequential(new AutoDropSequence()); // Drops cube in switch
			}
			else {
				addSequential(new DriveTurn(60, 0.4, 0.6, 4)); // Drive into cube
				addSequential(new AutoPickupSequence()); // Pick up cube
				addParallel(new SetElevatorPosition(Constants.ELEVATOR_HIGH_SCALE_ELEVATION)); // Turn around while raising elevator
				addSequential(new GyroTurning(90, 3));
				addSequential(new AutoDropSequence()); // Drop into scale
			}
		}
		
		// If left scale is ours
		else {
			addSequential(new DriveTurn(30, 0.5, 0, 1)); // Slow start to not jerk it
			addSequential(new DriveTurn(FieldDimensions.DS_TO_SCALE_DECISION_POINT-35, 1, 0, 3)); // Drives to midway
			addSequential(new DriveTurn(20, 0.8, 0.6, 2.5)); // Turn left
			addSequential(new DriveTurn(60, 1, 0, 2)); // Drive to the left side of the scale
			addSequential(new GyroTurning(90, 1)); // Turn towards the scale
			addSequential(new AutoDropSequence()); // Place the power cube
			addSequential(new DriveTurn(30, -0.5, 0, 2)); // Back off the scale
		}
	}
}
