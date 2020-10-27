package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class Lift
{
    CANSparkMax leftMotor = new CANSparkMax(Constants.LIFT_LEFT_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax rightMotor = new CANSparkMax(Constants.LIFT_RIGHT_MOTOR_ID, MotorType.kBrushless);
    
    public void lift()
    {
        leftMotor.set(1);
        rightMotor.set(1);
    }
}