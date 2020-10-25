package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public class Drivetrain
{   
    PWMVictorSPX lf = new PWMVictorSPX(Constants.LF_MOTOR_ID);
    PWMVictorSPX lb = new PWMVictorSPX(Constants.LB_MOTOR_ID);
    PWMVictorSPX rf = new PWMVictorSPX(Constants.RF_MOTOR_ID);
    PWMVictorSPX rb = new PWMVictorSPX(Constants.RB_MOTOR_ID);

    SpeedControllerGroup leftSide = new SpeedControllerGroup(lf, lb);
    SpeedControllerGroup rightSide = new SpeedControllerGroup(rf, rb);    
    
    DifferentialDrive dt = new DifferentialDrive(leftSide, rightSide);
    
    PIDController aimPID = new PIDController(1/27, 0, 0);   //  figure out D terms on both of these lol
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