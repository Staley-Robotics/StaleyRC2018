package org.usfirst.frc.team4959.robot.commands.auto.autoCommands;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4959.robot.util.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses encoders to drive to a desired distance while also be capable of turning at the same time.
 */
public class DriveTurn extends Command {
	private DriveTrain driveTrain;
	
	private double desiredDistance;
	private double desiredPower;
	private double currentDisplacement;
	// Sets distance from when it starts using user-made PID controls motor power
	private int pThreshold = 5;
	
	private double stopThreshold = 0.5;
	private Timer timer;
	private double time;
	private double turn = 0;

    public DriveTurn(double inches, double power, double turn, double seconds) {
        requires(Robot.driveTrain);
        driveTrain = Robot.driveTrain;
        this.turn = turn;
        desiredDistance = inches;
        desiredPower = power;
        time  = seconds;
        timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	driveTrain.resetEncoders();
    	driveTrain.resetNavx();
    	if(desiredPower < 0) {
    		driveTrain.rightEncoder.setDistancePerPulse((4 * Math.PI) / Constants.ENCODER_DISTANCE_PER_PULSE_NEGATIVE);
    		driveTrain.leftEncoder.setDistancePerPulse((4 * Math.PI) / Constants.ENCODER_DISTANCE_PER_PULSE_NEGATIVE);
    	}
    	else {
    		driveTrain.rightEncoder.setDistancePerPulse((4 * Math.PI) / Constants.ENCODER_DISTANCE_PER_PULSE_POSITIVE);
    		driveTrain.leftEncoder.setDistancePerPulse((4 * Math.PI) / Constants.ENCODER_DISTANCE_PER_PULSE_POSITIVE);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentAngle = driveTrain.getYaw();
    	currentDisplacement = ((driveTrain.getRightEncoderDistance()) + driveTrain.getLeftEncoderDistance()) / 2;

    	double error = Math.abs(desiredDistance) - Math.abs(currentDisplacement);
    	System.out.println("Error:" + error);
    	if(error < pThreshold) {
    		if(turn == 0 && desiredPower > 0) {
    			System.out.println("gyro");
    			driveTrain.arcadeDrive(error * 0.2, 0);
    		} else if(turn == 0 && desiredPower < 0) {
    			System.out.println("gyro");
    			driveTrain.arcadeDrive(-error * 0.2, 0);
    		} else {
    			System.out.println("turn");
    			driveTrain.arcadeDrive(desiredPower, turn);
    		}
    	} else {
    		if(turn == 0) {
    			System.out.println("Current Angle: " + currentAngle);
    			driveTrain.arcadeDrive(desiredPower, -currentAngle * 0.1);
    		} else {
    			System.out.println("turn");
    			driveTrain.arcadeDrive(desiredPower, turn);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	System.out.println("Current Displacement: " + currentDisplacement);
    	if (  Math.abs(currentDisplacement - desiredDistance) <= stopThreshold )
    		System.out.println("Ended with distance");
    	else if (timer.get() > time)
    		System.out.println("Timed out");
        return Math.abs(currentDisplacement - desiredDistance) <= stopThreshold || timer.get() > time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("end");
    	System.out.println("Left Encoder: " + driveTrain.getLeftEncoderDistance());
    	System.out.println("Right Encoder: " + driveTrain.getRightEncoderDistance());
    	System.out.println("Current Displacement: " + currentDisplacement);
    	System.out.println("Current Yaw: " + driveTrain.getTrueAngle());
    	driveTrain.arcadeDrive(0, 0);
    	driveTrain.resetNavx();
    	driveTrain.resetEncoders();
    	
    	System.out.println(Robot.gameData);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
