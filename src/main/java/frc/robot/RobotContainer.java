// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ForwardInches;
import frc.robot.commands.TurnDegrees;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drive m_robotDrive = new Drive();
  private final Arm m_arm = new Arm();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  SendableChooser<Command> auton_chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    chooseAuton();

  }

  private void configureBindings() {
    // Reset the encoders when the "a" button is pressed
    /*
     * m_driverController
     * .a()
     * .debounce(0.1)
     * .onTrue(new InstantCommand(() -> m_robotDrive.resetEncoders(),
     * m_robotDrive));
     */

    // Set drive default command
    m_robotDrive.setDefaultCommand(
        new RunCommand(
            () -> m_robotDrive.arcadeDrive(
                0.5 * MathUtil.applyDeadband(
                    Math.signum(m_driverController.getLeftY()) * Math.pow(Math.abs(m_driverController.getLeftY()), 1.5),
                    OperatorConstants.kDriveDeadband),
                -0.6 * MathUtil.applyDeadband(
                    Math.signum(m_driverController.getRightX()) * Math.pow(Math.abs(m_driverController.getRightX()), 2),
                    OperatorConstants.kDriveDeadband)),
            m_robotDrive));

    /*
     * // alternative version of setting the default command that uses the
     * Drive.arcadeDriveCommand()
     * m_robotDrive.setDefaultCommand(
     * m_robotDrive.arcadeDriveCommand(
     * () ->
     * -MathUtil.applyDeadband(
     * m_driverController.getLeftY(), OperatorConstants.kDriveDeadband),
     * () ->
     * -MathUtil.applyDeadband(
     * m_driverController.getRightX(), OperatorConstants.kDriveDeadband)));
     */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  private void chooseAuton() {
    auton_chooser.setDefaultOption("Square For 15 Seconds",
        new RepeatCommand(new SequentialCommandGroup(new ForwardInches(m_robotDrive, 24), new TurnDegrees(
            m_robotDrive, 90)))
            .withTimeout(15));
    auton_chooser.addOption("Turn 90 Degrees", new TurnDegrees(m_robotDrive, 90));
    auton_chooser.addOption("Forward Three Feet", new ForwardInches(m_robotDrive, 36));
    auton_chooser.addOption("Score Corn",
        new SequentialCommandGroup(new ForwardInches(m_robotDrive, 48), new TurnDegrees(
            m_robotDrive, -90),
            new ForwardInches(m_robotDrive, 24), new InstantCommand(() -> m_arm.score())));
    SmartDashboard.putData(auton_chooser);
  }

  public Command getAutonomousCommand() {
    return auton_chooser.getSelected();

    // An example command will be run in autonomous
    // return Autos.simpleAuto(m_robotDrive);

    // Alternative version of simpleAuto that used chained commands
    // return m_robotDrive.driveTimedCommand(0.5,
    // 2).andThen(m_robotDrive.driveTimedCommand(-0.5,
    // 1));
  }
}
