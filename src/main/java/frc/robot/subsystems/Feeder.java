package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.*;

public class Feeder extends SubsystemBase{
    

    private SparkMax motor1;
    private SparkMax motor2;


    public Feeder() { 
        motor1 = new SparkMax(Constants.FeederConstants.leftFeederMotorCanId, MotorType.kBrushless);
        motor2 = new SparkMax(Constants.FeederConstants.rightFeederMotorCanId, MotorType.kBrushless);
    }

    public void feed() {
        motor1.set(0.3);
        motor2.set(0.3);
    }

    public void stop() {
        motor1.stopMotor();
        motor2.stopMotor();
    }
}
