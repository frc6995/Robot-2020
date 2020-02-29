package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;
import frc.wrappers.MotorControllers.NomadVictorSPX;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

/**
 * The system we use to grab power cells from the floor and transfer them to the
 * storage system of the robot
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeS extends SubsystemBase implements Loggable {

  //private NomadTalonSRX intakeMotor = new NomadTalonSRX(IntakeConstants.CAN_ID_TALON_INTAKE_MOTOR);
  private NomadVictorSPX intakeMotor = new NomadVictorSPX(IntakeConstants.CAN_ID_TALON_INTAKE_MOTOR);
  private DoubleSolenoid intakeSolenoid = new DoubleSolenoid(1, IntakeConstants.DBL_SOLENOID_INTAKE_EXTEND,
      IntakeConstants.DBL_SOLENOID_INTAKE_RETRACT);
  private boolean deployed;

  /**
   * <ul>
   * <li>Motor on</li>
   * <li>Motor off</li>
   * <li>Extend intake</li>
   * <li>Retract intake</li>
   * </ul>
   */
  public IntakeS() {
    // TODO: figure out the inversion
    intakeMotor.setInverted(true);
  }

  /**
   * Deploy Solenoid
   */
  public void intakeDeploy() {
    // TODO: kforward?
    intakeSolenoid.set(Value.kForward);
    deployed = true;
  }

  /**
   * Retract Solenoid
   */
  public void intakeRetract() {
    // TODO: kreverse?
    intakeSolenoid.set(Value.kReverse);
    deployed = false;
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
  @Log
  public boolean isDeployed(){
    return deployed;
  }
}