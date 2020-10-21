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
    
    public Color gameColor = null;
     
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
        //#region ControlPanel
        if(controller.getAButton() && !controller.getBButton())
            rotationControl();
        else if(controller.getBButton() && !controller.getAButton())
            positionControl();
        //#endregion
    }

    private void rotationControl()
    {
        
    }
    
    private void positionControl()
    {
        ColorMatchResult matchResult = colorMatcher.matchClosestColor(colorSensor.getColor());

        while(colorSensor.getColor() != matchResult.color)  //  Josh realizes after meeting - didnt account for offset
            panelMotor.set(1);

        if (colorSensor.getColor() == gameColor)
            panelMotor.set(0);
    }

    /**
     * Gets the color of this match
     */
    public void getGameColor()
    {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            switch (gameData.charAt(0) )
            {
                case 'B' :
                    gameColor = kBlueTarget;
                    break;
                case 'G' :
                    gameColor = kGreenTarget;
                    break;
                case 'R' :
                    gameColor = kRedTarget;
                    break;
                case 'Y' :
                    gameColor = kYellowTarget;
                    break;
                default :
                    //This is corrupt data - Tell Shuffleboard there was an error and no color is known
                    break;
            }
        }
    }
}