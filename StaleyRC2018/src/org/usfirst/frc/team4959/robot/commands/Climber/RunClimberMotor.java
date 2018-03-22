package org.usfirst.frc.team4959.robot.commands.Climber;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.util.Constants;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the winch to pull us up to the rung
 */
public class RunClimberMotor extends Command {
	
	@SuppressWarnings("unused")
	private final String TAG = (this.getName() + ": ");

	private double power;

	public RunClimberMotor() {
		requires(Robot.climber);
		this.power = power;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@SuppressWarnings("static-access")
	protected void execute() {
		power = Robot.m_oi.getRightStickYCont1();
		if (Robot.m_oi.getRightStickYCont1() >= Constants.JOYSTICK_Y_AXIS_DEADZONE || Robot.m_oi.getRightStickYCont1() <= -Constants.JOYSTICK_Y_AXIS_DEADZONE) {
			Robot.climber.runClimber(power);
			System.out.println(TAG + "Powering climber"	);
//			Robot.m_oi.xboxController.setRumble(GenericHID.RumbleType.kRightRumble, Math.abs(Robot.m_oi.getRightStickYCont1()));
		} else {
//			Robot.m_oi.xboxController.setRumble(GenericHID.RumbleType.kRightRumble, 0);
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
