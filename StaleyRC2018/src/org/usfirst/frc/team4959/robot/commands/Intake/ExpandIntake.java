package org.usfirst.frc.team4959.robot.commands.Intake;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.util.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Expands the claws of the in-take to release a power cube or get ready to pick one up
 */
public class ExpandIntake extends Command {
	
	private final String TAG = (this.getName() + ": ");

    public ExpandIntake() {
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.expandIntake();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	States.intakeClawState = States.IntakeClawStates.open;
    	System.out.println("Expand Intake End\n");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
