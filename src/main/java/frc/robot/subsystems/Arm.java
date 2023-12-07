package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  private VictorSPX armMotor = new VictorSPX(6);

  public Arm() {
  }

  public void setSpeed(double speed) {
    armMotor.set(ControlMode.PercentOutput, 0.2 * speed);
  }

  public void armTriggers() {
    
  }

  @Override
  public void periodic() {
  }
}
