package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * ---------------------
 * Robot 2020 -- K-REN
 * -----------------
 * 
 * @author Sammcdo, EliSauder, JoeyFabel, Shueja, AriShashivkopanazak
 */
public final class Main {
  private Main() {
  }

  /**
   * Main Function
   * 
   * @param args Command Line Arguments
   */
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
