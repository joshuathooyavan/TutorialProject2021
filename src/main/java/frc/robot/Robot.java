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
*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class Robot extends TimedRobot {
  static CANSparkMax lf = new CANSparkMax(Constants.LF_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  static CANSparkMax lb = new CANSparkMax(Constants.LB_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  static CANSparkMax rf = new CANSparkMax(Constants.RF_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
  static CANSparkMax rb = new CANSparkMax(Constants.RB_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

  static SpeedControllerGroup leftSide = new SpeedControllerGroup(lf, lb);
  static SpeedControllerGroup rightSide = new SpeedControllerGroup(rf, rb);

  DifferentialDrive dt = new DifferentialDrive(leftSide, rightSide);
  XboxController driverController = new XboxController(0);

  RamseteController controller = new RamseteController();
  Trajectory trajectory;
  Pose2d currentRobotPose;
  DifferentialDriveKinematics kinematics;

  @Override
  public void autonomousPeriodic()
  {
    Trajectory.State goal = trajectory.sample(3.4); // sample the trajectory at 3.4 seconds from the beginning
    ChassisSpeeds adjustedSpeeds = controller.calculate(currentRobotPose, goal);
    
    DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(adjustedSpeeds);
    double left = wheelSpeeds.leftMetersPerSecond;
    double right = wheelSpeeds.rightMetersPerSecond;
  }

  @Override
  public void teleopPeriodic()
  {
    dt.arcadeDrive(driverController.getY(Hand.kLeft), driverController.getX(Hand.kRight));
  }
}
