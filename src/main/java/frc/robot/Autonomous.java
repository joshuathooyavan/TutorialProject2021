package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

import frc.robot.subsystems.*;

public class Autonomous
{  
    Timer autoTimer = new Timer();
    
    Drivetrain drivetrain;
    Shooter shooter;    
    Feeder feeder;

    // this 0.5 in the next line is the drivetrain wheelbase width
    DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(0.5); // ***needs a look at...

    Trajectory trajectory;
    RamseteController controller = new RamseteController();

    public Autonomous(Drivetrain drivetrain, Shooter shooter, Feeder feeder)
    {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.feeder = feeder;
    }

    public void robotInit()
    {        
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
    
    public void init()
    {
        autoTimer.start();
        feeder.setIntake(true);
    }

    public void periodic()
    {
        if (autoTimer.get() < 4) AimShoot();
        else if (autoTimer.get() < 11) Traj(autoTimer.get() - 4);
        else if (autoTimer.get() < 15) AimShoot();
    }

    private void AimShoot()
    {
        drivetrain.aim();
        shooter.shoot();
        feeder.feedForShooting();
    }

    private void Traj(double time)
    {
        Trajectory.State goal = trajectory.sample(time);
        ChassisSpeeds adjustedSpeeds = controller.calculate(drivetrain.getPose(), goal);  //  should be the *current* robot pose

        DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(adjustedSpeeds);
        double leftSpeed = wheelSpeeds.leftMetersPerSecond;
        double rightSpeed = wheelSpeeds.rightMetersPerSecond;

        drivetrain.driveBySpeeds(leftSpeed, rightSpeed);
    }
}