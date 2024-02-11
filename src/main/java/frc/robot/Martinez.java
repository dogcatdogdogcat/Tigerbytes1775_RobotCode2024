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


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Martinez extends TimedRobot{

    static final XboxController mainController = new XboxController(0);

    // variables for controlling the intake and arm extension
    private final CANSparkMax armAxisY = new CANSparkMax(11, MotorType.kBrushless);
    private final WPI_TalonSRX armAxisX = new WPI_TalonSRX(3);

    private final double armOutputPowerY = 0.6;
    private final double armOutputPowerX = 0.65;

    private final int armCurrentLimitY = 20;

     // helper functions, sets arm output powers in horizontal/vertical direction and displays on SmartDash
    public void setArmAxisMotor(String axis, double percent){

        if (axis == "y"){

            armAxisY.set(percent);
            SmartDashboard.putNumber("armAxisY power (in %): ", percent);

            return;
        }

        armAxisX.set(percent);
        SmartDashboard.putNumber("armAxisX power (in %): ", percent);
    }

    /* 
     * #################
     * ##### MODES #####
     * #################
    */

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

    }

    // IMPORTANT: 
    // Make sure all real functionality ceases when disabled. 
    // You could hurt someone if you don't
    @Override
    public void disabledInit(){
        armAxisX.set(0);
        armAxisY.set(0);
    }

    @Override
    public void teleopInit(){}
        
    @Override
    public void teleopPeriodic(){
        double armPower;

        // arm extension
        if (mainController.getLeftTriggerAxis() > 0.5){armPower = armOutputPowerX;}
        // arm retraction
        else if (mainController.getRightTriggerAxis() > 0.5){armPower = -armOutputPowerX;}
        // if joystick stationary, do nothing and let arm sit where it is
        else{armPower = 0.0; armAxisX.setNeutralMode(NeutralMode.Brake);}
        //armXAxis.stopMotor();

        setArmAxisMotor("x", armPower);

        // arm ascension
        if (mainController.getLeftY() > 0.5){armPower = armOutputPowerY;}
        // arm descension
        else if (mainController.getLeftY() < -0.5){armPower = -armOutputPowerY;}
        // if joystick stationary, do nothing and let arm sit where it is
        else{armPower = 0.0; armAxisY.setIdleMode(IdleMode.kBrake);}

        setArmAxisMotor("y", armPower);

    }

}
