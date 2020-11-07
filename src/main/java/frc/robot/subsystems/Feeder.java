package frc.robot.subsystems;

import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.Constants;

public class Feeder
{
    TalonSRX intake = new TalonSRX(Constants.INTAKE_MOTOR_ID);

    TalonSRX hopper = new TalonSRX(Constants.HOPPER_LEFT_ID);
    TalonSRX hopperRside = new TalonSRX(Constants.HOPPER_RIGHT_ID);

    TalonSRX feederMotor = new TalonSRX(Constants.FEEDER_MOTOR_ID);
    
    DigitalInput[] feederSensors = new DigitalInput[4];

    int numBalls = 0;

    public Feeder()
    {
        for(int i = 0; i < Constants.FEEDER_SENSOR_IDS.length; i++)
            feederSensors[i] = new DigitalInput(Constants.FEEDER_SENSOR_IDS[i]);

        hopperRside.setInverted(true);
        hopperRside.follow(hopper);
    }

    public void addBall()   //  TODO: actually autoindex TODO: set the controls properly
    {
        /*
            --- Feeder diagram for reference ---
            
            0 - ball
            \ - intake
            / - hopper
            |_ - feeder
            . - sensor is present here (3 total)

            .|0   \
            .|0   /\
            .|0  / 0\
             |__0__  \
                      \
        */

        switch(numBalls)
        {
            case 0: //  The first ball gets stored in the intake area
                intake.set(PercentOutput, 1);
                break;
            case 1: //  The second is stored in the hopper - note that every ball's position gets shifted
                setIntakeAndHop();
                break;
            case 2: //  The third will now be stored in the feeder - as will the rest
                setIntakeAndHop();
                if(feederSensors[0].get() != true)
                    feederMotor.set(PercentOutput, 1);
                else 
                    feederMotor.set(PercentOutput, 0);
                break;
            case 3:
                setIntakeAndHop();
                if(feederSensors[1].get() != true)
                    feederMotor.set(PercentOutput, 1);
                else 
                    feederMotor.set(PercentOutput, 0);
                break;
            case 4:
                setIntakeAndHop();
                if(feederSensors[2].get() != true)
                    feederMotor.set(PercentOutput, 1);
                else 
                    feederMotor.set(PercentOutput, 0);
                break;
            case 5: //  Don't take any more balls in!
                feederMotor.set(PercentOutput, 0);
            default:    //  Something must have gone wrong... tell shuffleboard
                break;
        }

        numBalls++;
    }
    
    public void setIntakeAndHop()
    {
        setIntakeAndHop(1);   //  TODO: tune intake and hopper speeds
    }

    public void setIntakeAndHop(double value)
    {
        intake.set(PercentOutput, value);
        hopper.set(PercentOutput, value);
    }

    /**
     * has to be called periodically lol
     */
    public void feedForShooting()
    {
        feederMotor.set(PercentOutput, 0.7);  //  TODO: tune feed speeds
        
        numBalls--;
        //  TODO: Use the sensors to check if we've shot all de balls
    }
    
    public void setIntake(boolean value)
    {
        if(value)
            intake.set(PercentOutput, 1);
        else
            intake.set(PercentOutput, 0);
    }
}