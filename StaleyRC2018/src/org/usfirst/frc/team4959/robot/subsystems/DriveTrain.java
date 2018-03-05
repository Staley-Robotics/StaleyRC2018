package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.commands.Drive.JoystickDrive;
import org.usfirst.frc.team4959.robot.util.Constants;
import org.usfirst.frc.team4959.robot.util.States;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Controls basic drive functionality of the robot
 */
public class DriveTrain extends Subsystem {

	private final String TAG = (this.getName() + ": ");

	public AHRS navx;

	public Encoder leftEncoder;
	public Encoder rightEncoder;

	Solenoid shifter = new Solenoid(RobotMap.SHIFTER_ONE_PORT);
	Solenoid shifter2 = new Solenoid(RobotMap.SHIFTER_TWO_PORT);

	private Victor frontLeft;
	private Victor rearLeft;
	private SpeedControllerGroup leftSide;

	private Victor frontRight;
	private Victor rearRight;
	private SpeedControllerGroup rightSide;

	public DifferentialDrive m_drive;

	public DriveTrain() {
		// Encoder setup
		try {
			leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_PORT_ONE, RobotMap.LEFT_ENCODER_PORT_TWO, false,
					Encoder.EncodingType.k4X);
			leftEncoder.reset();
			leftEncoder.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_PULSE);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error Instantiating Left Encoder: " + ex.getMessage(), true);
		}
		try {
			rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_PORT_ONE, RobotMap.RIGHT_ENCODER_PORT_TWO, false,
					Encoder.EncodingType.k4X);
			rightEncoder.reset();
			rightEncoder.setReverseDirection(true);
			rightEncoder.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_PULSE);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error Instantiating Right Encoder: " + ex.getMessage(), true);
		}

		// Gyro setup
		try {
			navx = new AHRS(SerialPort.Port.kUSB);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error Instantiating navX: " + ex.getMessage(), true);
		}

		// Drivetrain setup
		frontLeft = new Victor(RobotMap.FRONT_LEFT_DRIVE_MOTOR_PORT);
		rearLeft = new Victor(RobotMap.REAR_LEFT_DRIVE_MOTOR_PORT);
		leftSide = new SpeedControllerGroup(frontLeft, rearLeft);

		frontRight = new Victor(RobotMap.FRONT_RIGHT_DRIVE_MOTOR_PORT);
		rearRight = new Victor(RobotMap.REAR_RIGHT_DRIVE_MOTOR_PORT);
		rightSide = new SpeedControllerGroup(frontRight, rearRight);

		m_drive = new DifferentialDrive(leftSide, rightSide);
		m_drive.setSafetyEnabled(false);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());
	}

	// ***** Motor Control *****
	/**
	 * Sends power to the left side of the drive train
	 * 
	 * @param speed Power to send to the left side of the drive train
	 */
	public void leftDrive(double speed) {
		leftSide.set(speed);
	}

	/**
	 * Sends power to the right side of the drive train
	 * 
	 * @param speed Power to send to the right side of the drive train
	 */
	public void rightDrive(double speed) {
		rightSide.set(speed);
	}

	/**
	 * Drives the robot using arcade drive
	 * 
	 * @param power Power sent to the drive train
	 * @param turn Turn power sent to the drive train
	 */
	public void execute(double power, double turn) {
		arcadeDrive(power, turn);
	}

	// Stops drive motors
	public void stopMotors() {
		m_drive.stopMotor();
	}

	// ***** Drives *****

	/**
	 * Sends power to right and left sides individually
	 * Not in use
	 * 
	 * @param speedL Power to send to the motors on the left side of drive train
	 * @param speedR Power to send to the motors on right side of drive train
	 */
	public void tankDrive(double speedL, double speedR) {
		m_drive.tankDrive(speedL, speedR);
	}

	/**
	 * Controls robot using forwards, backwards, and rotation values
	 * The drive we are using right now
	 * 
	 * @param backward Power sent for going reverse from left trigger
	 * @param forward Power sent for going forward from right trigger
	 * @param rotate Rotation power sent to robot from left joystick's x-axis
	 */
	public void worldOfTanksDrive(double backward, double forward, double rotate) {
		double speedModifier = 1;
		double turnSpeedModifier = 0.85;

		backward = backward * speedModifier;
		forward = forward * speedModifier;
		if (rotate > Constants.JOYSTICK_X_AXIS_DEADZONE || rotate < Constants.JOYSTICK_X_AXIS_DEADZONE) {
			rotate = -rotate * turnSpeedModifier;
		} else {
			rotate = 0;
		}

		if (backward > 0) {
			m_drive.arcadeDrive(backward, rotate);
		} else if (forward > 0) {
			m_drive.arcadeDrive(-forward, rotate);
		} else {
			m_drive.arcadeDrive(0, rotate);
		}
	}

	/**
	 * Controls robot's drive train with power and turn values
	 * 
	 * @param power Power sent to robot's drive train
	 * @param turn Turn power sent to robot's drive train
	 */
	public void arcadeDrive(double power, double turn) {
		m_drive.arcadeDrive(power, turn);
	}

	// ***** Shifters *****

	// Shifts the gear-box up
	public void shifterOn() {
		shifter2.set(false);
		shifter.set(true);
		States.shifterState = States.ShifterStates.high;
	}

	// Shifts the gear-box down
	public void shifterOff() {
		shifter.set(false);
		shifter2.set(true);
		States.shifterState = States.ShifterStates.low;
	}

	// ***** Encoders *****
	/**
	 * Returns the current encoder value for the left side of the drive train
	 * 
	 * @return encoder value from encoder on left side
	 */
	public double getLeftEncoderDistance() {
		return leftEncoder.getDistance();
	}

	/**
	 * Returns the current encoder value for the right side of the drive train
	 * 
	 * @return encoder value from encoder on right side
	 */
	public double getRightEncoderDistance() {
		return rightEncoder.getDistance();
	}

	// Resets the values of the encoders on the drive train
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	// Takes in encoder ticks and returns it as inches
	public double ticksToInches(double ticks, double ticksPerRev, double wheelDiameter, double gearRatio) {
		return ((ticks / ticksPerRev) * gearRatio * (wheelDiameter * Math.PI));

	}

	// ***** NavX *****

	// Do not use
	// public void resetNavx() {
	// navx.reset();
	// }

	// Resets the yaw value set by user (Z-axis by default)
	public void resetYaw() {
		navx.zeroYaw();
	}

	/**
	 * Returns the current yaw value
	 * 
	 * @return current yaw value
	 */
	public double getYaw() {
		return navx.getYaw();
	}

	/**
	 * Returns true if Navx is connected to the Roborio
	 * 
	 * @return true If Navx is connect to the Roborio
	 * @return false If Navx isn't connected to the Roborio
	 */
	public boolean isNavxConnected() {
		return navx.isConnected();
	}

	/**
	 * Returns the yaw angle (Z-axis, in degrees)
	 * 
	 * @return yaw angle
	 */
	public double getTrueAngle() {
		return navx.getAngle();
	}

	/**
	 * Returns the X-axis value
	 * 
	 * @return x-axis value
	 */
	public double getPitch() {
		return navx.getPitch();
	}

	/**
	 * Returns the Y-axis value
	 * 
	 * @return y-axis value
	 */
	public double getRoll() {
		return navx.getRoll();
	}

	/**
	 * Returns the rotation of the yaw gyro, in degrees per second
	 * 
	 * @return rotation of yaw gyro in degrees per second
	 */
	public double getRotationSpeed() {
		return navx.getRate();
	}

	/**
	 * 
	 * @return
	 */
	public double getQuaternion() {
		return navx.getQuaternionZ();
	}

	/**
	 * Returns the Navx object being used
	 * 
	 * @return Navx object being used
	 */
	public AHRS getNavx() {
		return navx;
	}
}
