package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.commands.Intake.CloseIntake;
import org.usfirst.frc.team4959.robot.commands.Intake.ExpandIntake;
import org.usfirst.frc.team4959.robot.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Picks up a cube during autonomous
 * 
 * AutoRunIntake(power, time)
 * DriveTurn(inches, power, turn, time)
 * Delay(time)
 */
public class AutoPickupSequence extends CommandGroup {

    public AutoPickupSequence() {
    	addParallel(new AutoRunIntake(Constants.INTAKE_IN_SPEED, 2));
    	addSequential(new ExpandIntake());
    	addSequential(new DriveTurn(5, 0.3, 0, 3));
    	addSequential(new Delay(1));
    	addSequential(new CloseIntake());
    }
}
