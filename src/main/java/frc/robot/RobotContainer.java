// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.Drive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drive m_robotDrive = new Drive();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    // Reset the encoders when the "a" button is pressed
    m_driverController
        .a()
        .debounce(0.1)
        .onTrue(new InstantCommand(() -> m_robotDrive.resetEncoders(), m_robotDrive));

    // Set drive default command
    m_robotDrive.setDefaultCommand(
        new RunCommand(
            () ->
                m_robotDrive.arcadeDrive(
                    -MathUtil.applyDeadband(
                        m_driverController.getLeftY(), OperatorConstants.kDriveDeadband),
                    -MathUtil.applyDeadband(
                        m_driverController.getRightX(), OperatorConstants.kDriveDeadband)),
            m_robotDrive));

    /*
      // alternative version of setting the default command that uses the Drive.arcadeDriveCommand()
      m_robotDrive.setDefaultCommand(
          m_robotDrive.arcadeDriveCommand(
              () ->
                  -MathUtil.applyDeadband(
                      m_driverController.getLeftY(), OperatorConstants.kDriveDeadband),
              () ->
                  -MathUtil.applyDeadband(
                      m_driverController.getRightX(), OperatorConstants.kDriveDeadband)));
    */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.simpleAuto(m_robotDrive);

    // Alternative version of simpleAuto that used chained commands
    // return m_robotDrive.driveTimedCommand(0.5, 2).andThen(m_robotDrive.driveTimedCommand(-0.5, 1));
  }
}
