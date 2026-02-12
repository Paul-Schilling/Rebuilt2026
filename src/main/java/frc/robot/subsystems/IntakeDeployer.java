package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import static frc.robot.Constants.IntakeDeployerConstants.*;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeDeployer extends SubsystemBase{
    private SparkFlex deployMotor;
    private State upState = new State(upSetPoint, 0.0);
    private State downState = new State(downSetPoint, 0.0);
    private ArmFeedforward ff;

    public IntakeDeployer() {
        deployMotor = new SparkFlex(deployerCanId, MotorType.kBrushless);
        ff = new ArmFeedforward(0, kG, kV, kA);
        var motorConfig = new SparkFlexConfig();
        motorConfig.closedLoop.pid(kP, kI, kD);
        motorConfig.closedLoop.outputRange(-.5, .5);
        motorConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder);
        motorConfig.softLimit.reverseSoftLimit(extendedLimit);
        motorConfig.softLimit.forwardSoftLimit(retractLimit);
        deployMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void retract() {
        deployMotor.set(retractSpeed);
    }

    public void extend() {
        deployMotor.set(extendSpeed);
    }

    public void runToDownPosition() {
        double feedforward = ff.calculate(downState.position*2*Math.PI, downState.velocity);
        deployMotor.getClosedLoopController().setSetpoint(downState.position, ControlType.kPosition, 
                                                                    ClosedLoopSlot.kSlot0, feedforward);
    }

    public void runToUpPosition() {
        double feedforward = ff.calculate(upState.position*2*Math.PI, upState.velocity);
        deployMotor.getClosedLoopController().setSetpoint(upState.position, ControlType.kPosition, 
                                                                    ClosedLoopSlot.kSlot0, feedforward);
    }

    public void stop() {
        deployMotor.stopMotor();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Deployer Position", deployMotor.getAbsoluteEncoder().getPosition());
    }
}
