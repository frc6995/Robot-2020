package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeS;
import frc.robot.subsystems.RobotLEDS;
import frc.robot.subsystems.RobotLEDS.ledStates;

/**
 * Open Intake and Run motors
 * 
 * @author Shuja
 */
public class IntakeDeployAndRunCG extends SequentialCommandGroup {
  public IntakeDeployAndRunCG(IntakeS intake) {
    super(new InstantCommand(() -> intake.intakeDeploy(), intake),
<<<<<<< HEAD
    new RunCommand(() -> intake.intakeMotor(0.8), intake));
=======
        new RunCommand(() -> intake.intakeMotor(0.8), intake));
>>>>>>> ff6ec93459b3f9964a5c7ba484e53ab80350c62b
  }
}
