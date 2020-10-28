/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.Constants.ShotRange;
import frc.robot.subsystems.*;

//  TODO: Tell ShuffleBoard EVERYTHING - numBalls, errors, robot status, etc
  
public class Robot extends TimedRobot
{
  XboxController driverController = new XboxController(0);
  XboxController operatorController = new XboxController(1);

  Lift lift = new Lift();
  Shooter shooter = new Shooter();
  Feeder feeder = new Feeder();
  ControlPanel controlPanel = new ControlPanel();
  Drivetrain drivetrain = new Drivetrain(new Limelight());
  
  Autonomous auto = new Autonomous(drivetrain, shooter, feeder);

  @Override
  public void robotInit()  { auto.robotInit(); }
  
  @Override
  public void robotPeriodic()  { drivetrain.updateOdometry(); }

  @Override
  public void autonomousInit()  { auto.init(); }

  @Override
  public void autonomousPeriodic()  { auto.periodic(); }

  @Override
  public void teleopPeriodic()
  {
    boolean wantInitLineShot = operatorController.getXButton(),
            wantTrenchShot = operatorController.getYButton();
    
    if (!wantInitLineShot || !wantTrenchShot) //  If we're not aiming
      drivetrain.arcadeDrive(driverController.getY(Hand.kLeft), driverController.getX(Hand.kRight));  //  Driver control
    else
    {
      if (wantTrenchShot)  //  priority to trench shot in case operator hits both buttons
      {
        drivetrain.aim(ShotRange.kTrench);
        shooter.shoot(ShotRange.kTrench);
      } else if (wantInitLineShot)
      {
        drivetrain.aim(ShotRange.kInitLine);
        shooter.shoot(ShotRange.kInitLine);
      }
    }
    //  TODO: find out suitable power settings
    
    if(operatorController.getBButton())
      feeder.addBall();
    // else
      
      feeder.reverse(); //  TODO: redo controls to include a reverse button for jams or overintake
    
    switch(operatorController.getPOV(0))
    {
      case 270: //  Left DPad
        controlPanel.rotationControl();
        break;
      case 90:  //  Right DPad
        controlPanel.positionControl();
        break;
      case 0: //  Up/Forwards DPad
        lift.lift();
      default:
        break;
    }
  }
}
