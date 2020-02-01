package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.wrappers.MotorControllers.NomadVictorSPX;

public class SliderS extends SubsystemBase {

  private NomadVictorSPX sliderVictor= new NomadVictorSPX(Constants.SLIDER_CONTROLLER);
  private ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);

  public SliderS() {
    final UsbCamera camera = new UsbCamera("cam0", 0);
    CameraServer server = CameraServer.getInstance();
    server.startAutomaticCapture(camera);
  }

  public void translate(double speed){
    if (speed == 0) sliderVictor.stopMotor();
    else sliderVictor.set(speed);
  }  

  public double GetGyroAngle(){
    return gyro.getAngle();      
  }

  public double GetGyroRate(){
    return gyro.getRate();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
