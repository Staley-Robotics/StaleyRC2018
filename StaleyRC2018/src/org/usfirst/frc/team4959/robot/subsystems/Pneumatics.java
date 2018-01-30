package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.commands.Pneumatics.RunCompressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem controlling anything that only belongs to Pneumatics
 * Usually only the compressor 
 */
public class Pneumatics extends Subsystem {
	
	Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PORT);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void initDefaultCommand() {
		setDefaultCommand(new RunCompressor());
	}

	// Runs the compressor until the sensor says "Stop don't run anymore" and
	// then it stops
	public void runCompressor() {
		compressor.setClosedLoopControl(true);
	}
}