package org.usfirst.frc.team4959.robot.commands.Auto.AutoCommands;

import org.usfirst.frc.team4959.robot.Robot;
import org.usfirst.frc.team4959.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Rotates the robot using the NavX Gyro 
 */

public class GyroTurning extends Command implements PIDOutput{
	
	private DriveTrain driveTrain;
	private PIDController turnPID;
	private Timer time;
	
	private double angle;
	private double seconds;
	
	// PID Values
	private final double kP = 0.024;
	private final double kI = 0;
	private final double kD = 0.06;
	
    public GyroTurning(double angle, double seconds) {
    	requires(Robot.driveTrain);
        
    	driveTrain = Robot.driveTrain;
    	
    	time = new Timer();
    	time.reset();
    	
    	this.angle = angle;
    	this.seconds = seconds;
    	
    	turnPID = new PIDController(kP, kI, kD, driveTrain.getNavx(), this);
    	// Range of angles that can be inputted
    	turnPID.setInputRange(-180, 180);
    	
    	// prevent the motors from receiving too little power
    	if(angle > 0)
    		turnPID.setOutputRange(0.4, 1);
    	else if(angle < 0)
    		turnPID.setOutputRange(-1, -0.4);
    	
    	// Tolerance of how far off the angle can be
    	turnPID.setAbsoluteTolerance(0.5);
    	turnPID.setContinuous(false);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Gyro initialize");
    	driveTrain.resetNavx();
    	
    	turnPID.setSetpoint(angle);
    	turnPID.enable();
    	
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	System.out.println(driveTrain.getYaw());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return turnPID.onTarget() || time.get() > seconds;
    }

    // Called once after isFinished returns true
    protected void end() {
		SmartDashboard.putNumber("Motor Power", turnPID.get());
    	System.out.println("Finished: "  + driveTrain.getYaw());
    	driveTrain.arcadeDrive(0, 0);
    	turnPID.disable();
    	turnPID.reset();
    	driveTrain.resetNavx();
    	time.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

    // Outputs the motor speed from the PIDController
	@Override
	public void pidWrite(double output) {
		driveTrain.arcadeDrive(0, output);
	}
}
