package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.Constants;

public class Shooter
{
    XboxController controller;
    PWMVictorSPX shooterMotor;
    public Shooter(XboxController x)
    {
        controller = x;
        shooterMotor = new PWMVictorSPX(Constants.SHOOTER_MOTOR_ID);
    }

    public void teleOp()
    {
        //#region shooter
        if(controller.getXButton())
            shooterMotor.set(1);
        else
            shooterMotor.set(0);
        //#endregion
    }
}