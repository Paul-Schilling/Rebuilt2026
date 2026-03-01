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
import static frc.robot.ConstantValues.RollerConstants.*;

public class Rollers extends SubsystemBase {
    private SparkFlex rollerMotor;
    private SparkFlexConfig rollerMotorConfig;
    private double mP, mI, mD, mFF;

    public Rollers() {
        rollerMotor = new SparkFlex(rollerCanId, MotorType.kBrushless);
        this.mP = kP;
        this.mI = kI;
        this.mD = kD;
        this.mFF = kFF;
        rollerMotorConfig = createConfigurationForVelocity(true);
        rollerMotor.configure(rollerMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SmartDashboard.putNumber("roller P", mP);
        SmartDashboard.putNumber("roller I", mI);
        SmartDashboard.putNumber("roller D", mD);
        SmartDashboard.putNumber("roller FF", mFF);
    }

    public void start() {
        rollerMotor.set(0.5);
    }

    public void startAtVelocity() {
        rollerMotor.getClosedLoopController().setSetpoint(setPoint, ControlType.kVelocity, ClosedLoopSlot.kSlot0, mFF);
    }
    
    public void stop() {
        rollerMotor.stopMotor();
    }

    private SparkFlexConfig createConfigurationForVelocity(boolean inverted){
        var motorConfig = new SparkFlexConfig();
        motorConfig.closedLoop.pid(mP, mI, mD);
        motorConfig.closedLoop.maxOutput(1.0);
        motorConfig.closedLoop.minOutput(-1.0);
        motorConfig.inverted(inverted);
        return motorConfig;
    }

    @Override
    public void periodic(){
        double pVal = SmartDashboard.getNumber("roller P", mP);
        double iVal = SmartDashboard.getNumber("roller I", mI);
        double dVal = SmartDashboard.getNumber("roller D", mD);
        double ffVal = SmartDashboard.getNumber("roller FF", mFF);
        if (mP != pVal || mI != iVal || mD != dVal || mFF != ffVal) {
            mP = pVal;
            mI = iVal;
            mD = dVal;
            mFF = ffVal;
            rollerMotorConfig.closedLoop.pid(mP, mI, mD);
            rollerMotor.configure(rollerMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        }
        SmartDashboard.putNumber("Rollor Motor Velocity", rollerMotor.getEncoder().getVelocity());
    }
}
