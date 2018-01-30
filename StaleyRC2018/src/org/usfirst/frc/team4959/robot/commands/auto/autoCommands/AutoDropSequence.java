package org.usfirst.frc.team4959.robot.commands.auto.autoCommands;

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
		addSequential(new AutoRunIntake(Constants.INTAKE_OUT_SPEED, 1));
    }
}
