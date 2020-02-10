package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The system we use to grab power cells from the floor and transfer them to the storage system of the robot
 * 
 * @author Ari Shashivkopanazak
 * 
 * @version needs more features
 */
public class IntakeS extends SubsystemBase {

  private Talon intakeMotor;
  private DoubleSolenoid intakeSolenoid;

  /**
   * <ul>
   * <li>
   * Motor on
   * </li>
   * <li>
   * Motor off
   * </li>
   * <li>
   * Extend intake
   * </li>
   * <li>
   * Retract intake
   * </li>
   * </ul>
   */
  public IntakeS() {
    intakeMotor = new Talon(30);
    intakeSolenoid = new DoubleSolenoid(31, 32); 

    // Put configs here
  }

  /**
   * Deploy the Solenoid
   */
  public void intakeDeploy() {
    intakeSolenoid.set(Value.kForward);
  }

  /**
   * Retract Solenoid
   */
  public void intakeRetract() {
    intakeSolenoid.set(Value.kReverse);
  }
 
  /**
   * Motor speed
   * 
   * @param speed can be 1.0, 0.0, -1.0
   */
  public void intakeMotor(double speed) {
    intakeMotor.set(speed);
  }

  @Override
  public void periodic() {
  }
}
