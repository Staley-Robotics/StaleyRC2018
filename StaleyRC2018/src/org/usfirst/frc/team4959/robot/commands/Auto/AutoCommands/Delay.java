package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Delays in time for given amount of seconds
 */
public class Delay extends Command {
	
	private final String TAG = (this.getName() + ": ");
	
	private double seconds;
	private Timer time = new Timer();

	public Delay(double seconds) {
		this.seconds = seconds;
		time.reset();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		time.start();
		System.out.println(TAG + "Delay has started");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (time.get() > seconds);
	}

	// Called once after isFinished returns true
	protected void end() {
		time.stop();
		System.out.println(TAG + "Delay has ended\n");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}