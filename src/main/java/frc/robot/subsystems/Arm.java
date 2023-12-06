package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  private VictorSPX armMotor = new VictorSPX(3); // NOT correct CAN Id

  public Arm() {
  }

  public void setSpeed(double speed) {
    armMotor.set(ControlMode.PercentOutput, speed);
  }
}
