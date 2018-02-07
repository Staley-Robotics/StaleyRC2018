package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4959.robot.util.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Uses encoders to drive to a desired distance while also be capable of turning
 * at the same time.
 */
public class DriveTurn extends Command {
	private DriveTrain driveTrain;

	private double desiredDistance;
	private double desiredPower;
	private double currentDisplacement;
	// Sets distance from when it starts using user-made PID controls motor power
	private int pThreshold = 5;

	private double stopThreshold = 1;
	private Timer timer;
	private double time;
	private double turn = 0;

	public DriveTurn(double inches, double power, double turn, double seconds) {
		requires(Robot.driveTrain);
		driveTrain = Robot.driveTrain;
		this.turn = turn - 0.23;
		desiredDistance = inches;
		desiredPower = -power;
		time = seconds;
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		driveTrain.resetEncoders();
		driveTrain.resetNavx();

//		driveTrain.rightEncoder.setDistancePerPulse((6 * Math.PI) / Constants.ENCODER_DISTANCE_PER_PULSE);
//		driveTrain.leftEncoder.setDistancePerPulse((6 * Math.PI) / Constants.ENCODER_DISTANCE_PER_PULSE);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double currentAngle = driveTrain.getYaw();
		currentDisplacement = driveTrain.ticksToInches(((driveTrain.getRightEncoderDistance()) + driveTrain.getLeftEncoderDistance()) / 2,
				Constants.TICKS_PER_REVOLUTION, Constants.WHEEL_DIAMETER, Constants.GEAR_RATIO);

		double error = Math.abs(desiredDistance) - Math.abs(currentDisplacement);
		if (error < pThreshold) {
			if (turn == 0 && desiredPower > 0) {
				driveTrain.arcadeDrive(error * 0.2, 0);
			} else if (turn == 0 && desiredPower < 0) {
				driveTrain.arcadeDrive(-error * 0.2, 0);
			} else {
				driveTrain.arcadeDrive(desiredPower, turn);
			}
		} else {
			if (turn == 0) {
				// driveTrain.arcadeDrive(desiredPower, -currentAngle * 0.05);
				driveTrain.arcadeDrive(desiredPower, 0 * 0.05);
			} else {
				driveTrain.arcadeDrive(desiredPower, turn);
			}
		}
		
		SmartDashboard.putNumber("Desired Power: ", desiredPower);
		SmartDashboard.putNumber("Turn Power", turn);
		System.out.println("Left Encoder: " + driveTrain.getLeftEncoderDistance());
		System.out.println("Right Encoder: " + driveTrain.getRightEncoderDistance());
		System.out.println("Current Displacement: " + currentDisplacement);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(currentDisplacement - desiredDistance) <= stopThreshold)
			System.out.println("Ended with distance");
		else if (timer.get() > time)
			System.out.println("Drive Turn Timed out");
		else if (Math.abs(desiredDistance) - Math.abs(currentDisplacement) < 0) {
			System.out.println("Ended by going over threshold");
			return true;
		}
		return Math.abs(currentDisplacement - desiredDistance) <= stopThreshold || timer.get() > time;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("end");
		System.out.println("Left Encoder: " + driveTrain.ticksToInches(driveTrain.getLeftEncoderDistance(),
				Constants.TICKS_PER_REVOLUTION, Constants.WHEEL_DIAMETER, Constants.GEAR_RATIO));
		System.out.println("Right Encoder: " + driveTrain.ticksToInches(driveTrain.getRightEncoderDistance(),
				Constants.TICKS_PER_REVOLUTION, Constants.WHEEL_DIAMETER, Constants.GEAR_RATIO));
		System.out.println("Current Displacement: " + currentDisplacement);
		System.out.println("Current Yaw: " + driveTrain.getTrueAngle());

		System.out.println("Target: " + desiredDistance + "\tCurrent Displacement: " + currentDisplacement + "\tTimer: "
				+ timer.get() + "\n");
		timer.reset();
		driveTrain.arcadeDrive(0, 0);
		driveTrain.resetNavx();
		driveTrain.resetEncoders();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}
}
