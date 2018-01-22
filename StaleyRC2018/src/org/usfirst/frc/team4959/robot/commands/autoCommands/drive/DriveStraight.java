package org.usfirst.frc.team4959.robot.commands.autoCommands.drive;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a certain distance that is passed in.
 */
public class DriveStraight extends Command {

	private DriveTrain driveTrain;
	private double distance;
	private double currentDisplacement;

	public DriveStraight(double distance) {
		requires(Robot.driveTrain);
		driveTrain = Robot.driveTrain;
		this.distance = distance;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.startDriveToPosition(distance);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		currentDisplacement = ((driveTrain.getLeftEncoderDistance() + driveTrain.getRightEncoderDistance()) / 2);
		System.out.println();
		SmartDashboard.putNumber("Left Encoder: ", driveTrain.getLeftEncoderDistance());
		SmartDashboard.putNumber("Right Encoder: ", driveTrain.getRightEncoderDistance());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return driveTrain.isDriveOnTarget();
		// return Math.abs(currentDisplacement - distance) <= 0.5;
	}

	// Called once after isFinished returns true
	protected void end() {
		driveTrain.stopMotors();
		driveTrain.stopDriveToPosition();
		driveTrain.resetNavx();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
