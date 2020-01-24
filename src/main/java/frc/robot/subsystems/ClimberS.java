package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotPreferences;
import frc.wrappers.MotorControllers.NomadTalonSRX;
import frc.wrappers.MotorControllers.NomadVictorSPX;

public class ClimberS extends SubsystemBase {
  private NomadTalonSRX climbMaster = new NomadTalonSRX(Constants.CAN_ID_CLIMB_TALON);
  private NomadVictorSPX climbSlave = new NomadVictorSPX(Constants.CAN_ID_CLIMB_VICTOR, false, climbMaster);

  private Solenoid brakeSolenoid = new Solenoid(Constants.PCM_ID_CLIMB_BRAKE);

  private DigitalInput magneticLimitSwitch = new DigitalInput(Constants.DIO_CLIMB_MAGNETIC_LIMIT_SWITCH);

  /**
   * Creates a new ClimberS.
   */
  public ClimberS() {
    climbMaster.configVoltageCompSaturation(12);
    climbMaster.enableVoltageCompensation(true);
    climbSlave.configVoltageCompSaturation(12);
    climbSlave.enableVoltageCompensation(true);

    climbMaster.setInverted(false); //slave is already set to false

    climbMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    climbMaster.configSelectedFeedbackCoefficient(1.0);

    climbMaster.setSensorPhase(false); //not inverted

    climbMaster.configForwardSoftLimitThreshold(Constants.CLIMB_SOFT_LIMIT);
    climbMaster.configForwardSoftLimitEnable(true);

    climbMaster.config_kP(Constants.CLIMBER_PID_SLOT, RobotPreferences.climberKp.getValue();
  }



  public void brake() {
    brakeSolenoid.set(false); //false because no output should be braking
  }

  public void unbrake() {
    brakeSolenoid.set(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
