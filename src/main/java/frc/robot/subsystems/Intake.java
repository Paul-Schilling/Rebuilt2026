package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants.*;

public class Intake extends SubsystemBase {
    private SparkFlex intakeMotor;
    private Double fP, fFF, fI, fD;
    private SparkFlexConfig motorConfig;

    public Intake() {
        fFF = kFF;
        fP = kP;
        fI = kI;
        fD = kD;
        SmartDashboard.putNumber("IntakeP", fP);
        SmartDashboard.putNumber("IntakeI", fI);
        SmartDashboard.putNumber("IntakeD", fD);
        SmartDashboard.putNumber("IntakeFF", fFF);
        intakeMotor = new SparkFlex(intakeCanId, MotorType.kBrushless);
        motorConfig = createConfigurationForVelocity(true);
        intakeMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void start() {
        intakeMotor.set(0.85);
    }

    public void startAtVelocity() {
        intakeMotor.getClosedLoopController().setSetpoint(setPoint, ControlType.kVelocity, ClosedLoopSlot.kSlot0, kFF);
    }
    
    public void stop() {
        intakeMotor.stopMotor();
    }

    private SparkFlexConfig createConfigurationForVelocity(boolean isInverted){
        var motorConfig = new SparkFlexConfig();
        motorConfig.closedLoop.pid(kP, kI, kD);
        motorConfig.closedLoop.maxOutput(1.0);
        motorConfig.closedLoop.minOutput(-1.0);
        motorConfig.inverted(isInverted);
        return motorConfig;
    }

    @Override
    public void periodic(){
        double pVal = SmartDashboard.getNumber("IntakeP", fP);
        double iVal = SmartDashboard.getNumber("IntakeI", fI);
        double dVal = SmartDashboard.getNumber("IntakeD", fD);
        double ffVal = SmartDashboard.getNumber("IntakeFF", fFF);
        fFF = SmartDashboard.getNumber("IntakeFF", fFF);
        if (pVal != fP || iVal != fI || dVal != fD || ffVal != fFF) {
            motorConfig.closedLoop.pid(pVal, iVal, dVal);
            intakeMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            fP = pVal;
            fI = iVal;
            fD = dVal;
            fFF = ffVal;
        }
        SmartDashboard.putNumber("Intake Motor Velocity", intakeMotor.getEncoder().getVelocity());
    }
}
