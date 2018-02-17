package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.commands.Drive.ShifterOff;
import org.usfirst.frc.team4959.robot.commands.Drive.ShifterOn;
import org.usfirst.frc.team4959.robot.commands.Elevator.SetElevatorPosition;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The code used for driving straight to the switch, dropping the cube there, then backing off
 */
public class DriveToSwitch extends CommandGroup {

    public DriveToSwitch() {
		addSequential(new DriveTurn(10, 0.5, 0, 1.2));
		addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION)); // Raises elevator to position to
		addSequential(new DriveTurn(FieldDimensions.DS_TO_SWITCH-42, 0.7, 0, 2)); // Drive Forward to switch
		addSequential(new DriveTurn(30, 0.4, 0, 1.5));
		addSequential(new ShifterOff());
		addSequential(new Delay(0.25));
		addSequential(new AutoDropSequence()); // Drop power cube into switch
		addSequential(new Delay(0.25));
		addSequential(new ShifterOn());
		addSequential(new DriveTurn(-30, -0.7, 0, 2)); // Back off the switch
		addSequential(new DriveTurn(-10, -0.5, 0, 1));
		// Turn to around the switch
    }
}
