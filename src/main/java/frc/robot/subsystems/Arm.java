package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants.ArmConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  private VictorSPX armMotor = new VictorSPX(ArmConstants.kArmCanId);

  public Arm() {
  }

  public void setSpeed(double speed) {
    armMotor.set(ControlMode.PercentOutput, ArmConstants.kVelocityPercentMultiplier * speed);
  }

  public void armTriggers() {
    
  }

  @Override
  public void periodic() {
  }
}
