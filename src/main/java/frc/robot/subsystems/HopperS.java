package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotPreferences;
import frc.wrappers.MotorControllers.NomadVictorSPX;
/**
 * The Subsystem for that holds our balls
 */
public class HopperS extends SubsystemBase {
  /**
   * The victors that controls the hopper tubes
   */
  private NomadVictorSPX hopperVictor1 = new NomadVictorSPX(Constants.HOPPER_VICTOR_1_ID);
  private NomadVictorSPX hopperVictor2 = new NomadVictorSPX(Constants.HOPPER_VICTOR_2_ID);
  /**
   * The solenoid that controls the hopper pistons
   */  
  public HopperS() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
/**
 * The function that spins the hopper tubes at preference speed
 * @param reversed should the motor spin in reverse?
 */
  public void SpinTubes(Boolean reversed){
    if (reversed) {
      hopperVictor1.set(-RobotPreferences.hopperSpeed.getValue());
      hopperVictor2.set(-RobotPreferences.hopperSpeed.getValue());
    }
    else {
      hopperVictor1.set(RobotPreferences.hopperSpeed.getValue());
      hopperVictor2.set(RobotPreferences.hopperSpeed.getValue());
    }
  }

  public void SpinTube(Boolean reversed, int tube){
    double speed = reversed ? -RobotPreferences.hopperSpeed.getValue() : RobotPreferences.hopperSpeed.getValue();
  if (tube == 1) hopperVictor1.set(speed);
  else if (tube == 2) hopperVictor2.set(speed);

  }
  /**
   * Stops the hopper motor from spinning
   */
  public void StopTubes(){
    hopperVictor1.stopMotor();
    hopperVictor2.stopMotor();
  }

}
