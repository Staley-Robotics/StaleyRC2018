package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.commands.Pneumatics.RunCompressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics extends Subsystem {

    Solenoid shifter = new Solenoid(4);
    Compressor compressor = new Compressor(0);

    public void initDefaultCommand() {
        setDefaultCommand(new RunCompressor());
    }
    
    // Runs the compressor until the sensor says "Stop don't run anymore" and then it stops
    public void runCompressor() {
    	compressor.setClosedLoopControl(true);
    }
    
    // Shifts the gearbox up
    public void shifterOn() {
    	shifter.set(true);
    }
    
    // Shifts the gearbox down
    public void shifterOff() {
    	shifter.set(false);
    }
}

