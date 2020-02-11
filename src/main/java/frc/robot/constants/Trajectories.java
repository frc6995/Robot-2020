package frc.robot.constants;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;

public class Trajectories {
    public static Trajectory straight2m = TrajectoryGenerator.generateTrajectory(
        new Pose2d(
        new Translation2d(0.0, 0.0), new Rotation2d(0)),
        List.of(new Translation2d(1.0, 0.0)), 
        new Pose2d(
        new Translation2d(2.0, 0.0), new Rotation2d(0)), 
        AutoConstants.trajectoryConfig);
    public static Trajectory sCurveRight = TrajectoryGenerator.generateTrajectory(
        new Pose2d(
        new Translation2d(0.0, 0.0), new Rotation2d(0)),
        List.of(new Translation2d(1.0, -0.5)), 
        new Pose2d(
        new Translation2d(2.0, -1.0), new Rotation2d(0)), 
        AutoConstants.trajectoryConfig);
}