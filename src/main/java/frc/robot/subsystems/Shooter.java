package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.ShotRange;

public class Shooter
{
    CANSparkMax leftShooterMotor = new CANSparkMax(Constants.SHOOTER_LEFT_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax rightShooterMotor = new CANSparkMax(Constants.SHOOTER_RIGHT_MOTOR_ID, MotorType.kBrushless);
    
    CANPIDController leftPIDController = leftShooterMotor.getPIDController();

    public Shooter()
    {
        rightShooterMotor.follow(leftShooterMotor, true);
    }

    public void shoot(ShotRange range)
    {
        leftPIDController.setReference(range.requiredRPM, ControlType.kVelocity);
    }
}