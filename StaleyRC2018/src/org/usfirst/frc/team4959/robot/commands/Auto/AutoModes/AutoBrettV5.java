package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Intake.SetPivotPosition;
import org.usfirst.frc.team4959.robot.util.FieldDimensions;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 * 
 * DriveTurn(Inches, Power, Turn, Time)
 */
public class AutoBrettV5 extends CommandGroup {
	
	public AutoBrettV5() {
		
		addParallel(new SetPivotPosition(Robot.intake.getPivotEncoderDistance() - 404, -1));
		
		// ***** Cross the auto line *****
		addSequential(new DriveTurn(FieldDimensions.DS_TO_AUTO_LINE-20, 6, 0, 5));
	}
}
