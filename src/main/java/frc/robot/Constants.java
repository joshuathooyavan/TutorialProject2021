package frc.robot;

import edu.wpi.first.wpilibj.I2C.Port;

public class Constants
{
    // Drivetrain Motors (Intake side is front, L/R from robot's POV)
    public final static int LF_MOTOR_ID = 1;
    public final static int LB_MOTOR_ID = 2;
    public final static int RF_MOTOR_ID = 3;
    public final static int RB_MOTOR_ID = 4;

    // Subsystem Motor IDs
    public final static int FEEDER_MOTOR_ID = 5;
    public final static int CONTROL_MOTOR_ID = 6;
    public final static int INTAKE_MOTOR_ID = 7;
    public final static int LIFT_LEFT_MOTOR_ID = 8;
    public final static int LIFT_RIGHT_MOTOR_ID = 9;
    public final static int SHOOTER_LEFT_MOTOR_ID = 10;
    public final static int SHOOTER_RIGHT_MOTOR_ID = 11;
    
    // Sensor IDs
	public final static Port COLOR_SENSOR_ID = Port.kOnboard;
    
    public static final int[] DIGITAL_INPUT_IDS = { 0, 1, 2 };

    /** 
     * hahahahaha enum class heehahah 
     * 
     * https://www.programiz.com/java-programming/enums
     * https://www.programiz.com/java-programming/enum-constructor
     */
    public enum ShotRange
    {
        //  Format: kNameOfRange(requiredTy, requiredRPM)
        
        kInitLine(69420, 1250), //  10 foot shot
        kTrench(69420, 1600);   //  18 foot shot

        public double requiredTy, requiredRPM;

        private ShotRange(double reqTy, double reqRPM)
        {
            requiredTy = reqTy;
            requiredRPM = reqRPM;
        }
    }
    /*
        tbh this ShotRange enum class feels *kind of* stupid to have... it's a fair way to store the constants.
        Also, we should try that regression thing that we came up with on Friday morning before comp day...
            That would let us shoot from any given distance
    */
}