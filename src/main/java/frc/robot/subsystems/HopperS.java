package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotPreferences;
import frc.robot.constants.HopperConstants;
import frc.wrappers.MotorControllers.NomadTalonSRX;
import frc.wrappers.MotorControllers.NomadVictorSPX;
import io.github.oblarg.oblog.Loggable;
/**
 * The Subsystem that holds our balls
 * 
 * @author JoeyFabel
 */
public class HopperS extends SubsystemBase implements Loggable {
  /**
   * The victors that controls the hopper tubes
   */
  private NomadTalonSRX hopperTalon = new NomadTalonSRX(HopperConstants.CAN_ID_HOPPER_TALON, true);
  private NomadVictorSPX hopperVictor = new NomadVictorSPX(HopperConstants.CAN_ID_HOPPER_VICTOR);

  /**
   * The solenoid that controls the hopper pistons
   */
  public HopperS() {

  }

  @Override
  public void periodic() {
  }

  /**
   * The function that spins the hopper tubes at preference speed
   * 
   * @param reversed should the motor spin in reverse?
   */
  public void spinTubes(Boolean reversed) {
    if (reversed) {
      hopperTalon.set(-RobotPreferences.hopperSpeed.getValue());
      hopperVictor.set(-RobotPreferences.hopperSpeed.getValue());
    }
    else {
      hopperTalon.set(RobotPreferences.hopperSpeed.getValue());
      hopperVictor.set(RobotPreferences.hopperSpeed.getValue());
    }
  }

  public void spinTubes(Boolean reversed, double speed) {
    if (reversed) {
      hopperTalon.set(-speed);
      hopperVictor.set(-speed);
    }
    else {
      hopperTalon.set(speed);
      hopperVictor.set(speed);
    }
  }

  public void spinTube(Boolean reversed, int tube) {
    double speed = reversed ? -RobotPreferences.hopperSpeed.getValue() : RobotPreferences.hopperSpeed.getValue();
    if (tube == 1) {
      hopperTalon.set(speed);
    }
    else if (tube == 2) {
      hopperVictor.set(speed);
    }
  }

  public void spinTube(Boolean reversed, int tube, double spinSpeed) {
    double speed = reversed ? -spinSpeed : spinSpeed;
    if (tube == 1) {
      hopperTalon.set(speed);
    }
    else if (tube == 2) {
      hopperVictor.set(speed);
    }
  }

  /**
   * Stops the hopper motor from spinning
   */
  public void stopTubes() {
    hopperTalon.stopMotor();
    hopperVictor.stopMotor();
  }
}