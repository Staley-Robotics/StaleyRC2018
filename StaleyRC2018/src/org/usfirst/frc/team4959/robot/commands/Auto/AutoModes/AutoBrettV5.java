package org.usfirst.frc.team4959.robot.commands.Auto.AutoModes;

import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.Delay;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.DriveTurn;
import org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands.GyroTurning;
import org.usfirst.frc.team4959.robot.commands.Drive.ShifterOff;
import org.usfirst.frc.team4959.robot.commands.Drive.ShifterOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives straight forward to pass auto line
 * 
 * DriveTurn(Inches, Power, Turn, Time)
 */
public class AutoBrettV5 extends CommandGroup {
	
	private final String TAG = (this.getName() + ": ");

	public AutoBrettV5() {
		
		addSequential(new DriveTurn(50, 6, 0, 5));
	}
}
