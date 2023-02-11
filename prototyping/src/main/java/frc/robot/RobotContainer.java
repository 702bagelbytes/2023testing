// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GRABOTRONSubsystem;
import frc.robot.subsystems.TelescopeSubsystem;
import frc.robot.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final TurretSubsystem turretSubsystem = new TurretSubsystem();
  private final TelescopeSubsystem telescopeSubsystem = new TelescopeSubsystem();
  // private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final GRABOTRONSubsystem grabotronSubsystem = new GRABOTRONSubsystem();
  // private final CameraServer cameraServer = new Camera

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_coDriverController = new CommandXboxController(
      OperatorConstants.kCoDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driveSubsystem.setDefaultCommand(
        driveSubsystem.tankDriveCmd(() -> -m_driverController.getLeftY(), () -> -m_driverController.getRightY()));

    turretSubsystem.setDefaultCommand(turretSubsystem.moveCmd(() -> m_coDriverController.getRightX()));
    m_coDriverController.povUp().whileTrue(telescopeSubsystem.moveCmd(() -> -1.0));
    m_coDriverController.povDown().whileTrue(telescopeSubsystem.moveCmd(() -> 1.0));
    // armSubsystem.setDefaultCommand(armSubsystem.moveCmd(() ->
    // m_coDriverController.getLeftY()));
    m_coDriverController.a().onTrue(grabotronSubsystem.toggleCommand());

    // up/down dpad = out/in
    // left/right right analog stick = turret
    // up/down left analog stick = arm
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
