package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase{
    private SparkFlex leftMotor;
    private SparkFlex rightMotor;

    public Shooter() {
        leftMotor = new SparkFlex(Constants.ShooterConstants.leftCanId, MotorType.kBrushless);
        rightMotor = new SparkFlex(Constants.ShooterConstants.rightCanId, MotorType.kBrushless);
    }

    public void start() {
        leftMotor.set(0.4);
        rightMotor.set(0.4);
    }

    public void stop() {
        leftMotor.set(0.0);
        rightMotor.set(0.0);
    }
}
