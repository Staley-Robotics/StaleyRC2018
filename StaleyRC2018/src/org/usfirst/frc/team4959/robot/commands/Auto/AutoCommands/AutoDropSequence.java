package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.commands.Intake.ExpandIntake;
import org.usfirst.frc.team4959.robot.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drops the power cube during auto
 * 
 * AutoRunIntake(power, time)
 */
public class AutoDropSequence extends CommandGroup {

    public AutoDropSequence() {
    	addSequential(new ExpandIntake());
    	addSequential(new Delay(0.25));
		addSequential(new AutoRunIntake(Constants.INTAKE_OUT_SPEED, 1));
    }
}
