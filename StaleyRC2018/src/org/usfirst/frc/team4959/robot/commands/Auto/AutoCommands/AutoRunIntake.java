package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs intake during auto for a specific amount of time
 */
public class AutoRunIntake extends Command {
	
	private double power;
	private double seconds;
	private Timer time = new Timer();

    public AutoRunIntake(double power, double seconds) {
        this.power = power;
        this.seconds = seconds;
        time.reset();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Auto Intake started");
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.succBoi(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (time.get() > seconds);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.succBoi(0);
    	System.out.println("Auto Intake finished\n");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
