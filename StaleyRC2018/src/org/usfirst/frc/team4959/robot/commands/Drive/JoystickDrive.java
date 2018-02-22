package org.usfirst.frc.team4959.robot.commands.Drive;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is always active. Control the power to the drive-train with input
 * from a controller.
 */
public class JoystickDrive extends Command {
	
	private final String TAG = (this.getName() + ": ");

	private DriveTrain driveTrain;

	public JoystickDrive() {
		requires(Robot.driveTrain);
		driveTrain = Robot.driveTrain;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		driveTrain.worldOfTanksDrive(Robot.m_oi.getLeftTriggerCont1(), Robot.m_oi.getRightTriggerCont1(), Robot.m_oi.getLeftStickXCont1());
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
