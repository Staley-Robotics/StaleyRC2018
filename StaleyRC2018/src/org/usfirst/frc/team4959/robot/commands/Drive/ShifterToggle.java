package org.usfirst.frc.team4959.robot.commands.Drive;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.util.States;

import com.mach.LightDrive.Color;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Toggles the shifter to high/low gear
 */
public class ShifterToggle extends Command {
	
	private final String TAG = (this.getName() + ": ");
	
	Color lowColor = Color.RED;
	Color highColor = Color.GREEN;

	public ShifterToggle() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Toggles to high gear
		if (States.shifterState == States.ShifterStates.low) {
			Robot.driveTrain.shifterOn();
			
			Robot.ldrive.setColor(lowColor);
			
			System.out.println(TAG + "High Gear");
		}
		// Toggles to low gear
		else {
			Robot.driveTrain.shifterOff();
			
			Robot.ldrive.setColor(highColor);
			
			System.out.println(TAG + "Low Gear");
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		if (States.shifterState == States.ShifterStates.low) {
//			System.out.println(TAG + "Shifter on End");
		} else {
//			System.out.println(TAG + "Shifter off End");
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}
}
