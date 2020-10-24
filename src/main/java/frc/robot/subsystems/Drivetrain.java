package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import frc.robot.*;

public class Drivetrain
{
  // The motors on the left side of the drive.
  private final SpeedControllerGroup m_leftMotors =
      new SpeedControllerGroup(new PWMVictorSPX(Constants.LF_MOTOR_ID),
                               new PWMVictorSPX(Constants.LB_MOTOR_ID));

  // The motors on the right side of the drive.
  private final SpeedControllerGroup m_rightMotors =
      new SpeedControllerGroup(new PWMVictorSPX(Constants.RF_MOTOR_ID),
                               new PWMVictorSPX(Constants.RB_MOTOR_ID));

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  // The left-side drive encoder
  private final Encoder m_leftEncoder =
      new Encoder(Constants.kLeftEncoderPorts[0], Constants.kLeftEncoderPorts[1],
                  Constants.kLeftEncoderReversed);

  // The right-side drive encoder
  private final Encoder m_rightEncoder =
      new Encoder(Constants.kRightEncoderPorts[0], Constants.kRightEncoderPorts[1],
                  Constants.kRightEncoderReversed);

  // The gyro sensor
  public final Gyro m_gyro = new ADXRS450_Gyro();

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  PIDController leftPID = new PIDController(1, 0, 0);   //  https://frc-pdr.readthedocs.io/en/latest/control/pid_control.html#tuning-methods oooh!
  PIDController rightPID = new PIDController(1, 0, 0);

  /**
   * Creates a new DriveSubsystem.
   */
  public Drivetrain() {
    m_leftEncoder.setDistancePerPulse(Constants.kEncoderDistancePerPulse);
    m_rightEncoder.setDistancePerPulse(Constants.kEncoderDistancePerPulse);

    m_leftEncoder.reset();
    m_rightEncoder.reset();
    
    m_odometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(m_gyro.getAngle())));
  }

  /**
   * call me during periodic pls ;)
   */
  public void updateOdometry()
  {
    // Update the odometry in the periodic block
    m_odometry.update(new Rotation2d(Math.toDegrees(m_gyro.getAngle())), m_leftEncoder.getDistance(),
                      m_rightEncoder.getDistance());
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
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getRate(), m_rightEncoder.getRate());
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

  public void driveBySpeeds(double leftSpeed, double rightSpeed)
  {
    this.tankDriveVolts(
        leftPID.calculate(m_leftEncoder.getRate(), leftSpeed), 
        rightPID.calculate(m_rightEncoder.getRate(), rightSpeed)
        );  //  Maybe .getDistance works if this doesn't??? Test and find out
  }

  /**
   * Sets the max output of the drive.  Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getAngle()-180;
  }
}