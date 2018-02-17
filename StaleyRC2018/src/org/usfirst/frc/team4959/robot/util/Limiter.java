package org.usfirst.frc.team4959.robot.util;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Limiter {
	private static PowerDistributionPanel pdp;

	public Limiter() {
		pdp = new PowerDistributionPanel();
	}

	// Increases the power limit if the current is low enough that it can go faster
	public void elevatorCanGoFaster(int channel) {
		if (LiveVariableStory.elevatorPowerLimiter < 0.99) {
			if (pdp.getCurrent(channel) <= Constants.LOWEST_ELEVATOR_CURRENT) {
				LiveVariableStory.elevatorPowerLimiter += 0.02;
			}
		}
	}

	// Decreases the power limit if the current is too high
	public void elevatorNeedToSlowDown(int channel) {
		if (pdp.getCurrent(channel) >= Constants.HIGHEST_ELEVATOR_CURRENT) {
			LiveVariableStory.elevatorPowerLimiter -= 0.05;
		}
	}
}
