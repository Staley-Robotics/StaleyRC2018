package org.usfirst.frc.team4959.robot.subsystems;

import org.usfirst.frc.team4959.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that controls our elevator system that lifts power cubes to certain
 * elevations
 */
public class Elevator extends Subsystem implements PIDSource, PIDOutput {

	private Victor motor1;
	private Victor motor2;
	private Encoder encoder;

	// PID values
	private final double kP = 0.0353;
	private final double kI = 0;
	private final double kD = 0.2;

	private PIDController elevatorPID;

	public Elevator() {
		motor1 = new Victor(RobotMap.ELEVATOR_MOTOR_ONE_PORT);
		motor2 = new Victor(RobotMap.ELEVATOR_MOTOR_TWO_PORT);

		encoder = new Encoder(RobotMap.ELEVATOR_ENCODER_PORT_ONE, RobotMap.ELEVATOR_ENCODER_PORT_TWO, false,
				Encoder.EncodingType.k4X);

		encoder.reset();

		// encoder.setDistancePerPulse((4 * Math.PI) /
		// RobotMap.ENCODER_DISTANCE_PER_PULSE_POSITIVE);

		elevatorPID = new PIDController(kP, kI, kD, this, this);

		elevatorPID.setContinuous(false);
		elevatorPID.setOutputRange(-1, 1);
		elevatorPID.setAbsoluteTolerance(.5);
		elevatorPID.reset();
	}

	public void startPID(double pos) {
		elevatorPID.setSetpoint(pos);
		elevatorPID.enable();
	}

	public void stopPID() {
		elevatorPID.disable();
	}

	/**
	 * @return True if elevator has reached set point
	 */
	public boolean pidOnTarget() {
		return elevatorPID.onTarget();
	}

	public void initDefaultCommand() {

	}

	public void powerElevator(double power) {
		motor1.set(power);
		motor2.set(-power);
	}

	@Override
	public void pidWrite(double output) {
		powerElevator(output);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		setPIDSourceType(PIDSourceType.kDisplacement);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return encoder.getDistance();
	}

}
