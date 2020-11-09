package frc.robot.subsystems.drivetrains;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

import java.io.IOException;
import java.nio.file.Path;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

// import frc.robot.Constants;
import frc.robot.subsystems.*;

/**
 * going to try to write swerve drive??
 * okay got a lot of code from wpilib docs
 */
public class Swerve
{
	//#region fields
	Gyro gyro = new ADXRS450_Gyro();
	SwerveModule[] modules = new SwerveModule[4];

	// Creating my kinematics object using the module locations relative to the robot center.
	SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
		new Translation2d(0.25, 0.25),  // LF
		new Translation2d(0.25, -0.25), // RF
		new Translation2d(-0.25, 0.25), // LB
		new Translation2d(-0.25, -0.25) // RB
	);    

	// Creating my odometry object from the kinematics object. Here,
	// our starting pose is 5 meters along the long end of the field and in the
	// center of the field along the short end, facing forward.
	SwerveDriveOdometry odometry = new SwerveDriveOdometry(kinematics,
	new Rotation2d(Math.toDegrees(gyro.getAngle())), new Pose2d(5.0, 13.5, new Rotation2d()));
	
	Pose2d pose;

	HolonomicDriveController controller;
	Trajectory trajectory;

	Limelight limelight;
	PIDController aimXPID = new PIDController(1/27, 0, 0);   // ToDO:  TUNE ME and check units bc m/s
	PIDController aimRotPID = new PIDController(1/27, 0, 0);   // ToDO:  TUNE ME
	PIDController distPID = new PIDController(1/20, 0, 0);   // ToDO:  TUNE ME

	//#endregion

	public Swerve()
	{
		gyro.reset();

		for (int i = 0; i < modules.length; i++)
		{
			// modules[i] = new SwerveModule(Constants.SWERVE_MODULES[i].DRIVE_ID, Constants.SWERVE_MODULES[i].ANGLE_ID);
			modules[i] = new SwerveModule(0, 0);
		}

		/*
		* --- WARNING!!! ---
		* The following code means that you HAVE to set up the DS in the SAME spot the robot is placed, 
		* because the DS position determines the chosen trajectory.
		* Seems normal but just specifying... When testing, just choose it in the DS app.
		*/
		String path = Integer.toString(DriverStation.getInstance().getLocation());

		// OPENING TRAJECTORY
		try {
			Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve("PathWeaver/output/" + path);
			trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
		} catch (IOException ex) {
			DriverStation.reportError("Unable to open trajectory: " + path, ex.getStackTrace());
		}
	}

	/**
	 * use w joysticks
	 * @param x -1 to 1
	 * @param y -1 to 1
	 * @param rot -1 to 1
	 */
	public void driveByPower(double x, double y, double rot)
	{
		drive(x/4.5, y/4.5, rot/2.1); //  ToDO: find max linear speed and angular vel.
	}

	/**
	 * @param x m/s
	 * @param y m/s
	 * @param rot angular vel, rad/s
	 */
	public void drive(double x, double y, double rot)
	{
		SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(new ChassisSpeeds(x, y, rot));

		for (int i = 0; i < 4; i++)
			modules[i].set(moduleStates[i]);
	}

	public void followTrajectory(double time)
	{
		Trajectory.State desiredState = trajectory.sample(time);

		ChassisSpeeds targetChassisSpeeds = controller.calculate(pose, desiredState,
			trajectory.getStates().get(trajectory.getStates().size() - 1)   //  these two lines here
			.poseMeters.getRotation());                                     //  keep the swerve rotation the same lol
			//  pulled that from constructor of SwerveControllerCommand lol
		
		var targetModuleStates = kinematics.toSwerveModuleStates(targetChassisSpeeds);

		for (int i = 0; i < 4; i++)
			modules[i].set(targetModuleStates[i]);
	}

	public void aim()
	{
		drive(  //  make sure to consider units... this is not based on power            
			aimXPID.calculate(limelight.getTx(), 0),
			distPID.calculate(limelight.getTy(), 0),
			aimRotPID.calculate(limelight.getTx(), 0)
		);
	}

	/**
	 * call me during periodic pls ;)
	 */
	public void updateOdometry()
	{
		// Get my gyro angle. We are negating the value because gyros return positive
		// values as the robot turns clockwise. This is not standard convention that is
		// used by the WPILib classes.
		var gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());

		// Update the pose
		pose = odometry.update(gyroAngle, modules[0].getState(), modules[1].getState(),
		modules[2].getState(), modules[3].getState());
	}


	public class SwerveModule
	{
		CANSparkMax driveMotor, angleMotor;
		CANPIDController drivePID, anglePID;

		public SwerveModule(int driveId, int angleId)
		{
			driveMotor = new CANSparkMax(driveId, kBrushless);
			angleMotor = new CANSparkMax(angleId, kBrushless);
			
			drivePID = driveMotor.getPIDController();            
			anglePID = angleMotor.getPIDController();
		}

		/**
		 * Sets swerve module's speeds.
		 */
		public void set(SwerveModuleState state)
		{
			drivePID.setReference(state.speedMetersPerSecond, ControlType.kVelocity);   //  ToDO: set conversion factors on client?? should be m/s
			anglePID.setReference(state.angle.getDegrees(), ControlType.kPosition);    //  note how i made it a fake todo lol
		}

		public SwerveModuleState getState()
		{
			return new SwerveModuleState(
				driveMotor.getEncoder().getVelocity(), 
				new Rotation2d(angleMotor.getEncoder().getPosition())
			);
		}
	}
}