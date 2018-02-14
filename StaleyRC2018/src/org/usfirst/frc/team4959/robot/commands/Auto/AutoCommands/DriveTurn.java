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
	DriveTrain driveTrain;
	
	double desiredDistance;
	double desiredPower;
	double currentDisplacement;
	int pThreshold = 5;
	
	double stopThreshold = 1;
	Timer timer;
	double time;
	double turn = 0;
    public DriveTurn(double inches, double power, double turn, double seconds) {
    	driveTrain = Robot.driveTrain;
    	requires(Robot.driveTrain);
    	this.turn = turn;
    	desiredDistance = inches;
    	desiredPower = power;
    	time = seconds;
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	Robot.driveTrain.rightEncoder.reset();
    	Robot.driveTrain.leftEncoder.reset();
    	Robot.driveTrain.navx.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentAngle = Robot.driveTrain.getYaw();
    	currentDisplacement = (Robot.driveTrain.rightEncoder.getDistance() + Robot.driveTrain.leftEncoder.getDistance()) / 2;
    	double error = Math.abs(desiredDistance) - Math.abs(currentDisplacement);
    	System.out.println(error);
    	if(error < pThreshold){
    		if(turn == 0){
    			System.out.println("gyro");
        		Robot.driveTrain.arcadeDrive(desiredPower, -currentAngle * .009);
    		} else {
    			System.out.println("turn");
        		Robot.driveTrain.arcadeDrive(desiredPower, turn);
    		}    	} else {
    		if(turn == 0){
    			System.out.println("gyro");
        		Robot.driveTrain.arcadeDrive(desiredPower, -currentAngle * .009);
    		} else {
    			System.out.println("turn");
        		Robot.driveTrain.arcadeDrive(desiredPower, turn);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(currentDisplacement - desiredDistance) <= stopThreshold || timer.get() > time ||
        		Math.abs(desiredDistance) - Math.abs(currentDisplacement) < 0;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Done going to distance");
    	Robot.driveTrain.leftDrive(0);
    	Robot.driveTrain.rightDrive(0);
    	Robot.driveTrain.navx.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
