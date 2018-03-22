package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.AutoDropSequence;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;
import org.usfirst.frc.team4959.robot.util.PlateColorChecker;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drops a power cube at whichever side of the switch belongs to us.
 * 
 * DriveTurn(inches, power, turn, time) 
 * GyroTurning(angle)
 * SetElevatorPosition(height)
 * Delay(time)
 */
public class CenterToSwitch extends CommandGroup {
	
	public CenterToSwitch() {
		addParallel(new SetElevatorPosition(30000)); // Raises elevator to position to move forward without dragging
																	
		// If right switch is ours
		if (PlateColorChecker.rightSwitchColor()) {
			// ***** Place a cube in the right switch *****
			addSequential(new DriveTurn(10, 0.8, 0, 1)); // Drives straight
			addSequential(new DriveTurn(2, -0.4, 0, 1)); // Jerkin
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION));
			addSequential(new DriveTurn(47, 0.7, -0.75, 3)); // Moves forward while turning right
			addSequential(new DriveTurn(40, 0.7, 0.745, 3)); // Moves forward while turning left to straighten back out
			addSequential(new Delay(0.3));
			addSequential(new DriveTurn(20, 0.6, 0, 3)); // Straight into the switch
			addSequential(new AutoDropSequence());
			// Backup sequence
			addSequential(new DriveTurn(40, -0.4, 0, 3));
			addSequential(new SetElevatorPosition(Constants.ELEVATOR_BOTTOM_ELEVATION));
			
			// ***** Try for the a second cube in the switch *****
//			addSequential(new DriveTurn(12, -0.6, 0, 3)); // Straight into the switch
//			addSequential(new DriveTurn(30, -0.7, -0.70, 3)); // Moves forward while turning left to straighten back out
//			addSequential(new DriveTurn(30, -0.7, 0.65, 3)); // Moves forward while turning right
//			addSequential(new GyroTurning(-45, 3)); // Turn out to the left
//			addParallel(new SetElevatorPosition(Constants.ELEVATOR_BOTTOM_ELEVATION));
//			addSequential(new DriveTurn(30, 0.6, 0, 3)); // Drive towards cube pile
//			addSequential(new AutoPickupSequence()); // Pick up cube
			
//			addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION)); // Raise back to switch height
//			addSequential(new DriveTurn(50, 0.4, 0.5, 2)); // Back up
//			addSequential(new GyroTurning(45, 1)); // Turn back towards switch
//			addSequential(new DriveTurn(80, 0.6, 0, 3)); // Drive back to right switch
//			addSequential(new AutoDropSequence()); // Drop cube into switch
		}

		// If left switch is ours
		else {
			//***** Place a cube in the left switch *****
			addSequential(new DriveTurn(10, 0.8, 0, 1)); // Drives straight
			addSequential(new DriveTurn(2, -0.4, 0, 1)); // Jerkin
			addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION));
			addSequential(new DriveTurn(47, 0.7, 0.75, 3)); // Moves forward while turning left
			addSequential(new DriveTurn(5, 0.6, 0, 2));
			addSequential(new DriveTurn(40, 0.7, -0.79, 3)); // Moves forward while turning right to straighten back out
			addSequential(new Delay(0.3));
			addSequential(new DriveTurn(25, 0.6, 0, 3)); // Straight into the switch
			addSequential(new AutoDropSequence());
			// Backup sequence
			addSequential(new DriveTurn(40, -0.4, 0, 3));
			addSequential(new SetElevatorPosition(Constants.ELEVATOR_BOTTOM_ELEVATION));
		}
		
	}
}
