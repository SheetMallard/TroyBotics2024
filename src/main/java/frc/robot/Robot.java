// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Commands.TurnCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  Constants constants = new Constants();

  //private double test;
  private boolean able = true;

  /*
  private final Talon rightMotor = new Talon(constants.RIGHT_MOTOR_PORT);
  private final Talon leftMotor = new Talon(constants.LEFT_MOTOR_PORT);
  private final Talon arm = new Talon(constants.ARM_PORT);
  private final Talon intake = new Talon(constants.INTAKE_PORT);
  */
  private final Talon rightSpeedGroup = new Talon(constants.RIGHT_MOTOR_PORT);
  private final Talon leftSpeedGroup = new Talon(constants.LEFT_MOTOR_PORT);
  private final Talon armSpeedGroup = new Talon(constants.ARM_PORT);
  private final Talon armSpeedGroup2 = new Talon(constants.ARM_PORT2);
  private final Talon intakeSpeedGroup = new Talon(constants.INTAKE_PORT);

  /*
  private final MotorControllerGroup rightSpeedGroup = new MotorControllerGroup(rightMotor);
  private final MotorControllerGroup leftSpeedGroup = new MotorControllerGroup(leftMotor);
  private final MotorControllerGroup intakeSpeedGroup = new MotorControllerGroup(intake);
  private final MotorControllerGroup armSpeedGroup = new MotorControllerGroup(arm);
  */
  //private final PWMMotorController rightSpeedGroup = new PWMMotorController(rightMotor);

  DifferentialDrive drivetrain = new DifferentialDrive(rightSpeedGroup, leftSpeedGroup);
  

  XboxController stick = new XboxController(0);
  XboxController stick2 = new XboxController(1);


  private double Xspeed = 0.82;
  private double Xspeed2 = 0.6;
  //-private double Zspeed = 0.7;
  //private boolean oneStick = false;
  private boolean backwards = false;

  private double Xaxis = 0.0;
  private double Xaxis2 = 0.0;
  //private double Zaxis = 0.0;
  private double t = 0.0;
  //private double t1 = 0.0;

  Timer time = new Timer();
  Timer time1 = new Timer();
  TurnCommand turn = new TurnCommand(rightSpeedGroup, leftSpeedGroup);
  
  

  @Override
  public void robotInit() {
    //CameraServer.startAutomaticCapture();
    drivetrain.setSafetyEnabled(true);
    rightSpeedGroup.setInverted(true);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Forward Speed", Xspeed);
    SmartDashboard.putBoolean("Backwards", backwards);
    SmartDashboard.putNumber("Timer", t);
    SmartDashboard.putBoolean("Auto enabled", able);
  }

  @Override
  public void autonomousInit() {
    time.start();
  }

  @Override
  public void autonomousPeriodic() {
    if (able){
      t = time.get();
      if (time.get() <= 3.5){
        leftSpeedGroup.set(-0.25);
        rightSpeedGroup.set(-0.47);
      }
      /*else if (time.get() <= 3.36){
        leftSpeedGroup.set(0.4);
        rightSpeedGroup.set(-0.4);
      }*/
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    time.stop();
    //test = stick2.getLeftX();
    if (stick2.getRawButton(1)){
      armSpeedGroup.set(-constants.ARM_SPEED);
      armSpeedGroup2.set(constants.ARM_SPEED);
    }
    else{
      armSpeedGroup.set(0);
      armSpeedGroup2.set(0);
    }
    /*
    else{
      armSpeedGroup.set((constants.ARM_REDUCTION*stick2.getLeftY())/2);
      armSpeedGroup2.set((-constants.ARM_REDUCTION*stick2.getLeftY())/2);
    }
    armSpeedGroup.set(constants.ARM_REDUCTION*stick2.getLeftY());
    armSpeedGroup2.set(-constants.ARM_REDUCTION*stick2.getLeftY());
    */
    //intakeSpeedGroup.set(stick2.getZ()/constants.INTAKE_REDUCTION);
    intakeSpeedGroup.set(-stick2.getLeftY());

    /*if (stick2.getRawButton(1)){
      intakeSpeedGroup.set(stick2.getRightY());
    }
    else if (stick2.getRawButton(2)){
      intakeSpeedGroup.set(-constants.INTAKE_SPEED);
    }
    else if (stick2.getRawButton(3)){
      intakeSpeedGroup.set(-1);
    }
    else{
      intakeSpeedGroup.set(0);
    }*/
    //armSpeedGroup.set(constants.ARM_REDUCTION*stick2.getLeftX());
    if(stick.getRightBumperReleased()){
      Xspeed = 1.0;
      Xspeed2 = 0.78;
    }
    if(stick.getLeftBumperReleased()){
      Xspeed = 0.82;
      Xspeed2 = 0.6;
    }
    /*
    if (stick.getRightTriggerAxis() >= 0.6){
      if (Xspeed < 1.0){
        Xspeed += constants.CHANGE_IN_SPEED;
        Xspeed += constants.CHANGE_IN_SPEED;
      }
    }
    if (stick.getLeftTriggerAxis() >= 0.6){
      if (Xspeed > 0.4){
        Xspeed -= constants.CHANGE_IN_SPEED;
        Xspeed2 -= constants.CHANGE_IN_SPEED;
      }
    } 
    if (stick.getBButtonReleased()){
      if (Zspeed == 0.7 || Zspeed == 1.0){
        Zspeed = 0.5;
      }
      else{
        Zspeed = 0.7;
      }
    }
    if(stick.getAButtonReleased()){
      oneStick = !oneStick;
    }
    if (oneStick){
      Zaxis = stick.getLeftX();
    }
    else{
      Zaxis = stick.getRightX();
    }
    */
    if(stick.getYButtonReleased()){
      backwards = !backwards;
    }
    if(!backwards){
      Xaxis = stick.getLeftY();
      Xaxis2 = stick.getRightY();
    }
    else{
      Xaxis = -stick.getLeftY();
      Xaxis2 = -stick.getRightY();
    }
    /*if (stick.getXButtonReleased()){
      time1.start();
    }*/
    //t1 = time1.get();
    drivetrain.tankDrive(Xspeed*Xaxis, Xspeed2*Xaxis2);
    /*if (time1.get() <= 0.36 && time1.get() != 0){
      leftSpeedGroup.set(-0.4);
      rightSpeedGroup.set(0.4);
    }
    else{
      time1.stop();
      time1.reset();
    }*/
  }

  @Override
  public void disabledInit() {
    time.stop();
    time.reset();
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
