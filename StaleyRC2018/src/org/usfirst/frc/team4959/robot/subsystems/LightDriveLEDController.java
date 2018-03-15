package org.usfirst.frc.team4959.robot.subsystems;

import com.mach.LightDrive.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LightDriveLEDController extends Subsystem {

    LightDriveCAN ldrive_can;
    
    public LightDriveLEDController() {
    	try {
    	ldrive_can = new LightDriveCAN();
    	} catch (RuntimeException ex) {
    		DriverStation.reportError("Error Instantiating LightDrive LED Controller: " + ex.getMessage(), true);
    	}
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setColor (Color color) {
    	ldrive_can.SetColor(2, color);
    	System.out.println(ldrive_can.GetStatus().toString());
    }
    
    public void updateLEDs() {
    	ldrive_can.Update();
    }
}

