package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Rollers extends SubsystemBase {
    private SparkFlex rollerMotor;

    public Rollers() {
        rollerMotor = new SparkFlex(Constants.RollerConstants.rollerCanId, MotorType.kBrushless);
    }

    public void start() {
        rollerMotor.set(0.3);
    }
    
    public void stop() {
        rollerMotor.stopMotor();
    }

}
