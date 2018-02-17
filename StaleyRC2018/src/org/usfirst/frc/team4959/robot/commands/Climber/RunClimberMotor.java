package org.usfirst.frc.team4959.robot.commands.Climber;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the winch to pull us up to the rung
 */
public class RunClimberMotor extends Command {

	private double power;

	public RunClimberMotor(double power) {
		requires(Robot.climber);
		this.power = power;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@SuppressWarnings("static-access")
	protected void execute() {
		if (Robot.m_oi.getRightStickYCont2() >= 0.10 || Robot.m_oi.getRightStickYCont2() <= -0.1) {
			Robot.climber.runClimber(power);
			Robot.m_oi.xboxControllertwo.setRumble(GenericHID.RumbleType.kRightRumble, Math.abs(Robot.m_oi.getRightStickYCont2()));
		}
		else {
			Robot.m_oi.xboxControllertwo.setRumble(GenericHID.RumbleType.kRightRumble, 0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.climber.runClimber(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
