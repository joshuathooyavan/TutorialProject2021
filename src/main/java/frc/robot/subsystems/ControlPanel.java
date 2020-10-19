package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Constants;

/**
 * A one motor system that can move in either direction.
 */
public class ControlPanel {
    
    private XboxController controller;
    private PWMVictorSPX panelMotor;

    private ColorSensorV3 colorSensor;
    private final ColorMatch colorMatcher = new ColorMatch();

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    

    /**
     * Constructs a ControlPanel object.
     * @param x the XboxController
     */
    public ControlPanel(XboxController x)
    {
        controller = x;
        panelMotor = new PWMVictorSPX(Constants.CONTROL_MOTOR_ID);
        colorSensor = new ColorSensorV3(Constants.COLOR_SENSOR_ID);
        
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }
    
    public void teleOp()
    { 
        // //#region ControlPanel
        // if(controller.getAButton() && !controller.getBButton()) 
        //     panelMotor.set(1);
        // else if(controller.getBButton() && !controller.getAButton())
        //     panelMotor.set(-1);
        // else
        //     panelMotor.set(0);
        // //#endregion
    }
    /**
     * Gets the color of this match
     */
    private void getGameColor()
    {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            switch (gameData.charAt(0))
            {
                case 'B' :
                    //Blue case code
                    break;
                case 'G' :
                    //Green case code
                    break;
                case 'R' :
                    //Red case code
                    break;
                case 'Y' :
                    //Yellow case code
                    break;
                default :
                    //This is corrupt data
                    break;
            }
        } else {
            //Code for no data received yet
        }
    }
}