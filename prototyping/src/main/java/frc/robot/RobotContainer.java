// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GRABOTRONSubsystem;
import frc.robot.subsystems.TelescopeSubsystem;
import frc.robot.subsystems.TurretSubsystem;

import org.opencv.core.Mat;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final TurretSubsystem turretSubsystem = new TurretSubsystem();
  private final TelescopeSubsystem telescopeSubsystem = new TelescopeSubsystem();
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final GRABOTRONSubsystem grabotronSubsystem = new GRABOTRONSubsystem();
  // private final CameraServer cameraServer = new Camera

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_coDriverController = new CommandXboxController(
      OperatorConstants.kCoDriverControllerPort);

  // private final VisionThread visionThread;

  public RobotContainer() {
    // Creates UsbCamera and MjpegServer [1] and connects them
    // UsbCamera camera = CameraServer.startAutomaticCapture();

    // // Creates the CvSink and connects it to the UsbCamera
    // CvSink cvSink = CameraServer.getVideo();

    // // Creates the CvSource and MjpegServer [2] and connects them
    // CvSource outputStream = CameraServer.putVideo("Video", 640, 480);
    Runnable r = () -> {
      UsbCamera camera = CameraServer.startAutomaticCapture();
      // https://dylan-frc-docs.readthedocs.io/en/latest/docs/software/vision-processing/using-the-cameraserver-on-the-roborio.html
      camera.setResolution(640, 480);

      CvSink cvSink = CameraServer.getVideo();
      CvSource outputStream = CameraServer.putVideo("video", 640, 480);

      Mat source = new Mat();
      Mat output = new Mat();

      while (!Thread.interrupted()) {
        cvSink.grabFrame(source);
        outputStream.putFrame(output);
      }
    };

    new Thread(r).start();

    // this.visionThread = new VisionThread(camera, null, )

    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(
        driveSubsystem.tankDriveCmd(() -> -m_driverController.getLeftY(), () -> -m_driverController.getRightY()));

    turretSubsystem.setDefaultCommand(turretSubsystem.moveCmd(() -> m_coDriverController.getRightX()));
    m_coDriverController.povUp().whileTrue(telescopeSubsystem.moveCmd(() -> -1.0));
    m_coDriverController.povDown().whileTrue(telescopeSubsystem.moveCmd(() -> 1.0));
    armSubsystem.setDefaultCommand(armSubsystem.moveCmd(() -> m_coDriverController.getLeftY()));
    m_coDriverController.a().onTrue(grabotronSubsystem.toggleCommand());

    // up/down dpad = out/in
    // left/right right analog stick = turret
    // up/down left analog stick = arm
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
