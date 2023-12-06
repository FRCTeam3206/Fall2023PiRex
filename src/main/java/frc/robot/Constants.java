// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kDriveDeadband = 0.05;
  }

  public static class DriveConstants {
    // SPARK MAX CAN IDs
    public static final int kLeftDriveCanId = 2;
    public static final int kRightDriveCanId = 1;
  }

  public static class AutonConstants {
    // The first four are private to reduce the number of options for referencing since the first
    // four constants are only used to set the last two.
    private static final double kLeftRightWheelDistInch = 9.962762; // as measured in CAD
    private static final double kFrontBackWheelDistInch = 7.5; // as measured in CAD
    private static final double kWheelRadiusInch =
        3.965079 / 2; // as measured in CAD, 3.965079 is diameter
    private static final double kTurningCircleRadiusInch =
        Math.sqrt(
            Math.pow(kLeftRightWheelDistInch / 2, 2) + Math.pow(kFrontBackWheelDistInch / 2, 2));
    public static final double kWheelCircumferenceInch = 2 * Math.PI * kWheelRadiusInch;
    public static final double kTurningCircleCircumferenceInch =
        2 * Math.PI * kTurningCircleRadiusInch;
  }
}
