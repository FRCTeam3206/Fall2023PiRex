// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import java.util.function.DoubleSupplier;

public class Drive extends SubsystemBase {
  // Initialize the motors and drivetrain
  private final CANSparkMax m_leftDrive =
      new CANSparkMax(DriveConstants.kLeftDriveCanId, MotorType.kBrushless);
  private final CANSparkMax m_rightDrive =
      new CANSparkMax(DriveConstants.kRightDriveCanId, MotorType.kBrushless);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftDrive, m_rightDrive);

  private RelativeEncoder m_leftEncoder;
  private RelativeEncoder m_rightEncoder;

  public Drive() {
    // restore motors to defaults so we can know how they will behave
    m_leftDrive.restoreFactoryDefaults();
    m_rightDrive.restoreFactoryDefaults();

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward.
    m_rightDrive.setInverted(true);

    m_leftEncoder = m_leftDrive.getEncoder();
    m_rightEncoder = m_rightDrive.getEncoder();
    resetEncoders();
  }

  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_drive.tankDrive(leftSpeed, rightSpeed);
  }

  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Position", m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Position", m_rightEncoder.getPosition());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /*********************************************/
  /* Advanced concepts using Command factories */
  /*********************************************/

  public Command arcadeDriveCommand(DoubleSupplier fwd, DoubleSupplier rot) {
    // uses Suppliers which allow more flexible sources of input
    return run(() -> m_drive.arcadeDrive(fwd.getAsDouble(), rot.getAsDouble()))
        .withName("arcadeDrive");
  }

  public Command driveTimedCommand(double speed, double time) {
    // Does the same thing as the frc.robot.commands.DriveTimed class
    return run(() -> m_drive.arcadeDrive(speed, 0))
        .withTimeout(time)
        .finallyDo(interrupted -> m_drive.stopMotor())
        .withName("driveTimed");
  }
}
