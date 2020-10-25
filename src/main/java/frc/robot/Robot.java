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
import frc.robot.subsystems.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  XboxController driverController = new XboxController(0);
  XboxController operatorController = new XboxController(1);  
  
  Lift liftControl = new Lift(operatorController);
  Intake intakeControl = new Intake(operatorController);
  Shooter shooterControl = new Shooter(operatorController);
  Feeder feedControl = new Feeder(operatorController);
  ControlPanel controlPanel = new ControlPanel(operatorController);
  Limelight limelight = new Limelight();
  Drivetrain dt = new Drivetrain(limelight);

  Timer autoTimer = new Timer();

  @Override
  public void robotInit()
  {

  }

  @Override
  public void robotPeriodic()
  {
    if(controlPanel.gameColor == null)
      controlPanel.getGameColor();
    
    limelight.Update();
  }

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
      dt.aim(ShotRange.kInitLine);
      shooterControl.shoot(ShotRange.kInitLine);
    }
    else if (10 < time && time < 15) 
    {
      dt.arcadeDrive(-0.7, 0.2);
    }
  }

  @Override
  public void teleopPeriodic()
  {
    
    dt.arcadeDrive(driverController.getY(Hand.kLeft), driverController.getX(Hand.kRight));
  }
}
