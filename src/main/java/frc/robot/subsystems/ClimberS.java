package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

  /**A solenoid that controls the friction brake on the elevator. */
  private Solenoid brakeSolenoid = new Solenoid(Constants.PCM_ID_CLIMB_BRAKE);

  private DigitalInput magneticLimitSwitch = new DigitalInput(Constants.DIO_CLIMB_MAGNETIC_LIMIT_SWITCH);

  /**
   * Creates a new ClimberS. This is the elevator 
   * on our robot uses for climbing in endgame.<br><br>
   * This subsystem uses PID to lift the elevator 
   * and then do a pullup on the bar, giving us 
   * 25 points.
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

    climbMaster.config_kP(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKpUp.getValue());
    climbMaster.config_kI(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKiUp.getValue());
    climbMaster.config_kD(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKdUp.getValue());
    climbMaster.config_kF(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKf.getValue());

    climbMaster.config_IntegralZone(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberIZoneUp.getValue());
 
    climbMaster.configClosedloopRamp(0.7);
    climbMaster.configClosedLoopPeakOutput(Constants.CLIMBER_PID_UP_SLOT, 0.5); //tune me pls
  }


  /**
   * Start braking the elevator.<br/><br/>
   * <b>NOTE:</b> This method sets the solenoid output to false.
  */
  public void brake() {
    brakeSolenoid.set(false); //false because no output should be braking
  }

  /**
   * Unbrake the elevator.<br/><br/>
   * <b>NOTE:</b> This sets the solenoid output to true.
  */
  public void unbrake() {
    brakeSolenoid.set(true);
  }

  /**
   * Set the raw elevator power as a number from -1 to 1 inclusive.
   * 
   * @param power The power to apply. It is clamped within (-1,1)
  */
  public void setClimberPower(double power) {
    var pwr = Math.max(Math.min(power, 1.0), -1.0);
    climbMaster.set(ControlMode.PercentOutput, pwr);
  }

  /**
   * Sets the elevator power from -1 to 1 but adds Feed Forward
   * so it will apply enough power to hold in place with an 
   * input of 0.
   * 
   * @param power The power to add Feed Forward to and apply
   */
  public void setClimberPowerFeedForward(double power) {
    setClimberPower(power + RobotPreferences.climberKf.getValue());
  }

  /**
   * This method runs the Elevator PID up to the upper set point in RobotPreferences.
   * It also updates PIDF, i-zone and set point with current values from preferences.
   */
  public void runUpPID() {
    climbMaster.config_kP(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKpUp.getValue());
    climbMaster.config_kI(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKiUp.getValue());
    climbMaster.config_kD(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKdUp.getValue());
    climbMaster.config_kF(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKf.getValue());

    climbMaster.config_IntegralZone(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberIZoneUp.getValue());

    climbMaster.set(ControlMode.Position, RobotPreferences.liftHeight.getValue());
  }

  /**
   * This method runs the PID down to the lower set point stored in robot preferences.
   * It updates PIDF, i-zone and the set point with the values stored in preferences,
   * but uses different values from runUpPID.
   */
  public void runDownPID() {
    climbMaster.config_kP(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKpDown.getValue());
    climbMaster.config_kI(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKiDown.getValue());
    climbMaster.config_kD(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKdDown.getValue());
    climbMaster.config_kF(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberKf.getValue());

    climbMaster.config_IntegralZone(Constants.CLIMBER_PID_UP_SLOT, RobotPreferences.climberIZoneDown.getValue());

    climbMaster.set(ControlMode.Position, RobotPreferences.pullHeight.getValue());
  }

  /**
   * Check whether the magnetic limit switch is flipped, inverted
   * so that true is on and false is off.
   * @return the switch flipped status as a boolean.
   */
  public boolean isHomed() {
    return magneticLimitSwitch.get(); //invert? add a not
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
