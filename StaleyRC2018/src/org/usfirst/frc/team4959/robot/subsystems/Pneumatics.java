package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.commands.Pneumatics.RunCompressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics extends Subsystem {
	
	Solenoid shifter = new Solenoid(0);
	Solenoid shifter2 = new Solenoid(1);
	Compressor compressor = new Compressor(0);

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
	// Shifts the gearbox up
	public void shifterOn() {
		shifter2.set(true);
		shifter.set(false);
	}

	// Shifts the gearbox down
	public void shifterOff() {
		shifter.set(true);
		shifter2.set(false);
	}

}

