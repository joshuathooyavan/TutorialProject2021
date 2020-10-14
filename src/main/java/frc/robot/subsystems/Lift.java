package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
//imports here
import frc.robot.Constants;

public class Lift 
{
    XboxController controller;
    PWMVictorSPX liftMotor;

    public Lift(XboxController x)
    {
        controller = x;
        liftMotor = new PWMVictorSPX(Constants.LIFT_MOTOR_ID); 
    }

    public void teleOp()
    {
        if( controller.getXButton() ) 
        {
            liftMotor.set(1);
        }
        else if( controller.getYButton() ) 
        {
            liftMotor.set(-1);
        }
        else 
        {
            liftMotor.set(0);
        }
    }

}