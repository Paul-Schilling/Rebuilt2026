package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.*;

public class Feeder extends SubsystemBase{
    private SparkFlex beltMotor; // TODO: Talk to the builders and vwerify that the bult will have 2 motors.
    private SparkFlex indexMotor;
    

    private SparkMax leftMotor;
    private SparkMax rightMotor;


    public Feeder() { 
        beltMotor = new SparkFlex(Constants.FeederConstants.beltCanId, MotorType.kBrushless);
        indexMotor = new SparkFlex(Constants.FeederConstants.indexCanId, MotorType.kBrushless);
        leftMotor = new SparkMax(Constants.ShooterConstants.leftCanId, MotorType.kBrushless);
        rightMotor = new SparkMax(Constants.ShooterConstants.rightCanId, MotorType.kBrushless);
    }

    public void feed() {
        beltMotor.set(0.3);
        indexMotor.set(0.3);
        leftMotor.set(0.3);
        rightMotor.set(0.3);
    }

    public void stop() {
         beltMotor.set(0.0);
        indexMotor.set(0.0);
        leftMotor.set(0.0);
        rightMotor.set(0.0);+
    }
}
