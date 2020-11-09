package frc.robot;

import edu.wpi.first.wpilibj.I2C.Port;

//  TODO: Unbogus EVERYTHING LMAO
public class Constants
{
	// Drivetrain Motors (Intake side is front, L/R from robot's POV)
	public static final int LF_MOTOR_ID = 4;    //  CANSparkMax
	public static final int LB_MOTOR_ID = 1;    //  CANSparkMax
	public static final int RF_MOTOR_ID = 8;    //  CANSparkMax
	public static final int RB_MOTOR_ID = 7;    //  CANSparkMax

	// Subsystem Motor IDs
	public static final int FEEDER_MOTOR_ID = 12;    //  TalonSRX
	public static final int CONTROL_MOTOR_ID = 69_420;    //  TalonSRX
	public static final int INTAKE_MOTOR_ID = 3;    //  TalonSRX
	public static final int LIFT_LEFT_MOTOR_ID = 69_420;    //  CANSparkMax
	public static final int LIFT_RIGHT_MOTOR_ID = 69_420;    //  CANSparkMax
	public static final int SHOOTER_LEFT_MOTOR_ID = 2;    //  CANSparkMax
	public static final int SHOOTER_RIGHT_MOTOR_ID = 6;    //  CANSparkMax
	public static final int HOPPER_LEFT_ID = 5; //  TalonSRX
	public static final int HOPPER_RIGHT_ID = 4;    //  TalonSRX
	
	
	// Sensor IDs
	public static final Port COLOR_SENSOR_ID = Port.kOnboard;
	
	public static final int[] FEEDER_SENSOR_IDS = { 0, 1, 2 };
	public static final int LIFT_LIMSWITCH_ID = 69_420;

	// Constant encoder... thing...
	public static double kEncoderDistancePerPulse = 69_420;
}