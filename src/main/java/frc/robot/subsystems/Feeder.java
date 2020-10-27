package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
public class Feeder
{
    private TalonSRX feederMotor = new TalonSRX(Constants.FEEDER_MOTOR_ID);

    public void feed()
    {
        feederMotor.set(ControlMode.PercentOutput, 1);
    }
}