package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.SliderConstants;
import frc.wrappers.MotorControllers.NomadVictorSPX;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

/**
 * The Slider subsystem that enables the robot to translate on the switch.
 * 
 * @author JoeyFabel
 */
public class SliderS extends SubsystemBase implements Loggable {
  private NomadVictorSPX sliderVictor = new NomadVictorSPX(SliderConstants.CAN_ID_SLIDER_CONTROLLER);
  @Log(name = "Slider Gyro", width = 3, height = 3, columnIndex = 0, rowIndex = 0)
  private ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);

  public SliderS() {
  }

  /**
   * Function to translate the robot: positve forward, negative backward.
   * 
   * @param speed how fast the robot should translate
   */
  public void translate(double speed) {
    if (speed == 0)
      sliderVictor.stopMotor();
    else
      sliderVictor.set(speed);
  }

  /**
   * function to get the gyro's angle.
   * 
   * @return returns the angle of the gyro.
   */

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  /**
   * function to get the gyro's rate.
   * 
   * @return returns the rate of the gyro.
   */
  @Log.Graph(name = "Gyro Rate", columnIndex = 0, rowIndex = 3, height = 3, width = 5)
  public double getGyroRate() {
    return gyro.getRate();
  }

  @Override
  public void periodic() {
  }
}