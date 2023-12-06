package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class TurnDegrees extends CommandBase {
  Drive drive;
  private double goalAsDistance;
  private double leftEncoderDist;
  private double leftEncoderOffsetAsRotations;
  private double rightEncoderDist;
  private double rightEncoderOffsetAsRotations;

  public TurnDegrees(
      Drive drive, double degrees) { // uses degrees instead of radians because, for example, 45 is
    // easier to write than Math.PI / 4
    addRequirements(drive);
    this.drive = drive;
    goalAsDistance = Constants.AutonConstants.kTurningCircleCircumferenceInch * (degrees / 360);
  }

  public void initialize() {
    drive.arcadeDrive(0, 0);
    leftEncoderOffsetAsRotations = drive.getLeftEncoderPos();
    rightEncoderOffsetAsRotations = drive.getRightEncoderPos();
    leftEncoderDist = 0;
    rightEncoderDist = 0;
  }

  public void execute() {
    leftEncoderDist =
        Constants.AutonConstants.kWheelCircumferenceInch
            * (drive.getLeftEncoderPos() - leftEncoderOffsetAsRotations);
    rightEncoderDist =
        Constants.AutonConstants.kWheelCircumferenceInch
            * (drive.getRightEncoderPos() - rightEncoderOffsetAsRotations);
    drive.arcadeDrive(0, 0.5);
  }

  public void end() {
    drive.arcadeDrive(0, 0);
  }

  public boolean isFinished() {
    if (goalAsDistance > 0) {
      return (Math.abs(leftEncoderDist) + Math.abs(rightEncoderDist)) / 2 >= goalAsDistance;
    } else {
      return -(Math.abs(leftEncoderDist) + Math.abs(rightEncoderDist)) / 2 <= goalAsDistance;
    }
  }
}
