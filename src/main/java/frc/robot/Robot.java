/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Names:
/*
  Jacob Lewis
  Zane Badgett
  Joshua P
  Arrio Gonsalves
  Zayaan Rahman
  Neeka Lin
  Gabriel Rivera
  Fernando Tovar
  Matthew Metta


  i'm just typing out other softrical members' names:
  Shaurya
  Surya
  Harshal
*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

import frc.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {
    
  Timer autoTimer = new Timer();

  Drivetrain drivetrain = new Drivetrain();

  //this 0.4 in the next line is the drivetrain wheelbase width
  DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(0.4);  //  ***needs a look at... 
  //robot characterization much? to add on the [...]Kinematics.Constraints stuff

  Trajectory trajectory;
  RamseteController controller = new RamseteController();

  @Override
  public void robotInit()
  {
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve("PathWeaver/Paths/Red Init 1");
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: PathWeaver/Paths/Red Init 1", ex.getStackTrace());
    }
  }
  
  @Override
  public void robotPeriodic()
  {
    drivetrain.updateOdometry();
  }

  @Override
  public void autonomousInit()
  {
    autoTimer.start();
  }

  @Override
  public void autonomousPeriodic()
  {
    Trajectory.State goal = trajectory.sample(autoTimer.get());
    ChassisSpeeds adjustedSpeeds = controller.calculate(drivetrain.getPose(), goal);  //  should be the *current* robot pose
    
    DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(adjustedSpeeds);
    double leftSpeed = wheelSpeeds.leftMetersPerSecond;
    double rightSpeed = wheelSpeeds.rightMetersPerSecond;

    drivetrain.driveBySpeeds(leftSpeed, rightSpeed); // *** should use PIDstuff to control by speed instead... CANSparkMax much?
  }
}
