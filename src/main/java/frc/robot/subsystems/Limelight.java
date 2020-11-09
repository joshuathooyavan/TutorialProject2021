package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
	
	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	
	NetworkTableEntry tx = table.getEntry("tx");
	NetworkTableEntry ty = table.getEntry("ty");
	NetworkTableEntry ta = table.getEntry("ta");
	NetworkTableEntry tv = table.getEntry("tv");

	public static final int kOff = 1, kOn = 2, kBlink = 3;  // i wish java had normal enums with values mane

	public boolean getTv(){
		return tv.getBoolean(false);
	}

	public double getTx(){
		return tx.getDouble(0.0);
	}

	public double getTy() {
		return ty.getDouble(0.0);
	}

	public double getTa(){
		return ta.getDouble(0.0);
	}
	
	/**
	 * Use Limelight.kOff/On/Blink
	 * @param mode
	 */
	public void setLedMode(int mode)
	{
		table.getEntry("ledMode").setNumber(mode);
	}    

	public void shuffleboardUpdate() {
		SmartDashboard.putNumber("LimelightX", getTx());
		SmartDashboard.putNumber("LimelightY", getTy());
		SmartDashboard.putNumber("LimelightArea", getTa());
		SmartDashboard.putBoolean("LimelightV", getTv());
		
	}
}