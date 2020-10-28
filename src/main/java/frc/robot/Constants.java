package frc.robot;

import edu.wpi.first.wpilibj.I2C.Port;

//  TODO: Unbogus EVERYTHING LMAO
public class Constants
{
    // Drivetrain Motors (Intake side is front, L/R from robot's POV)
    public static final int LF_MOTOR_ID = 1;    //  CANSparkMax
    public static final int LB_MOTOR_ID = 2;    //  CANSparkMax
    public static final int RF_MOTOR_ID = 3;    //  CANSparkMax
    public static final int RB_MOTOR_ID = 4;    //  CANSparkMax

    // Subsystem Motor IDs
    public static final int FEEDER_MOTOR_ID = 5;    //  TalonSRX
    public static final int CONTROL_MOTOR_ID = 6;    //  TalonSRX
    public static final int INTAKE_MOTOR_ID = 7;    //  TalonSRX
    public static final int LIFT_LEFT_MOTOR_ID = 8;    //  CANSparkMax
    public static final int LIFT_RIGHT_MOTOR_ID = 9;    //  CANSparkMax
    public static final int SHOOTER_LEFT_MOTOR_ID = 10;    //  CANSparkMax
    public static final int SHOOTER_RIGHT_MOTOR_ID = 11;    //  CANSparkMax
	public static final int HOPPER_LEFT_ID = 0;
	public static final int HOPPER_RIGHT_ID = 0;
    
    
    // Sensor IDs
	public static final Port COLOR_SENSOR_ID = Port.kOnboard;
    
    public static final int[] FEEDER_SENSOR_IDS = { 0, 1, 2 };

    // Constant encoder... thing...
    public static double kEncoderDistancePerPulse = 69_420;

    /** 
     * hahahahaha enum class heehahah
     * 
     * https://www.programiz.com/java-programming/enums
     * https://www.programiz.com/java-programming/enum-constructor
     */
    public enum ShotRange
    {
        //  Format: kNameOfRange(requiredTy, requiredRPM)
        
        kInitLine(69420, 1250), //  10 foot shot TODO: tune tY and RPMs
        kTrench(69420, 1600);   //  18 foot shot

        public double requiredTy, requiredRPM;

        private ShotRange(double reqTy, double reqRPM)
        {
            requiredTy = reqTy;
            requiredRPM = reqRPM;
        }
    }
    /* TODO: shoot aim regression
        Also, we should try that regression thing that we came up with on Friday morning before comp day...
            That would let us shoot from any given distance
    */
}