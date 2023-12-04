import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnDegrees extends CommandBase {
  Drive drive;
  private double turnRadiansGoal;
  private double leftEncoderDist;
  private double leftEncoderOffsetAsRotations;
  private double rightEncoderDist;
  private double rightEncoderOffsetAsRotations;
  
  public TurnDegrees(Drive drive, double degrees) { // uses degrees instead of radians because, for example, 45 is easier to write than Math.PI / 4 
    addRequirements(drive);
    this.drive = drive;
    turnRadiansGoal = Math.PI * (degrees / 180);
  }
  
  public void initialize() {
    drive.arcadeDrive(0, 0);
    leftEncoderOffsetAsRotations = drive.getLeftEncoderPos();
    rightEncoderOffsetAsRotations = drive.getRightEncoderPos();
    leftEncoderDist = 0;
    rightEncoderDist = 0;
  }
  public void execute() {
    leftEncoderDist = 2 * Math.PI * (drive.getLeftEncoderPos() - leftEncoderOffsetAsRotations);
    rightEncoderDist = 2 * Math.PI * (drive.getRightEncoderPos() - rightEncoderOffsetAsRotations);
    drive.arcadeDrive(0, 0.5);
  }
  public void end() {
    drive.arcadeDrive(0, 0);
  }
  public boolean isFinished() {
    return (leftEncoderDist + rightEncoderDist) / 2 >= turnRadiansGoal;
  }
}
