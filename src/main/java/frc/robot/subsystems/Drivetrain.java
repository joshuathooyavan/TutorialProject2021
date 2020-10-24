package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import frc.robot.Constants;

/**
 * Based off of DriveSubsystem example in RamseteCommand
 */
public class Drivetrain
{
    CANSparkMax lf = new CANSparkMax(Constants.LF_MOTOR_ID, MotorType.kBrushless);
    CANSparkMax rf = new CANSparkMax(Constants.RF_MOTOR_ID, MotorType.kBrushless);
    
    CANEncoder m_leftEncoder = new CANEncoder(lf);
    CANEncoder m_rightEncoder = new CANEncoder(rf);

    private final SpeedControllerGroup m_leftMotors =
        new SpeedControllerGroup(lf, new CANSparkMax(Constants.LB_MOTOR_ID, MotorType.kBrushless));

    private final SpeedControllerGroup m_rightMotors =
        new SpeedControllerGroup(rf, new CANSparkMax(Constants.RB_MOTOR_ID, MotorType.kBrushless));                               

    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);
    
    public final Gyro m_gyro = new ADXRS450_Gyro();

    // Odometry class for tracking robot pose
    private final DifferentialDriveOdometry m_odometry;

    PIDController leftPID = new PIDController(1, 0, 0);   //  https://frc-pdr.readthedocs.io/en/latest/control/pid_control.html#tuning-methods oooh!
    PIDController rightPID = new PIDController(1, 0, 0);

    /**
     * Creates a new DriveSubsystem.
     */
    public Drivetrain() {
        m_leftEncoder.setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
        m_rightEncoder.setPositionConversionFactor(Constants.kEncoderDistancePerPulse);

        m_leftEncoder.setVelocityConversionFactor(Constants.kEncoderDistancePerPulse);
        m_rightEncoder.setVelocityConversionFactor(Constants.kEncoderDistancePerPulse);

        m_odometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(m_gyro.getAngle())));
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

    /**
     * Drives the robot using arcade controls.
     *
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation
     */
    public void arcadeDrive(double fwd, double rot) {
        m_drive.arcadeDrive(fwd, rot);
    }

    /**
     * Controls the left and right sides of the drive directly with voltages.
     *
     * @param leftVolts  the commanded left output
     * @param rightVolts the commanded right output
     */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        m_leftMotors.setVoltage(leftVolts);
        m_rightMotors.setVoltage(-rightVolts);
        m_drive.feed();
    }

    /**
     * Sets the drivetrain to a given speed.
     * @param leftSpeed speed of left side
     * @param rightSpeed speed of right side, duh lmao
     */
    public void driveBySpeeds(double leftSpeed, double rightSpeed)
    {
    this.tankDriveVolts(
        leftPID.calculate(m_leftEncoder.getVelocity(), leftSpeed), 
        rightPID.calculate(m_rightEncoder.getVelocity(), rightSpeed)
        );  //  Maybe .getDistance works if this doesn't??? Test and find out
    }
}