package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;

public class Intake {
    XboxController controller;
    PWMVictorSPX intakeMotor;
    
    /** 
     * Constructs the intake motor along with the Xbox Controller.
     */
    public Intake(XboxController x) {
        controller = x;
        intakeMotor = new PWMVictorSPX(Constants.INTAKE_MOTOR_ID);
    }

    public void teleOp()    
    {
        //#region Intake
        if(controller.getXButton())
            intakeMotor.set(1);
        else if(controller.getYButton())
            intakeMotor.set (-1);
        else
            intakeMotor.set (0);
        //#endregion Intake

        //#region Hopper
        
        //#endregion Hopper
    }
}