package frc.robot;

public class Constants
{
    public final static int LF_MOTOR_ID = 1;
    public final static int LB_MOTOR_ID = 2;
    public final static int RF_MOTOR_ID = 3;
    public final static int RB_MOTOR_ID = 4;
    
    public static final int[] kLeftEncoderPorts = new int[]{0, 1};
	public static final boolean kLeftEncoderReversed = false;
	public static final int[] kRightEncoderPorts = new int[]{2, 3};
	public static final boolean kRightEncoderReversed = true;
	public static double kEncoderDistancePerPulse = 69_420;
}