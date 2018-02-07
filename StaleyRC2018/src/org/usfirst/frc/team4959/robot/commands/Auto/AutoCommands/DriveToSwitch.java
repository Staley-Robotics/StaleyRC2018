package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.util.FieldDimensions;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToSwitch extends CommandGroup {

    public DriveToSwitch() {
//		addParallel(new SetElevatorPosition(Constants.ELEVATOR_SWITCH_ELEVATION)); // Raises elevator to position to
		addSequential(new DriveTurn(10, 0.5, 0, 1.2));
		addSequential(new DriveTurn(FieldDimensions.DS_TO_SWITCH-40, 0.85, 0, 2)); // Drive Forward to switch
		addSequential(new DriveTurn(40, 0.5, 0, 1.5));
		addSequential(new Delay(0.25));
		addSequential(new AutoDropSequence()); // Drop power cube into switch
		addSequential(new Delay(1.0));
		addSequential(new DriveTurn(-30, -0.75, 0, 2)); // Back off the switch
		addSequential(new DriveTurn(-10, -0.5, 0, 1));
		// Turn to around the switch
    }
}
