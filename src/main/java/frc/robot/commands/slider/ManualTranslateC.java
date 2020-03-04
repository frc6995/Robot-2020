package frc.robot.commands.slider;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SliderS;
import java.util.function.DoubleSupplier;

/**
 * The command that allows the robot to manually translate on the switch in
 * response to user input.
 * 
 * @author JoeyFabel
 */
public class ManualTranslateC extends CommandBase {

  private DoubleSupplier slide;

  /**
   * The command that allows the robot to manually translate on the switch in
   * respone to user input.
   * 
   * @param sliderS   The instance of the slider subsystem used by the robot.
   * @param slideAxis The double supplier providing the value of the joystick axis
   *                  for input.
   */
  public ManualTranslateC(SliderS sliderS, DoubleSupplier slideAxis) {
    addRequirements(sliderS);
    this.slide = slideAxis;
  }

  /**
   * Initializes the command by having the slider remain stationary.
   */
  @Override
  public void initialize() {
    RobotContainer.sliderS.translate(0);
  }

  /**
   * The main part of the command. Checks the value of the input axis against a
   * deadzone, and, if greater than the deadzone, gives that value as the
   * translation speed.
   */
  @Override
  public void execute() {
    double speed = this.slide.getAsDouble();
    if (Math.abs(speed) < 0.1) speed = 0;
    RobotContainer.sliderS.translate(speed);
  }

  /**
   * If the manual translate command is interupted, tell the slider to stop
   * moving.
   */
  @Override
  public void end(boolean interrupted) {
    RobotContainer.sliderS.translate(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}