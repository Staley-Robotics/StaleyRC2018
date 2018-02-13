package org.usfirst.frc.team4959.robot.commands.Intake;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.util.States;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Toggles the piston on the in-take to expand and contract the claws that hold
 * the power cube
 */
public class IntakePistonToggle extends Command {
	private final String TAG = "Intake: ";

	public IntakePistonToggle() {

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (States.intakeClawState == States.IntakeClawStates.closed) {
			Robot.intake.expandIntake();
		} else {
			Robot.intake.closeIntake();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		if (States.intakeClawState == States.IntakeClawStates.closed) {
			States.intakeClawState = States.IntakeClawStates.open;
			System.out.println(TAG + "Expand Intake End");
		} else {
			States.intakeClawState = States.IntakeClawStates.closed;
			System.out.println(TAG + "Close Intake End");
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
