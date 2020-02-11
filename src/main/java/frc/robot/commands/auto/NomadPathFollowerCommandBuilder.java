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
import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.DrivebaseS;

public class NomadPathFollowerCommandBuilder {
  /**
   * The DrivebaseS object to follow the path with.
   */
  private DrivebaseS drivetrain;
  /**
   * The filename (minus path and .wpilib.json) of the Pathweaver JSON.
   */
  private String filename;
  /**
   * The Trajectory to follow.
   */
  private Trajectory trajectory;
  /**
   * Creates a new NomadPathFollowerC from a Pathweaver JSON.
   * @param filename The filename (minus path and .wpilib.json) of the Pathweaver JSON.
   * @param drivebaseS The DrivebaseS object to follow the path with.
   */
  public NomadPathFollowerCommandBuilder(String filename, DrivebaseS drivebaseS) {
    drivetrain = drivebaseS;
    this.filename = filename;
    // Create a voltage constraint to ensure we don't accelerate too fast
  }
  
  public SequentialCommandGroup buildPathFollowerCommandGroup() {

    // An example trajectory to follow.  All units in meters.
    if(filename != null) {
      try{
        if(RobotBase.isReal()){
            trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/output/" + filename + ".wpilib.json"));
        } 
        else {
          trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get("src/main/deploy/output/" + filename + ".wpilib.json"));
        }    
      } catch (IOException e) {
        System.out.println("Cannot load trajectory file " + filename + ":" + e.getStackTrace());
            trajectory = TrajectoryGenerator.generateTrajectory(drivetrain.getPose(), List.of(drivetrain.getPose().getTranslation()), drivetrain.getPose(), AutoConstants.trajectoryConfig); //trajectory = hold still.
      }      
    }
    RamseteCommand ramseteCommand = new RamseteCommand(
        trajectory,
        drivetrain::getPose,
        AutoConstants.ramseteController,
        
        DriveConstants.kDriveKinematics,
        (BiConsumer<Double,Double>) (Double leftSpeed, Double rightSpeed) -> {
          drivetrain.trajectoryDrive(leftSpeed, rightSpeed);
          SmartDashboard.putNumber("leftSpeedSetpoint", leftSpeed);
          SmartDashboard.putNumber("rightSpeedSetpoint",rightSpeed);
          },
        // RamseteCommand passes speed to the callback

        drivetrain
    );

    return (ramseteCommand.andThen(() -> drivetrain.trajectoryDrive(0, 0)));
  }


  /**
   * Creates a new NomadPathFollowerC.
   * @param trajectory The Trajectory to follow.
   * @param drivebaseS The DrivebaseS object to follow the path with.
   */
  public NomadPathFollowerCommandBuilder(Trajectory trajectory, DrivebaseS drivebaseS) {
    drivetrain = drivebaseS;
    this.trajectory = trajectory;
    // Create a voltage constraint to ensure we don't accelerate too fast
  }
  
  
}
