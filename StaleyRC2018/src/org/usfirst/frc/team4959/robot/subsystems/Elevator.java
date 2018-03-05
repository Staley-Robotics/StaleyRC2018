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
	
	private final String TAG = (this.getName() + ": ");

	public WPI_TalonSRX talon;
	public WPI_TalonSRX talon2;

	boolean brake = true;

	// PID values
	private final double kP = 0.015;
	private final double kI = 0.0;
	private final double kD = 0.7;
	
	private final double NOMINAL_PID = 0.08;
	private final double NOMINAL_MOVE = 0.085;
	private final double PEAK_PID_FWD = 0.9;
	private final double PEAK_PID_REV = -0.8;
	private final double PEAK_MOVE = 1.0;

	public Elevator() {
		// Setting up the first TalonSRX
		try {
			talon = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_PORT_ONE);
			// Brake mode if brake is true Coast Mode if brake is false
			talon.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
			talon.setInverted(false);
			// Configures the sensor hooked up to it to be an encoder.
			talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			talon.setSensorPhase(false); // Inverts the sensor

			talon.configPeakOutputForward(PEAK_MOVE, 0); // Max power going up
			talon.configPeakOutputReverse(-PEAK_MOVE, 0); // Max power going down
			talon.configNominalOutputForward(NOMINAL_MOVE, 0); // Minimal power going up
			talon.configNominalOutputReverse(-NOMINAL_MOVE, 0); // Minimal power going down
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
		// Setting up the second TalonSRX
		try {
			talon2 = new WPI_TalonSRX(RobotMap.ELEVATOR_MOTOR_PORT_TWO);
			// Brake mode if brake is true Coast Mode if brake is false
			talon2.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
			talon2.setInverted(true);

			talon2.configPeakOutputForward(PEAK_MOVE, 0); // Max power going up
			talon2.configPeakOutputReverse(-PEAK_MOVE, 0); // Max power going down
			talon2.configNominalOutputForward(NOMINAL_MOVE, 0); // Minimal power going up
			talon2.configNominalOutputReverse(-NOMINAL_MOVE, 0); // Minimal power going down
			talon2.configForwardSoftLimitThreshold(Constants.FWD_SOFT_LIMIT, 0); // The farthest distance it can go up
			talon2.configReverseSoftLimitThreshold(Constants.REV_SOFT_LIMIT, 0); // The farthest distance it can go down
			talon2.configForwardSoftLimitEnable(true, 0);
			talon2.configReverseSoftLimitEnable(true, 0);
			
			talon2.set(ControlMode.Follower, RobotMap.ELEVATOR_MOTOR_PORT_TWO);

			// Set PID values
//			talon2.config_kP(0, kP, 0);
//			talon2.config_kI(0, kI, 0);
//			talon2.config_kD(0, kD, 0);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error Instantiating TalonSRX: " + ex.getMessage(), true);
		}
		
	}

	// ***** Elevator Motor Control *****
	public void initDefaultCommand() {
		setDefaultCommand(new MoveElevator());
	}

	/**
	 * Lets us know if the first talon SRX is alive
	 * 
	 * @return true If TalonSRX one is alive
	 * @return false If TalonSRX one isn't alive
	 */
	public boolean talonOneIsAlive() {
		return talon.isAlive();
	}

	/**
	 * Lets us know if the second talon SRX is alive
	 * 
	 * @return true if TalonSRX two is alive
	 * @return false if TalonSRX two isn't alive
	 */
	public boolean talonTwoIsAlive() {
		return talon2.isAlive();
	}

	/**
	 * Returns what percent of motor power is being used by the first elevator motor
	 * 
	 * @return percent motor power being sent through TalonSRX one
	 */
	public double getTalonOneMotorPower() {
		return talon.getMotorOutputPercent();
	}

	/**
	 * Returns what percent of motor power is being used by the second elevator motor
	 * 
	 * @return percent motor power being sent through TalonSRX two
	 */
	public double getTalonTwoMotorPower() {
		return talon2.getMotorOutputPercent();
	}

	/**
	 * Used to set a position for the elevator to move to and makes it begin moving.
	 * Sets nominal and peak outputs for elevator motors during PID control
	 * 
	 * @param position encoder value the elevator is to move to
	 */
	public void setPosition(double position) {
		// Sets minimal power to send to motor when moving on its own
		talon.configNominalOutputForward(NOMINAL_PID, 0);
		talon.configNominalOutputReverse(-NOMINAL_PID, 0);
		talon2.configNominalOutputForward(NOMINAL_PID, 0);
		talon2.configNominalOutputReverse(-NOMINAL_PID, 0);
		// Sets max power to send to motor when moving on its own
		talon.configPeakOutputForward(PEAK_PID_FWD, 0);
		talon.configPeakOutputReverse(PEAK_PID_REV, 0);
		talon2.configPeakOutputForward(PEAK_PID_FWD, 0);
		talon2.configPeakOutputReverse(PEAK_PID_REV, 0);

		// Setting position to go to
		talon.set(ControlMode.Position, position);
		talon2.set(ControlMode.Follower, RobotMap.ELEVATOR_MOTOR_PORT_ONE);
	}
	
	// Disables elevator
	public void disableTalonPIDsetPoint() {
		System.out.println(TAG + "disable set point");
//		talon.set(ControlMode.Disabled, 0);
//		talon2.set(ControlMode.Disabled, 0);
		talon.stopMotor();
		talon2.stopMotor();
	}

	/**
	 * Powers the elevator motor with specified power
	 * Sets nominal and peak outputs for elevator motors during joystick control
	 * 
	 * @param power Power sent to the elevator motors
	 */
	public void moveElevator(double power) {
		// Sets minimal power to send to motors when controlled by player
		talon.configNominalOutputForward(NOMINAL_MOVE, 0);
		talon.configNominalOutputReverse(-NOMINAL_MOVE, 0);
		talon2.configNominalOutputForward(NOMINAL_MOVE, 0);
		talon2.configNominalOutputReverse(-NOMINAL_MOVE, 0);
		// Sets max power to send to motor when controlled by player
		talon.configPeakOutputForward(PEAK_MOVE, 0);
		talon.configPeakOutputReverse(-PEAK_MOVE, 0);
		talon2.configPeakOutputForward(PEAK_MOVE, 0);
		talon2.configPeakOutputReverse(-PEAK_MOVE, 0);

		talon.set(ControlMode.PercentOutput, -power);
		talon2.set(ControlMode.Follower, RobotMap.ELEVATOR_MOTOR_PORT_ONE);
	}

	// Zeros the encoder connected to the talon SRX
	public void zeroPosition() {
		talon.setSelectedSensorPosition(0, 0, 0);
	}

	/**
	 * Returns the current encoder value
	 * 
	 * @return current elevator encoder value
	 */
	public double getPosition() {
		return talon.getSelectedSensorPosition(0);
	}

	// Stops the elevator
	public void stopElevator() {
		talon.stopMotor();
		talon2.stopMotor();
	}
}
