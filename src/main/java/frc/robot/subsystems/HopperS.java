package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.wrappers.MotorControllers.NomadVictorSPX;
/**
 * The Subsystem for that holds our balls
 */
public class HopperS extends SubsystemBase {
  /**
   * The victor that controls the hopper tubes
   */
  private NomadVictorSPX hopperVictor = new NomadVictorSPX(Constants.HOPPER_VICTOR_ID);
  /**
   * The solenoid that controls the hopper pistons
   */
  private DoubleSolenoid doubleSolenoid = new DoubleSolenoid(2, 3);
  /**
   * The preferenc variable for the motor power
   */
  private double motorPower = 0.5;

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
    if (reversed) hopperVictor.set(-motorPower);
    else hopperVictor.set(motorPower);
  }
  /**
   * Stops the hopper motor from spinning
   */
  public void StopTubes(){
    hopperVictor.stopMotor();
  }
  /**
   * Extends the pistons, preventing ball flow through the hopper
   */
  public void ExtendPistons(){
    doubleSolenoid.set(DoubleSolenoid.Value.kForward);
  }
  /**
   * retract the pistons, enabling ball flow through the hopper
   */
  public void RetractPistons(){
    doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
  /**
   * stop the pistons where they are
   */
  public void StopPistons(){
doubleSolenoid.set(DoubleSolenoid.Value.kOff);
  }
}
