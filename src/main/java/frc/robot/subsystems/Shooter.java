package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants;

public class Shooter
{
    XboxController controller;
    
    CANSparkMax leftShooterMotor = new CANSparkMax(Constants.SHOOTER_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax rightShooterMotor = new CANSparkMax(Constants.SHOOTER_MOTOR_ID, MotorType.kBrushless);
    //*** probably change the motor IDs
    
    CANEncoder leftEncoder = leftShooterMotor.getEncoder();
    CANEncoder rightEncoder = leftShooterMotor.getEncoder();

    CANPIDController leftPIDController = leftShooterMotor.getPIDController();
    CANPIDController rightPIDController = leftShooterMotor.getPIDController();

    public Shooter(XboxController x)
    {
        controller = x;
        rightShooterMotor.setInverted(true);
    }

    public void teleOp()
    {
        if(controller.getXButton())
        {
            if (controller.getBumper(Hand.kRight))
                this.shoot(ShotRange.kInitLine);
            else
                this.shoot(ShotRange.kTrench);
        }
    }

    public void shoot(ShotRange range)
    {
        leftPIDController.setReference(range.requiredRPM, ControlType.kVelocity);
    }
}