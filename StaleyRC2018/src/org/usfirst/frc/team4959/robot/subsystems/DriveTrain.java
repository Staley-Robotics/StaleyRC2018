package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;
import org.usfirst.frc.team4959.robot.commands.Drive.JoystickDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Controls basic drive functionality of the robot
 */
public class DriveTrain extends Subsystem {

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
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_PORT_ONE, RobotMap.LEFT_ENCODER_PORT_TWO, false,
				Encoder.EncodingType.k4X);
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_PORT_ONE, RobotMap.RIGHT_ENCODER_PORT_TWO, false,
				Encoder.EncodingType.k4X);
		leftEncoder.reset();
		rightEncoder.reset();
		rightEncoder.setReverseDirection(true);
		// We don't why distance per pulse is this number, but it works
		leftEncoder.setDistancePerPulse((4 * Math.PI) / RobotMap.ENCODER_DISTANCE_PER_PULSE_POSITIVE);
		rightEncoder.setDistancePerPulse((4 * Math.PI) / RobotMap.ENCODER_DISTANCE_PER_PULSE_POSITIVE);

		// Gyro setup
		navx = new AHRS(SPI.Port.kMXP);

		// Drivetrain setup
		frontLeft = new Victor(RobotMap.FRONT_LEFT_DRIVE_MOTOR_PORT);
		rearLeft = new Victor(RobotMap.REAR_LEFT_DRIVE_MOTOR_PORT);
		leftSide = new SpeedControllerGroup(frontLeft, rearLeft);

		frontRight = new Victor(RobotMap.FRONT_RIGHT_DRIVE_MOTOR_PORT);
		rearRight = new Victor(RobotMap.REAR_RIGHT_DRIVE_MOTOR_PORT);
		rightSide = new SpeedControllerGroup(frontRight, rearRight);

		m_drive = new DifferentialDrive(leftSide, rightSide);
		m_drive.setSafetyEnabled(false);

		// Live Window stuff
		setName("Drive Train");
		addChild("Gyro", navx);
		addChild("Left Encoder", leftEncoder);
		addChild("Right Encoder", rightEncoder);
		addChild("Differential Drive", m_drive);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());
	}

	// ***** Motor Control *****

	public void leftDrive(double speed) {
		leftSide.set(speed);
	}

	public void rightDrive(double speed) {
		rightSide.set(speed);
	}

	public void execute(double power, double turn) {
		arcadeDrive(power, turn);
	}

	public void stopMotors() {
		m_drive.stopMotor();
	}

	// ***** Drives *****

	public void tankDrive(double speedL, double speedR) {
		m_drive.tankDrive(speedL, speedR);
	}

	public void worldOfTanksDrive(double backward, double forward, double rotate) {
		double speedModifier = 1;
		double turnSpeedModifier = 1;

		if (backward * speedModifier < 0) {
			m_drive.arcadeDrive(-backward * speedModifier, rotate * turnSpeedModifier);
		} else if (forward < 0) {
			m_drive.arcadeDrive(forward * speedModifier, rotate * turnSpeedModifier);
		} else {
			m_drive.arcadeDrive(0, rotate * turnSpeedModifier);
		}
	}

	public void arcadeDrive(double power, double turn) {
		// leftDrive(power + turn);
		// rightDrive(power + turn);
		m_drive.arcadeDrive(power, turn);
	}

	// ***** Shifters *****

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

	// ***** Encoders *****

	public double getLeftEncoderDistance() {
		return leftEncoder.getDistance();
	}

	public double getRightEncoderDistance() {
		return rightEncoder.getDistance();
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	// ***** NavX *****

	public void resetNavx() {
		navx.reset();
	}

	// Resets the Yaw value set by user (Z-axis by default)
	public void resetYaw() {
		navx.zeroYaw();
	}

	// Returns the current yaw value
	public double getYaw() {
		return navx.getYaw();
	}

	public boolean isNavxConnected() {
		return navx.isConnected();
	}

	// Returns the yaw angle (Z-axis, in degrees)
	public double getTrueAngle() {
		return navx.getAngle();
	}

	// Returns the X-axis value
	public double getPitch() {
		return navx.getPitch();
	}

	// Returns the Y-axis value
	public double getRoll() {
		return navx.getRoll();
	}

	// Returns the rotation of the yaw gyro, in degrees per second
	public double getRotationSpeed() {
		return navx.getRate();
	}

	public double getQuaternion() {
		return navx.getQuaternionZ();
	}

	public AHRS getNavx() {
		return navx;
	}
}
