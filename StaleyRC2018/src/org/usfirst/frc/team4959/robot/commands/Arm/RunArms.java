package org.usfirst.frc.team4959.robot.commands.Arm;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * This command is always running.  
 * Allows the two motors on the arm mechanism to be controlled by input from the controller.
 */
public class RunArms extends Command {

    public RunArms() {
    	requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm.moveTopArm(Robot.m_oi.getLeftStickYCont2());
    	Robot.arm.moveBottomArm(Robot.m_oi.getRightStickYCont2());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
