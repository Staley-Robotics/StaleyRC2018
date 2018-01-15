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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls basic drive functionality of the robot
 */
public class DriveTrain extends Subsystem {

	private AHRS navx;

	private Encoder leftEncoder;
	private Encoder rightEncoder;

	PIDSource drivePS;
	PIDOutput drivePO;
	public static PIDController drivePos;

	private Victor frontLeft;
	private Victor rearLeft;
	private SpeedControllerGroup leftSide;

	private Victor frontRight;
	private Victor rearRight;
	private SpeedControllerGroup rightSide;

	private DifferentialDrive m_drive;

	public DriveTrain() {
		// Encoder setup
		leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		leftEncoder.reset();
		rightEncoder.reset();
		rightEncoder.setReverseDirection(true);
		leftEncoder.setDistancePerPulse((3.5 * Math.PI) / 256);
		rightEncoder.setDistancePerPulse((3.5 * Math.PI) / 256);

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

		// PID setup
		drivePS = new PIDSource() {
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				setPIDSourceType(PIDSourceType.kDisplacement);
			}

			@Override
			public double pidGet() {
				// TODO use two
				return (rightEncoder.getDistance() + leftEncoder.getDistance()) / 2;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return PIDSourceType.kDisplacement;
			}
		};

		drivePO = new PIDOutput() {
			
			@Override
			public void pidWrite(double output) {
				double angle = getYaw();
				SmartDashboard.putNumber("Power", output);
				execute(output, angle * -.05);
			}
		};
		drivePos = new PIDController(.0353, 0.005, 0, drivePS, drivePO);
		drivePos.setContinuous(false);
		drivePos.setOutputRange(-1, 1);
		drivePos.setAbsoluteTolerance(.5);

		// Live Window stuff
		setName("Drive Train");
		addChild("Drivetrain Position PID", drivePos);
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

	// ***** Drives *****

	public void tankDrive(double speedL, double speedR) {
		m_drive.tankDrive(speedL, speedR);
	}

	public void arcadeDrive(double power, double turn) {
		// leftDrive(power + turn);
		// rightDrive(power + turn);
		m_drive.arcadeDrive(power, turn);
	}

	// ***** PID *****

	// Starts the PID controller to a given setpoint
	public void startDriveToPosition(double position) {
		drivePos.reset();
		leftEncoder.reset();
		rightEncoder.reset();
		navx.reset();
		driveToSetpoint(position);
		drivePos.enable();

	}

	// Stops and resets the PID controller
	public void stopDriveToPosition() {
		drivePos.disable();
		execute(0, 0);
		drivePos.reset();
	}

	// Gives the PID controller a setpoint
	public void driveToSetpoint(double pos) {
		drivePos.setSetpoint(pos);
	}

	// Returns true if the value from the PID controller is within the tolerance
	public boolean isDriveOnTarget() {
		return drivePos.onTarget();
	}

	public PIDController getDrivePID() {
		return drivePos;
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

	// Yaw is like the same thing as the angle we think but idk
	public void resetYaw() {
		navx.zeroYaw();
	}

	public double getYaw() {
		return navx.getAngle();
	}

	public boolean isNavxConnected() {
		return navx.isConnected();
	}

	public double getTrueAngle() {
		return navx.getAngle();
	}

	public double getPitch() {
		return navx.getPitch();
	}

	public double getRoll() {
		return navx.getRoll();
	}

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
