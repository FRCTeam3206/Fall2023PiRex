package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class ForwardInches extends CommandBase {
  Drive drive;
  private double distGoal;
  private double leftEncoderDist;
  private double leftOffset;
  private double rightEncoderDist;
  private double rightOffset;
  private double totalDist;

  public ForwardInches(Drive drive, double dist) {
    addRequirements(drive);
    this.drive = drive;
    distGoal = dist;
  }

  public void initialize() {
    drive.tankDrive(0, 0);
    leftOffset = drive.getLeftEncoderPos();
    rightOffset = drive.getRightEncoderPos();
    leftEncoderDist = 0;
    rightEncoderDist = 0;
    totalDist = 0;
  }

  public void execute() {
    drive.tankDrive(0.3, 0.3);
    // SmartDashboard.putNumber("Left Encoder Dist with ForwardInches Offset", leftEncoderDist - leftEncoderOffsetAsRotations);
    // SmartDashboard.putNumber("Right Encoder Dist with ForwardInches Offset", rightEncoderDist - rightEncoderOffsetAsRotations);
    leftEncoderDist = drive.getLeftEncoderPos() - leftOffset;
    rightEncoderDist = drive.getRightEncoderPos() - rightOffset;
    totalDist = (leftEncoderDist + rightEncoderDist) / 2;
  }

  public void end() {
    drive.tankDrive(0, 0);
  }

  public boolean isFinished() {
    return distGoal > 0 ? totalDist >= distGoal : totalDist <= distGoal;
  }
}
