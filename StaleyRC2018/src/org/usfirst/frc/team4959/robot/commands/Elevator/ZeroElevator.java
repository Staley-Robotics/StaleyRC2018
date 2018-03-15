package org.usfirst.frc.team4959.robot.commands.Elevator;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.util.LiveVariableStory;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Zeros the encoder on the elevator
 */
public class ZeroElevator extends Command {
	
	@SuppressWarnings("unused")
	private final String TAG = (this.getName() + ": ");

    public ZeroElevator() {

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.zeroPosition();
    	LiveVariableStory.pos = 0;
    	Robot.m_oi.xboxControllertwo.setRumble(RumbleType.kRightRumble, 1);
    	Robot.m_oi.xboxControllertwo.setRumble(RumbleType.kLeftRumble, 1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.m_oi.xboxControllertwo.setRumble(RumbleType.kRightRumble, 0);
    	Robot.m_oi.xboxControllertwo.setRumble(RumbleType.kLeftRumble, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
