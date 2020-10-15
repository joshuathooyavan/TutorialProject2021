package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants;

/**
 * A one motor subsystem with a fast mode and a slow mode.
 */
public class Feeder 
{
    XboxController controller;
    PWMVictorSPX feedMotor;

    /**
     * Constructs a new Feeder with one motor and a controller.
     * @param x the controller
     */
    public Feeder(XboxController x) 
    {
        controller = x;
        feedMotor = new PWMVictorSPX(Constants.FEEDER_MOTOR_ID); 
    }

    /**
     * Goes fast, slow, or not at all depending on the controller input
     */
    public void teleOp() 
    {
        if (controller.getBumperReleased(Hand.kRight))
        {
            feedMotor.set(1);
        } else if(controller.getBumperReleased(Hand.kLeft))
        {
            feedMotor.set(0.3);
        } else
        {
            feedMotor.set(0);
        }
    }
}