// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kCoDriverControllerPort = 1;
  }

  public static class DriveConstantsDemobot {
    public static final int TALON_FL_ID = 30;
    public static final int SPARK_ML_ID = 3;
    public static final int TALON_BL_ID = 29;
    public static final int ARM_ID = 33;
    public static final int ARMUPDOWN_ID = 36;
    public static final int SPARK_FR_ID = 1;
    public static final int TALON_MR_ID = 22;
    public static final int TALON_BR_ID = 21;
    public static final double ARM_SPEED = 0.5;
    public static final double ARMUPDOWN_SPEED = 0.2;

    public static final boolean LEFT_ENCODER_INVERTED = false;
    public static final boolean RIGHT_ENCODER_INVERTED = true;

    public static final double DRIVE_SPEED = 0.5;
  }

  public static class DriveConstants extends DriveConstantsDemobot {
  }

  public static class TurretConstants {
    public static final int kTurretTalonFX = 1;
    public static final double kMaxOutput = 0.5;
  }

  public static class TelescopeConstants {
    public static final int kExtensionTalonSRX = 33;
  }

  public static class ArmConstants {
    public static final int kRaiseSpark = 4;
  }

  public static class GRABOTRONConstants {
    public static final int kRevPneumaticsHubId = 1;
    public static final int kExtendSolenoid = 0;
    public static final int kRetractSolenoid = 1;
  }
}