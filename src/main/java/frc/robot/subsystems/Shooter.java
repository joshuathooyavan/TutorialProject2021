package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

public class Shooter
{
	CANSparkMax leftShooterMotor = new CANSparkMax(Constants.SHOOTER_LEFT_MOTOR_ID, MotorType.kBrushless);
	CANSparkMax rightShooterMotor = new CANSparkMax(Constants.SHOOTER_RIGHT_MOTOR_ID, MotorType.kBrushless);
	
	CANPIDController leftPIDController = leftShooterMotor.getPIDController();   //  TODO: tune in client

	Limelight limelight;

	public Shooter(Limelight limelight)
	{
		rightShooterMotor.follow(leftShooterMotor, true);
		this.limelight = limelight;
	}

	public void shoot()
	{
		leftPIDController.setReference(tYToRPM(), ControlType.kVelocity);
	}

	private double tYToRPM()
	{
		/*
		  https://docs.limelightvision.io/en/latest/cs_estimating_distance.html
		  
		  d = (h2-h1) / tan(a1+a2)
		  
		   "the height of the target (h2) is known because it is a property of the field. 
		   The height of your camera above the floor (h1) is known and its mounting angle is known (a1). 
		   The limelight (or your vision system) can tell you the y angle to the target (a2)."

		  say our regression eq. from distance to RPM is v = sqrt(d)
		*/

		// Units in meters!
		double d = (25 - 69_420) / Math.tan(0 + limelight.getTy()); //  TODO: tune: find LL height on bot
		return Math.sqrt(d);
	}
}