package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

import frc.robot.Constants;

public class Lift
{
    CANSparkMax leftMotor = new CANSparkMax(Constants.LIFT_LEFT_MOTOR_ID, kBrushless);
    CANSparkMax rightMotor = new CANSparkMax(Constants.LIFT_RIGHT_MOTOR_ID, kBrushless);
    
    CANPIDController pid = leftMotor.getPIDController(); // TODO: Tune

    public Lift()
    {
        rightMotor.follow(leftMotor, true);
    }

    public void lift()
    {
        pid.setReference(69_420, ControlType.kPosition);
        //  TODO: Tune to find how many ticks or whatever measurement it needs (Could use conversion again)
    }


    public void down()
    {
        leftMotor.set(-.5);
    }
}