/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants.ShotRange;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot
{
  XboxController driverController = new XboxController(0);
  XboxController operatorController = new XboxController(1);  
  
  Lift lift = new Lift();
  Intake intake = new Intake();
  Shooter shooter = new Shooter();
  Feeder feeder = new Feeder();
  ControlPanel controlPanel = new ControlPanel();
  Limelight limelight = new Limelight();
  Drivetrain drivetrain = new Drivetrain(limelight);

  Timer autoTimer = new Timer();

  @Override
  public void autonomousInit()
  {
    autoTimer.start();
  }

  @Override
  public void autonomousPeriodic()
  {
    double time = autoTimer.get();
    
    if (0 < time && time < 10) 
    {
      drivetrain.aim(ShotRange.kInitLine);
      shooter.shoot(ShotRange.kInitLine);
    }
    else if (10 < time && time < 15) 
    {
      drivetrain.arcadeDrive(-0.7, 0.2);
    }
  }

  @Override
  public void teleopPeriodic()
  {    
    drivetrain.arcadeDrive(driverController.getY(Hand.kLeft), driverController.getX(Hand.kRight));
    
    drivetrain.aim(ShotRange.kInitLine);
    drivetrain.aim(ShotRange.kTrench);

    intake.intake();

    feeder.feed();
    
    shooter.shoot(ShotRange.kInitLine);
    shooter.shoot(ShotRange.kTrench);

    controlPanel.rotationControl();
    controlPanel.positionControl();

    lift.lift();
  }
}
