package frc.wrappers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * Add your docs here.
 */
public class NomadVictorSPX extends WPI_VictorSPX{
    /**
     * Constructs a VictorSPX, reverts it to factory default, and sets brake mode.
     * @param port The CAN ID of this Talon
     */
    public NomadVictorSPX(int port){
        super(port);
        this.configFactoryDefault();
        this.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Constructs a VictorSPX, reverts it to factory default, and sets brake mode and inversion status.
     * @param port The CAN ID of this Talon.
     * @param inverted True for inverted, false if not.
     */
    public NomadVictorSPX(int port, boolean inverted) {
        this(port);
        this.setInverted(inverted);
    }
    /**
     * Constructs a VictorSPX, reverts it to factory default, sets brake mode and inversion status, and slaves it to a specified NomadVictorSPX.
     * @param port The CAN ID of this Talon.
     * @param inverted True for inverted, false if not.
     * @param master The NomadTalonSRX to follow.
     */    
    public NomadVictorSPX(int port, boolean inverted, NomadTalonSRX master){
        this(port, inverted);
        this.follow(master);
    }
}