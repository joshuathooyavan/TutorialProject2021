package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;

import frc.robot.Constants;
import frc.robot.Constants.ShotRange;


/**
 * Based off of DriveSubsystem example in RamseteCommand
 */
public class Drivetrain
{   
    CANSparkMax lf = new CANSparkMax(Constants.LF_MOTOR_ID, kBrushless);
    CANSparkMax lb = new CANSparkMax(Constants.LB_MOTOR_ID, kBrushless);
    CANSparkMax rf = new CANSparkMax(Constants.RF_MOTOR_ID, kBrushless);
    CANSparkMax rb = new CANSparkMax(Constants.RB_MOTOR_ID, kBrushless);

    CANEncoder m_leftEncoder = lf.getEncoder();
    CANEncoder m_rightEncoder = rf.getEncoder();

    //  https://frc-pdr.readthedocs.io/en/latest/control/pid_control.html#tuning-methods oooh!
    // TODO: Use the SPARK MAX Client application to set PID coeffs that will persist!
    CANPIDController leftPID = lf.getPIDController();
    CANPIDController rightPID = rf.getPIDController();

    SpeedControllerGroup leftSide = new SpeedControllerGroup(lf, lb);
    SpeedControllerGroup rightSide = new SpeedControllerGroup(rf, rb);    
    
    DifferentialDrive dt = new DifferentialDrive(leftSide, rightSide);
    
    PIDController aimPID = new PIDController(1/27, 0, 0);   // TODO:  TUNE ME
    PIDController distancePID = new PIDController(1/20.5, 0, 0);

    Limelight limelight;
    
    Gyro m_gyro = new ADXRS450_Gyro();

    // Odometry class for tracking robot pose
    DifferentialDriveOdometry m_odometry;

    public Drivetrain(Limelight l)
    {
        limelight = l;

        m_leftEncoder.setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
        m_rightEncoder.setPositionConversionFactor(Constants.kEncoderDistancePerPulse);

        m_leftEncoder.setVelocityConversionFactor(Constants.kEncoderDistancePerPulse);
        m_rightEncoder.setVelocityConversionFactor(Constants.kEncoderDistancePerPulse);

        // m_rightEncoder.setInverted(true);

        m_odometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(m_gyro.getAngle())));
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

    /**
     * Sets the drivetrain to a given speed.
     * @param leftSpeed speed of left side
     * @param rightSpeed speed of right side, duh lmao
     */
    public void driveBySpeeds(double leftSpeed, double rightSpeed)
    {      
        leftPID.setReference(leftSpeed, ControlType.kVelocity);
        rightPID.setReference(rightSpeed, ControlType.kVelocity);
    }

    /**
     * call me during periodic pls ;)
     */
    public void updateOdometry()
    {
        // Update the odometry in the periodic block
        m_odometry.update(new Rotation2d(Math.toDegrees(m_gyro.getAngle())), m_leftEncoder.getPosition(),  //  need to make sure getPosition is same as getDistance
                            m_rightEncoder.getPosition());
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }
}