package frc.robot.subsystems;

import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import java.util.ArrayList;

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
    
    private final ArrayList<Color> colors = new ArrayList<>();
    
    public Color gameColor = null;

    private Timer timer = new Timer();
    
    public ControlPanel()
    {
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
        colors.add(kRedTarget);
        colors.add(kGreenTarget);
        colors.add(kBlueTarget);
        colors.add(kYellowTarget);
    }

    /**
     * wheel go spin (but only 3-5 times
     */
    public void rotationControl()
    {
        timer.start();
        
        while(timer.get() < 20.0)   //  TODO: Tune time to rotate ctrlpanel
            panelMotor.set(PercentOutput, 1);

        timer.stop();
        panelMotor.set(PercentOutput, 0);
    }
    
    /**
     * wheel spins, but not that much
     */
    public void positionControl()
    {
        //this is the color the robot sees - which is offset from what the field sees
        Color color = colors.get((colors.indexOf(gameColor) + 2)  % colors.size()); //the modulo is so that if it's above the length, it loops back to the beginning of the array
        
        if(colorMatcher.matchClosestColor(colorSensor.getColor()).color != color)
            panelMotor.set(PercentOutput, 1);   //  TODO: Tune ctrlpanel speeds
        else
            panelMotor.set(PercentOutput, 0);
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