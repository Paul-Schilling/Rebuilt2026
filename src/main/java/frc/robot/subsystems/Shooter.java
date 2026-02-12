package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase{
    private SparkFlex motor1;
    private SparkFlex motor2;

    public Shooter() {
        motor1 = new SparkFlex(motor1CanId, MotorType.kBrushless);
        motor1.configure(createConfigurationForVelocity(), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        motor2 = new SparkFlex(motor2CanId, MotorType.kBrushless);
        motor2.configure(createConfigurationForVelocity(), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void start() {
        motor1.getClosedLoopController().setSetpoint(setPoint, ControlType.kVelocity, ClosedLoopSlot.kSlot0, kFF);
        motor1.getClosedLoopController().setSetpoint(setPoint, ControlType.kVelocity, ClosedLoopSlot.kSlot0, kFF);
    }

    public void stop() {
        motor1.stopMotor();
        motor2.stopMotor();
    }

    private SparkFlexConfig createConfigurationForVelocity(){
        var motorConfig = new SparkFlexConfig();
        motorConfig.closedLoop.pid(kP, kI, kD);
        motorConfig.closedLoop.maxOutput(1.0);
        motorConfig.closedLoop.minOutput(-1.0);
        return motorConfig;
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Motor 1 Velocity", motor1.getEncoder().getVelocity());
        SmartDashboard.putNumber("Motor 2 Velocity", motor1.getEncoder().getVelocity());
    }
}
