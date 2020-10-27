package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Constants;

public class Intake extends TalonSRX {
    
    public Intake()
    {
        super(Constants.INTAKE_MOTOR_ID);
    }

    public void intake()
    {
        this.set(ControlMode.PercentOutput, 1);
    }

    public void intake(double power)
    {
        this.set(ControlMode.PercentOutput, power);
    }
}