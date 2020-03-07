package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;
<<<<<<< HEAD
import frc.robot.subsystems.RobotLEDS.ledStates;
import frc.wrappers.MotorControllers.NomadVictorSPX;
=======
import io.github.oblarg.oblog.Loggable;
>>>>>>> ff6ec93459b3f9964a5c7ba484e53ab80350c62b

/**
 * The system we use to grab power cells from the floor and transfer them to the
 * storage system of the robot
 * 
 * @author Ari Shashivkopanazak
 */
public class IntakeS extends SubsystemBase implements Loggable  {

  // private NomadTalonSRX intakeMotor = new
  // NomadTalonSRX(IntakeConstants.CAN_ID_TALON_INTAKE_MOTOR);
  private CANSparkMax intakeMotor = new CANSparkMax(IntakeConstants.CAN_ID_SPARK_MAX_MOTOR, MotorType.kBrushless);
  private DoubleSolenoid intakeSolenoid = new DoubleSolenoid(1, IntakeConstants.DBL_SOLENOID_INTAKE_EXTEND,
      IntakeConstants.DBL_SOLENOID_INTAKE_RETRACT);

  /**
   * <ul>
   * <li>Motor on</li>
   * <li>Motor off</li>
   * <li>Extend intake</li>
   * <li>Retract intake</li>
   * </ul>
   */
  public IntakeS() {
    intakeMotor.setInverted(true);
  }

  /**
   * Deploy Solenoid
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
    if (speed != 0) RobotLEDS.robotLEDS.currentState = ledStates.Intake;
    else RobotLEDS.robotLEDS.revertLEDS();
  }

  @Override
  public void periodic() {
  }
}