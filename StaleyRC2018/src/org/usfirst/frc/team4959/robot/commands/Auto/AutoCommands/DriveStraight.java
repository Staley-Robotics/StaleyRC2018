package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a certain distance that is passed in.
 * Currently not in use
 */
public class DriveStraight extends Command {

	private DriveTrain driveTrain;
	private double distance;

	// PID objects
	private PIDSource drivePS;
	private PIDOutput drivePO;
	public static PIDController drivePos;

	// PID values
	private final double kP = 0.0353;
	private final double kI = 0.005;
	private final double kD = 0.2; // 0.2

	public DriveStraight(double distance) {
		requires(Robot.driveTrain);
		driveTrain = Robot.driveTrain;
		this.distance = distance;

		// Initializes PID objects
		drivePS = new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				setPIDSourceType(PIDSourceType.kDisplacement);
			}

			@Override
			public double pidGet() {
				return (driveTrain.getLeftEncoderDistance() + driveTrain.getRightEncoderDistance()) / 2;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		};
		drivePO = new PIDOutput() {
			@Override
			public void pidWrite(double output) {
//				driveTrain.execute(output, driveTrain.getYaw() * -0.02);
				driveTrain.execute(output, 0);
			}
		};

		drivePos = new PIDController(kP, kI, kD, drivePS, drivePO);
		drivePos.setContinuous(false);
		drivePos.setOutputRange(-1, 1);
		drivePos.setAbsoluteTolerance(.5);
	}

	// Gives the PID controller a set-point
	public void driveToSetpoint(double pos) {
		drivePos.setSetpoint(pos);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drivePos.reset();
		driveTrain.resetEncoders();
		driveTrain.resetNavx();
		driveToSetpoint(distance);
		drivePos.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("Left Encoder: ", driveTrain.getLeftEncoderDistance());
		SmartDashboard.putNumber("Right Encoder: ", driveTrain.getRightEncoderDistance());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return drivePos.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		drivePos.disable();
		driveTrain.execute(0, 0);
		drivePos.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
