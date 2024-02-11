package frc.robot;

// essential API
import edu.wpi.first.wpilibj.TimedRobot;

// API to help move the intake
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//
import edu.wpi.first.wpilibj2.command.Command;


import edu.wpi.first.wpilibj.XboxController;

public class Martinez extends TimedRobot{

    static final XboxController mainController = new XboxController(0);

    // variables for controlling the intake and arm extension
    private final CANSparkMax armAxisY = new CANSparkMax(11, MotorType.kBrushless);
    private final WPI_TalonSRX armAxisX = new WPI_TalonSRX(3);

    private final double armOutputPowerY = 0.6;
    private final double armOutputPowerX = 0.65;

    private final int armCurrentLimitY = 20;

    // something
    private RobotContainer m_robotContainer;


    @Override
    public void robotInit(){

        // initial setup and resetting of the arm's conditions
        armAxisY.setInverted(true);
        armAxisY.setIdleMode(IdleMode.kBrake);
        armAxisY.setSmartCurrentLimit(armCurrentLimitY);

        armAxisY.enableSoftLimit(SoftLimitDirection.kForward, false);
        armAxisY.enableSoftLimit(SoftLimitDirection.kReverse, false);

        // ((CANSparkMax) armYAxis).burnFlash();
        // unsure of what the above line does but it was present in Robot.java and seems necessary

        // 
        armAxisX.setInverted(false);
        armAxisX.setNeutralMode(NeutralMode.Brake);

        // button binding, puts autonomous chooser on the dashboard
        // again, unsure of why this is the way it is, even after looking at RobotContainer.java
        m_robotContainer = new RobotContainer();
    }

    @Override
    public void teleopInit(){

    }
        
    @Override
    public void teleopPeriodic(){

    }

}
