package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;

public class ControlPanel
{    
    private TalonSRX panelMotor = new TalonSRX(Constants.CONTROL_MOTOR_ID);

    private ColorSensorV3 colorSensor = new ColorSensorV3(Constants.COLOR_SENSOR_ID);;
    private final ColorMatch colorMatcher = new ColorMatch();

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    
    private Timer timer = new Timer();
    private boolean rotationControlHasAlreadyStarted;
    
    public ControlPanel()
    {
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    /**
     * wheel go spin (but only 3-5 times
     */
    public void rotationControl()
    {
        double startTime = 0.0;
        if(!rotationControlHasAlreadyStarted) {
            timer.start();
            panelMotor.set(ControlMode.PercentOutput, 0.5);
            startTime = timer.get();
            rotationControlHasAlreadyStarted = true;
        }
        
        //"timeInterval" would be some amount we experiment for
        if(timer.get() - startTime > 20.0 /*Needs to be tested for later*/ ) {
            timer.stop();
            panelMotor.set(ControlMode.PercentOutput, 0);
            rotationControlHasAlreadyStarted = false;
        }
    }
    
    /**
     * wheel spins, but not that much
     */
    public void positionControl()
    {
        if(colorMatcher.matchClosestColor(colorSensor.getColor()).color != getGameColor())
            panelMotor.set(ControlMode.PercentOutput, 1);
        else
            panelMotor.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Gets the color of this match
     */
    public Color getGameColor()
    {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            switch (gameData.charAt(0) )
            {
                case 'B' :
                    return kBlueTarget;
                case 'G' :
                    return kGreenTarget;
                case 'R' :
                    return kRedTarget;
                case 'Y' :
                    return kYellowTarget;
                default :
                    //This is corrupt data - Tell Shuffleboard there was an error and no color is known
                    return null;
            }
        }
        else
            ;   //  same here, tell shuffleboard
        
        return null;
    }
}