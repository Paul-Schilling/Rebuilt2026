package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private SparkFlex intakeMotor;

    public Intake() {
        intakeMotor = new SparkFlex(Constants.IntakeConstants.intakeCanId, MotorType.kBrushless);
    }

    public void start() {
        intakeMotor.set(0.3);
    }
    
    public void stop() {
        intakeMotor.stopMotor();
    }

}
