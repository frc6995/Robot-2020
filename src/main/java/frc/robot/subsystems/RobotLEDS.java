package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

/**
 * LED options for light Strip
 * 
 * @author JoeyFabel
 */
public class RobotLEDS extends SubsystemBase implements Loggable{
  
  /**
   * A singleton of this subsystem so that the state
   * can be changed without a command.
   */
  public static RobotLEDS robotLEDS;

  public enum ledStates{
    Climbing,
    Climb_Time,
    Intake,
    Auto,
    Disabled,
    Default,
    Shooting
  }

  /**
   * Enum of colors which includes the correct motor speed for ease of use
   */
  public enum ledColors {
    Green_Solid(0.77),
    Gold(0.67),
    Party(-0.97),
    Red_Pulse(-0.25),
    Blue(0.87),
    Green_Pattern(-0.91),
    Orange(0.65), //Green chase is not possible from the docs I saw, so doing orange instead
    Purple(0.91);
    
    public double value;
    
    private ledColors(double sparkValue){
      value = sparkValue;
    }
  }
  
  public boolean isShooting = false;

    public ledStates currentState; 

    private Spark ledStrip = new Spark(0);
  
    //  public static LEDStates ledStates = new LEDStates();

  /**
   * Creates a new RobotLEDS.
   */
  public RobotLEDS() {
    currentState = ledStates.Default;
  }

  /**
   * Automatically updates the LED color based on the current state.
   */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("LED State", currentState.toString());

    if (Timer.getMatchTime() == 0 && currentState != ledStates.Climbing) currentState = ledStates.Climb_Time;

    switch (currentState){
      case Climbing :
        ledStrip.setSpeed(ledColors.Party.value); break;
      case Climb_Time :
        ledStrip.setSpeed(ledColors.Red_Pulse.value); break;
      case Intake :
        ledStrip.setSpeed(ledColors.Blue.value); break;
      case Auto :
        ledStrip.setSpeed(ledColors.Green_Pattern.value); break;
      case Disabled : 
        ledStrip.setSpeed(ledColors.Orange.value); break;//can't find green chase, so doing orange instead
      case Default :
        ledStrip.setSpeed(ledColors.Green_Solid.value); break;
      case Shooting :
        ledStrip.setSpeed(ledColors.Gold.value); break;
      default :      //default is disabled color (green chase/ orange)
        ledStrip.setSpeed(ledColors.Orange.value); break;
    }

  }

  /**
   * Reverts leds to default unless in endgame
   */
  public void revertLEDS(){
    if (Timer.getMatchTime() <= 30) currentState = ledStates.Climb_Time;
    if (Timer.getMatchTime() <= 150 && Timer.getMatchTime() >= 135) currentState = ledStates.Auto;
    // get match appears to return the time left in auto or teleop, but not end game, so if it is 0, it should be endgame
    else if (isShooting) currentState = ledStates.Shooting;
    else currentState = ledStates.Default;
  }

  @Log
  public String getState() {
    return currentState.toString();
  }

}
