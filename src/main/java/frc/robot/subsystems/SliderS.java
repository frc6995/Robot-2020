package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.wrappers.MotorControllers.NomadVictorSPX;
import io.github.oblarg.oblog.Loggable;

/**
 * The Slider subsystem that enables the robot to translate on the switch.
 */
public class SliderS extends SubsystemBase implements Loggable {
  private NomadVictorSPX sliderVictor= new NomadVictorSPX(Constants.SLIDER_CONTROLLER);
  private ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);

  public SliderS() {
  }
/**
 * Function to translate the robot: positve forward, negative backward.
 * @param speed how fast the robot should translate
 */
  public void translate(double speed){
    if (speed == 0) sliderVictor.stopMotor();
    else sliderVictor.set(speed);
  }  
/**
 * function to get the gyro's angle.
 * @return returns the angle of the gyro.
 */
  public double GetGyroAngle(){
    return gyro.getAngle();      
  }
/**
 * function to get the gyro's rate.
 * @return returns the rate of the gyro.
 */
  public double GetGyroRate(){
    return gyro.getRate();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
