package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.lightStripConstants;
import io.github.oblarg.oblog.Loggable;

public class LightStripS extends SubsystemBase implements Loggable {
  private static Spark blinkin;
  /**
   * Creates a new lightStripS.
   */
  public LightStripS() {
    blinkin = new Spark(lightStripConstants.PWM_ID_BLINKIN);
  }

  public void setColor(double value) {
    blinkin.set(value);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
