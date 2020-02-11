package frc.robot.constants;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

public class AutoConstants {

	public static final double kMaxAccelerationMetersPerSecondSquared = 0.5;
	public static final double kMaxSpeedMetersPerSecond = 1;
	public static final double kRamseteB = 2.0;
	public static final double kRamseteZeta = 0.7;

	/**
   * The SimpleMotorFeedForward object for trajectory generation on the talon.
   */
  public static final SimpleMotorFeedforward trajectoryFeedForward = new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                   DriveConstants.kvVoltSecondsPerMeter,
                                   DriveConstants.kaVoltSecondsSquaredPerMeter);
   public static final DifferentialDriveVoltageConstraint autoVoltageConstraint =
      new DifferentialDriveVoltageConstraint(
         new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                    DriveConstants.kvVoltSecondsPerMeter,
                                    DriveConstants.kaVoltSecondsSquaredPerMeter),
         DriveConstants.kDriveKinematics,
         10);
   public static final CentripetalAccelerationConstraint autoCentripetalConstraint =
      new CentripetalAccelerationConstraint(2);
   // Create config for trajectory
   public static final TrajectoryConfig trajectoryConfig =
      new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                           AutoConstants.kMaxAccelerationMetersPerSecondSquared)
         // Add kinematics to ensure max speed is actually obeyed
         .setKinematics(DriveConstants.kDriveKinematics)
         // Apply the voltage constraint
         .addConstraint(autoVoltageConstraint)
         .addConstraint(autoCentripetalConstraint);
   public static final RamseteController ramseteController = new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta);
}
