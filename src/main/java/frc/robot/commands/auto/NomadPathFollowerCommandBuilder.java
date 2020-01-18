/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.constants.AutoConstants;

public class NomadPathFollowerCommandBuilder {
  private DrivebaseS drivetrain;
  private String filename;
  private Trajectory trajectory;
  /**
   * Creates a new NomadPathFollowerC.
   */
  public NomadPathFollowerCommandBuilder(String filename, DrivebaseS drivebaseS) {
    drivetrain = drivebaseS;
    this.filename = filename;
    // Create a voltage constraint to ensure we don't accelerate too fast
  }
  
  public SequentialCommandGroup buildPathFollowerCommandGroup() {
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                       DriveConstants.kvVoltSecondsPerMeter,
                                       DriveConstants.kaVoltSecondsSquaredPerMeter),
            DriveConstants.kDriveKinematics,
            10);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                             AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DriveConstants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow.  All units in meters.
    try{
      if(RobotBase.isReal()){
          trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/" + filename + ".wpilib.json"));
      } 
      else {
        trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get("src/main/deploy/" + filename + ".wpilib.json"));
      }    
    } catch (IOException e) {
      System.out.println("Cannot load trajectory file " + filename + ":" + e.getStackTrace());
          trajectory = TrajectoryGenerator.generateTrajectory(drivetrain.getPose(), List.of(drivetrain.getPose().getTranslation()), drivetrain.getPose(), config);
    }      

    RamseteCommand ramseteCommand = new RamseteCommand(
        trajectory,
        drivetrain::getPose,
        new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
        new SimpleMotorFeedforward(DriveConstants.ksVolts,
                                   DriveConstants.kvVoltSecondsPerMeter,
                                   DriveConstants.kaVoltSecondsSquaredPerMeter),
        DriveConstants.kDriveKinematics,
        drivetrain::getWheelSpeeds,
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        drivetrain::tankDriveVolts,
        drivetrain
    );

    return (ramseteCommand.andThen(() -> drivetrain.tankDriveVolts(0, 0)));
  }
}
