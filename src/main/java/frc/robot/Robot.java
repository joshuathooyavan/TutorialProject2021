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

import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Robot extends TimedRobot {
  private static PWMVictorSPX lf = new PWMVictorSPX(Constants.LF_MOTOR_ID);
  private static PWMVictorSPX lb = new PWMVictorSPX(Constants.LB_MOTOR_ID);
  private static PWMVictorSPX rf = new PWMVictorSPX(Constants.RF_MOTOR_ID);
  private static PWMVictorSPX rb = new PWMVictorSPX(Constants.RB_MOTOR_ID);
  
  private static SpeedControllerGroup leftSide = new SpeedControllerGroup(lf, lb);
  private static SpeedControllerGroup rightSide = new SpeedControllerGroup(rf, rb);

  DifferentialDrive dt = new DifferentialDrive(leftSide, rightSide);
  XboxController driverController = new XboxController(0);

  @Override
  public void teleopPeriodic()
  {
    dt.arcadeDrive(driverController.getY(Hand.kLeft), driverController.getX(Hand.kRight));
  }
}
