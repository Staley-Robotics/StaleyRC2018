package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses encoders to drive to a desired distance while also be capable of turning
 * at the same time.
 */
public class DriveTurn extends Command {
	
	private final String TAG = (this.getName() + ": ");
	
	DriveTrain driveTrain;
	Timer timer;

	private double desiredDistance;
	private double desiredPower;
	private double currentDisplacement;
	
	private final int pThreshold = 5;
	private final double stopThreshold = 0.5;
	
	private double time;
	private double turn;
	private double startingAngle;
	private double currentAngle;

	public DriveTurn(double inches, double power, double turn, double seconds) {
		requires(Robot.driveTrain);
		driveTrain = Robot.driveTrain;
		timer = new Timer();
		
		desiredDistance = inches;
		desiredPower = -power;
		
		time = seconds;
		this.turn = turn;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		driveTrain.resetEncoders();
		
		startingAngle = driveTrain.getYaw();
		currentAngle = startingAngle;
		
		System.out.println(TAG + "Drive Turn Initialized.  Gyro at: " + startingAngle);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		currentAngle = Robot.driveTrain.getYaw() - startingAngle;
		currentDisplacement = (Robot.driveTrain.rightEncoder.getDistance() + Robot.driveTrain.leftEncoder.getDistance()) / 2;
		
		double error = Math.abs(desiredDistance) - Math.abs(currentDisplacement);
		
		if (error < pThreshold) {
			if (turn == 0 && desiredPower > 0) {
				Robot.driveTrain.arcadeDrive(error * 0.2, 0);
			} else if (turn == 0 && desiredPower < 0) {
				driveTrain.arcadeDrive(-error * 0.2, 0);
			} else {
				Robot.driveTrain.arcadeDrive(desiredPower, turn);
			}
		} else {
			if (turn == 0) {
				Robot.driveTrain.arcadeDrive(desiredPower, currentAngle * .24);
			} else {
				Robot.driveTrain.arcadeDrive(desiredPower, turn);
			}
		}
		System.out.println(TAG + "Gyro: " + currentAngle + "\t\tError: " + error  + "\t\tCurrent Diplacemnt: " + currentDisplacement);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(currentDisplacement - desiredDistance) <= stopThreshold) 
			System.out.println(TAG + "Ended with distance");
		else if (timer.get() > time) 
			System.out.print(TAG + "Drive Turn Time out");
		return Math.abs(currentDisplacement - desiredDistance) <= stopThreshold || timer.get() > time || Math.abs(desiredDistance) - Math.abs(currentDisplacement) < 0;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println(TAG + "Target: " + desiredDistance + "\tCurrent Displacement: " + currentDisplacement + "\tTimer: " + timer.get() + "\n");
		timer.reset();
		Robot.driveTrain.leftDrive(0);
		Robot.driveTrain.rightDrive(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}
}
