package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class TurnDegrees extends CommandBase {
  Drive drive;
  private double goalAsDistance;
  private double leftEncoderDist;
  private double leftOffset;
  private double rightEncoderDist;
  private double rightOffset;

  public TurnDegrees(
      Drive drive, double degrees) { // uses degrees instead of radians because, for example, 45 is
    // easier to write than Math.PI / 4
    addRequirements(drive);
    this.drive = drive;
    goalAsDistance = Constants.AutonConstants.kTurningCircleCircumferenceInch * (degrees / 360);
  }

  public void initialize() {
    drive.arcadeDrive(0, 0);
    leftOffset = drive.getLeftEncoderPos();
    rightOffset = drive.getRightEncoderPos();
    leftEncoderDist = 0;
    rightEncoderDist = 0;
  }

  public void execute() {
    drive.arcadeDrive(0, 0.25);
    leftEncoderDist = drive.getLeftEncoderPos() - leftOffset;
    rightEncoderDist = drive.getRightEncoderPos() - rightOffset;
    // SmartDashboard.putNumber("Left Encoder Dist with TurnDegrees Offset", leftEncoderDist - leftEncoderOffsetAsRotations);
    // SmartDashboard.putNumber("Right Encoder Dist with TurnDegrees Offset", rightEncoderDist - rightEncoderOffsetAsRotations);
        
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
