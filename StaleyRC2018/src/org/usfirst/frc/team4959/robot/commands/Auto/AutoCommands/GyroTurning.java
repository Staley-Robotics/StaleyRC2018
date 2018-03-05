package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/*
 * Rotates the robot using the NavX Gyro 
 */

public class GyroTurning extends Command {

	private final String TAG = (this.getName() + ": ");

	private DriveTrain driveTrain;
	private Timer time;

	private double targetAngle;
	private double startingAngle;
	private double currentAngle;
	private double error;
	private double seconds;

	private double power;

	private boolean isClockwise;

	// PID Values
	private final double kP = 0.02;

	public GyroTurning(double angle, double seconds) {
		requires(Robot.driveTrain);

		driveTrain = Robot.driveTrain;

		time = new Timer();
		time.reset();

		this.targetAngle = angle;
		this.seconds = seconds;

		power = 0.6;

		isClockwise = angle > 0;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		time.start();

		startingAngle = driveTrain.getYaw();
		targetAngle += startingAngle;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		currentAngle = driveTrain.getYaw();

		if (isClockwise) {
			error = targetAngle - currentAngle;
			double Pout = error * kP;
			
			if (error < 20) {
				driveTrain.arcadeDrive(0, Pout);
			} else {
				driveTrain.arcadeDrive(0, power);
			}
		} else {
			error = currentAngle - targetAngle;
			double Pout = error * kP;
			
			if (error < 20) {
				driveTrain.arcadeDrive(0, Pout);
			} else {
				driveTrain.arcadeDrive(0, power);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
    	return Math.abs(error) < 2 || time.get() > seconds;
    }

	// Called once after isFinished returns true
	protected void end() {
		driveTrain.arcadeDrive(0, 0);

		time.stop();
		time.reset();

		driveTrain.shifterOn();
		System.out.println(TAG + "High gear");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
