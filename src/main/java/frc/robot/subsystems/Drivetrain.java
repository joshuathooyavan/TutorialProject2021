package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.Constants.ShotRange;

public class Drivetrain
{   
    CANSparkMax lf = new CANSparkMax(Constants.LF_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax lb = new CANSparkMax(Constants.LB_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax rf = new CANSparkMax(Constants.RF_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax rb = new CANSparkMax(Constants.RB_MOTOR_ID, MotorType.kBrushless);

    SpeedControllerGroup leftSide = new SpeedControllerGroup(lf, lb);
    SpeedControllerGroup rightSide = new SpeedControllerGroup(rf, rb);    
    
    DifferentialDrive dt = new DifferentialDrive(leftSide, rightSide);
    
    PIDController aimPID = new PIDController(1/27, 0, 0);   //  TUNE ME
    PIDController distancePID = new PIDController(1/20.5, 0, 0);

    Limelight limelight;

    public Drivetrain(Limelight l)
    {
        limelight = l;
    }

    public void arcadeDrive(double xSpeed, double zRotation)
    {
        dt.arcadeDrive(xSpeed, zRotation);
    }

    public void aim(ShotRange range)
    {
        dt.arcadeDrive(
            distancePID.calculate(limelight.getTy(), range.requiredTy),
            aimPID.calculate(limelight.getTx(), 0)
            );
    }
}