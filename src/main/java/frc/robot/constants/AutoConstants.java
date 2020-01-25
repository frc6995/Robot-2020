package frc.robot.constants;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public class AutoConstants {

	public static final double kMaxAccelerationMetersPerSecondSquared = 0.5;
	public static final double kMaxSpeedMetersPerSecond = 0.5;
	public static final double kRamseteB = 2.0;
	public static final double kRamseteZeta = 0.7;

	/**
   * The SimpleMotorFeedForward object for trajectory generation on the talon.
   */
  public static final SimpleMotorFeedforward trajectoryFeedForward = new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                   DriveConstants.kvVoltSecondsPerMeter,
                                   DriveConstants.kaVoltSecondsSquaredPerMeter);
}
