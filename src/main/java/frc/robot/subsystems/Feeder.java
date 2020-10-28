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
    
    DigitalInput[] feederSensors;

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
                intakeAndHop();
                break;
            case 2: //  The third will now be stored in the feeder - as will the rest
                intakeAndHop();
                while(feederSensors[0].get() != true)
                    feederMotor.set(PercentOutput, 1);
                break;
            case 3:
                intakeAndHop();
                while(feederSensors[1].get() != true)
                    feederMotor.set(PercentOutput, 1);
                break;
            case 4:
                intakeAndHop();
                while(feederSensors[2].get() != true)
                    feederMotor.set(PercentOutput, 1);
                break;
            case 5: //  Don't take any more balls in!
            default:    //  Something must have gone wrong... tell shuffleboard
                break;
        }

        numBalls++;
    }
    
    private void intakeAndHop()
    {        
        intake.set(PercentOutput, 1);   //  TODO: tune intake speed
        hopper.set(PercentOutput, 1);   //  TODO: tune hopper speed
    }

    public void feedForShooting()
    {
        feederMotor.set(PercentOutput, 0.7);  //  TODO: tune feed speeds
        
        numBalls--;
        //  TODO: Use the sensors to check if we've shot all de balls
    }

    public void reverse()
    {
        //  TODO: implement reverse
    }

}