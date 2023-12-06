package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class ForwardInches extends CommandBase {
  Drive drive;
  private double distGoal;
  private double leftEncoderDist;
  private double leftEncoderOffsetAsRotations;
  private double rightEncoderDist;
  private double rightEncoderOffsetAsRotations;

  public ForwardInches(Drive drive, double dist) {
    addRequirements(drive);
    this.drive = drive;
    distGoal = dist;
  }

  public void initialize() {
    drive.tankDrive(0, 0);
    leftEncoderOffsetAsRotations = drive.getLeftEncoderPos();
    rightEncoderOffsetAsRotations = drive.getRightEncoderPos();
    leftEncoderDist = 0;
    rightEncoderDist = 0;
  }

  public void execute() {
    drive.tankDrive(0.5, 0.5);
    leftEncoderDist =
        Constants.AutonConstants.kWheelCircumferenceInch
            * (drive.getLeftEncoderPos() - leftEncoderOffsetAsRotations);
    rightEncoderDist =
        Constants.AutonConstants.kWheelCircumferenceInch
            * (drive.getRightEncoderPos() - rightEncoderOffsetAsRotations);
  }

  public void end() {
    drive.tankDrive(0, 0);
  }

  public boolean isFinished() {
    return (leftEncoderDist - rightEncoderDist) / 2 >= distGoal;
  }
}
