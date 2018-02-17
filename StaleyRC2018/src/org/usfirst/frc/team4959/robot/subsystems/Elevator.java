package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.commands.Elevator.MoveElevator;
import org.usfirst.frc.team4959.robot.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that controls our elevator system that lifts power cubes to certain
 * elevations
 */
public class Elevator extends Subsystem {

	public WPI_TalonSRX talon;
	public WPI_TalonSRX talon2;

	boolean brake = false;

	// PID values
	private final double kP = 0.037;
	private final double kI = 0;
	private final double kD = 0.3;

	public Elevator() {
		// Setting up the first TalonSRX
		try {
			talon = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_PORT_ONE);
			// Brake mode if brake is true Coast Mode if brake is false
			talon.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
			talon.setInverted(true);
			// Configures the sensor hooked up to it to be an encoder.
			talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			talon.setSensorPhase(true); // Inverts the sensor

			talon.configPeakOutputForward(1.0, 0); // Max power going up
			talon.configPeakOutputReverse(-1.0, 0); // Max power going down
			talon.configNominalOutputForward(0.30, 0); // Minimal power going up
			talon.configNominalOutputReverse(-0.30, 0); // Minimal power going down
			talon.configForwardSoftLimitThreshold(Constants.FWD_SOFT_LIMIT, 0); // The farthest distance it can go up
			talon.configReverseSoftLimitThreshold(Constants.REV_SOFT_LIMIT, 0); // The farthest distance it can go down
			talon.configForwardSoftLimitEnable(true, 0);
			talon.configReverseSoftLimitEnable(true, 0);

			zeroPosition();

			// Set PID values
			talon.config_kP(0, kP, 0);
			talon.config_kI(0, kI, 0);
			talon.config_kD(0, kD, 0);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error Instantiating TalonSRX: " + ex.getMessage(), true);
		}
		// Setting up the first TalonSRX
		try {
			talon2 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_PORT_ONE);
			// Brake mode if brake is true Coast Mode if brake is false
			talon2.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
			talon2.setInverted(true);

			talon2.configPeakOutputForward(1.0, 0); // Max power going up
			talon2.configPeakOutputReverse(-1.0, 0); // Max power going down
			talon2.configNominalOutputForward(0.30, 0); // Minimal power going up
			talon2.configNominalOutputReverse(-0.30, 0); // Minimal power going down
			talon2.configForwardSoftLimitThreshold(Constants.FWD_SOFT_LIMIT, 0); // The farthest distance it can go up
			talon2.configReverseSoftLimitThreshold(Constants.REV_SOFT_LIMIT, 0); // The farthest distance it can go down
			talon2.configForwardSoftLimitEnable(true, 0);
			talon2.configReverseSoftLimitEnable(true, 0);

			// Set PID values
			talon.config_kP(0, kP, 0);
			talon.config_kI(0, kI, 0);
			talon.config_kD(0, kD, 0);
			
//			talon2.follow(talon);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error Instantiating TalonSRX: " + ex.getMessage(), true);
		}
	}

	// ***** Elevator Motor Control *****
	public void initDefaultCommand() {
		setDefaultCommand(new MoveElevator());
	}

	// Lets us know if the first talon SRX is alive
	public boolean talonOneIsAlive() {
		return talon.isAlive();
	}
	
	// Lets us know if the second talon SRX is alive
	public boolean talonTwoIsAlive() {
		return talon2.isAlive();
	}

	// Returns what percent of motor power is being used by the first elevator motor
	public double getTalonOneMotorPower() {
		return talon.getMotorOutputPercent();
	}
	
	// Returns what percent of motor power is being used by the second elevator motor
	public double getTalonTwoMotorPower() {
		return talon2.getMotorOutputPercent();
	}

	// Used to set a position for the elevator to move to and makes it begin moving.
	public void setPosition(double position) {
		// Sets minimal power to send to motor when moving on its own
		talon.configNominalOutputForward(0.1, 0);
		talon.configNominalOutputReverse(-0.1, 0);
		talon2.configNominalOutputForward(0.1, 0);
		talon2.configNominalOutputReverse(-0.1, 0);

		// Setting position to go to
		talon.set(ControlMode.Position, position);
	}

	// Powers the elevator motor with specified power
	public void moveElevator(double power) {
		// Sets minimal power to send to motors when controlled by player
		talon.configNominalOutputForward(0.15, 0);
		talon.configNominalOutputReverse(-0.15, 0);
		talon2.configNominalOutputForward(0.15, 0);
		talon2.configNominalOutputReverse(-0.15, 0);
	}

	// Zeros the encoder connected to the talon SRX
	public void zeroPosition() {
		talon.setSelectedSensorPosition(0, 0, 0);
	}

	// Returns the current encoder value
	public double getPosition() {
		return talon.getSelectedSensorPosition(0);
	}

	// Stops the elevator
	public void stopElevator() {
		talon.stopMotor();
		talon2.stopMotor();
	}
}
