package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/*
 * Rotates the robot using the NavX Gyro 
 */

public class GyroTurning extends Command implements PIDOutput {

	private final String TAG = (this.getName() + ": ");

	private DriveTrain driveTrain;
	private PIDController turnPID;
	private Timer time;
	private Timer delay;
	
	private double angle;
	private double seconds;

	// PID Values
	private final double kP = 0.023;
	private final double kI = 0;
	private final double kD = 0.06;

	public GyroTurning(double angle, double seconds) {
		requires(Robot.driveTrain);

		driveTrain = Robot.driveTrain;

		time = new Timer();
		time.reset();

		delay = new Timer();
		delay.reset();

		this.angle = angle;
		this.seconds = seconds;

		turnPID = new PIDController(kP, kI, kD, driveTrain.getNavx(), this);
		// Range of angles that can be inputted
		turnPID.setInputRange(-180, 180);

		// prevent the motors from receiving too little power
		if (angle > 0)
			turnPID.setOutputRange(0.5, 1);
		else if (angle < 0)
			turnPID.setOutputRange(-1, -0.5);

		// Tolerance of how far off the angle can be
		turnPID.setAbsoluteTolerance(1.0);
		turnPID.setContinuous(false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.resetNavx();
		driveTrain.shifterOff();

		// Delay while the gyro is reseting
		delay.start();
		while (delay.get() < 0.1) {
			System.out.println("Delaying Gyro");
		}
		delay.stop();

		time.start();
		turnPID.setSetpoint(angle);
		turnPID.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("Angle: " + driveTrain.getYaw());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return turnPID.onTarget() || time.get() > seconds;
	}

	// Called once after isFinished returns true
	protected void end() {
		driveTrain.arcadeDrive(0, 0);

		System.out.println("Finished: " + driveTrain.getYaw());

		turnPID.disable();
		turnPID.reset();

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

	/*
	 * Outputs the motor speed from the PIDController
	 */
	@Override
	public void pidWrite(double output) {
		driveTrain.arcadeDrive(0, -output);
	}
}
