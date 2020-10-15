package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;

/**
 * A one motor system that can move in either direction.
 */
public class ControlPanel {
    
    private XboxController controller;
    private PWMVictorSPX panelMotor;

    /**
     * Constructs a ControlPanel object.
     * @param x the XboxController
     */
    public ControlPanel(XboxController x)
    {
        controller = x;
        panelMotor = new PWMVictorSPX(Constants.CONTROL_MOTOR_ID);
    }

    public void teleOp()
    {        
        //#region ControlPanel
        if(controller.getAButton() && !controller.getBButton()) 
            panelMotor.set(1);
        else if(controller.getBButton() && !controller.getAButton())
            panelMotor.set(-1);
        else
            panelMotor.set(0);
        //#endregion
    }
}