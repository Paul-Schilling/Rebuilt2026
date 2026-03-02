package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.ConfigIO;
import frc.robot.ConstantsKeys;
import frc.robot.config.RootConfig;

import java.io.IOException;

public class SaveConstantsNetworkTablesCommand extends InstantCommand {
    public SaveConstantsNetworkTablesCommand() {
        super(SaveConstantsNetworkTablesCommand::saveNow);
    }

    private static double getDoubleOr(NetworkTable table, String key, double fallback) {
        var e = table.getEntry(key);
        return e.exists() ? e.getDouble(fallback) : fallback;
    }

    private static int getIntOr(NetworkTable table, String key, int fallback) {
        var e = table.getEntry(key);
        return e.exists() ? (int) e.getDouble(fallback) : fallback;
    }

    private static String getStringOr(NetworkTable table, String key, String fallback) {
        var e = table.getEntry(key);
        return e.exists() ? e.getString(fallback) : fallback;
    }

    private static boolean getBooleanOr(NetworkTable table, String key, boolean fallback) {
        var e = table.getEntry(key);
        return e.exists() ? e.getBoolean(fallback) : fallback;
    }

    private static void saveNow() {
        try {
            NetworkTable ct = NetworkTableInstance.getDefault().getTable(ConstantsKeys.TABLE);

            RootConfig cfg = new RootConfig();

            // Shooter
            cfg.ShooterConstants.motor1CanId = getIntOr(ct, ConstantsKeys.SHOOTER_MOTOR1, cfg.ShooterConstants.motor1CanId);
            cfg.ShooterConstants.motor2CanId = getIntOr(ct, ConstantsKeys.SHOOTER_MOTOR2, cfg.ShooterConstants.motor2CanId);
            cfg.ShooterConstants.kP = getDoubleOr(ct, ConstantsKeys.SHOOTER_KP, cfg.ShooterConstants.kP);
            cfg.ShooterConstants.kI = getDoubleOr(ct, ConstantsKeys.SHOOTER_KI, cfg.ShooterConstants.kI);
            cfg.ShooterConstants.kD = getDoubleOr(ct, ConstantsKeys.SHOOTER_KD, cfg.ShooterConstants.kD);
            cfg.ShooterConstants.kFF = getDoubleOr(ct, ConstantsKeys.SHOOTER_KFF, cfg.ShooterConstants.kFF);
            cfg.ShooterConstants.setPoint = getDoubleOr(ct, ConstantsKeys.SHOOTER_SETPOINT, cfg.ShooterConstants.setPoint);
            cfg.ShooterConstants.kMaxI = getDoubleOr(ct, ConstantsKeys.SHOOTER_KMAXI, cfg.ShooterConstants.kMaxI);

            // Feeder
            cfg.FeederConstants.motor1CanId = getIntOr(ct, ConstantsKeys.FEEDER_MOTOR1, cfg.FeederConstants.motor1CanId);
            cfg.FeederConstants.motor2CanId = getIntOr(ct, ConstantsKeys.FEEDER_MOTOR2, cfg.FeederConstants.motor2CanId);
            cfg.FeederConstants.kP = getDoubleOr(ct, ConstantsKeys.FEEDER_KP, cfg.FeederConstants.kP);
            cfg.FeederConstants.kI = getDoubleOr(ct, ConstantsKeys.FEEDER_KI, cfg.FeederConstants.kI);
            cfg.FeederConstants.kD = getDoubleOr(ct, ConstantsKeys.FEEDER_KD, cfg.FeederConstants.kD);
            cfg.FeederConstants.kFF = getDoubleOr(ct, ConstantsKeys.FEEDER_KFF, cfg.FeederConstants.kFF);
            cfg.FeederConstants.setPoint = getDoubleOr(ct, ConstantsKeys.FEEDER_SETPOINT, cfg.FeederConstants.setPoint);

            // Roller
            cfg.RollerConstants.rollerCanId = getIntOr(ct, ConstantsKeys.ROLLER_ID, cfg.RollerConstants.rollerCanId);
            cfg.RollerConstants.kP = getDoubleOr(ct, ConstantsKeys.ROLLER_KP, cfg.RollerConstants.kP);
            cfg.RollerConstants.kI = getDoubleOr(ct, ConstantsKeys.ROLLER_KI, cfg.RollerConstants.kI);
            cfg.RollerConstants.kD = getDoubleOr(ct, ConstantsKeys.ROLLER_KD, cfg.RollerConstants.kD);
            cfg.RollerConstants.kFF = getDoubleOr(ct, ConstantsKeys.ROLLER_KFF, cfg.RollerConstants.kFF);
            cfg.RollerConstants.setPoint = getDoubleOr(ct, ConstantsKeys.ROLLER_SETPOINT, cfg.RollerConstants.setPoint);

            // Intake
            cfg.IntakeConstants.intakeCanId = getIntOr(ct, ConstantsKeys.INTAKE_MOTOR_ID, cfg.IntakeConstants.intakeCanId);
            cfg.IntakeConstants.kP = getDoubleOr(ct, ConstantsKeys.INTAKE_KP, cfg.IntakeConstants.kP);
            cfg.IntakeConstants.kI = getDoubleOr(ct, ConstantsKeys.INTAKE_KI, cfg.IntakeConstants.kI);
            cfg.IntakeConstants.kD = getDoubleOr(ct, ConstantsKeys.INTAKE_KD, cfg.IntakeConstants.kD);
            cfg.IntakeConstants.kFF = getDoubleOr(ct, ConstantsKeys.INTAKE_KFF, cfg.IntakeConstants.kFF);
            cfg.IntakeConstants.setPoint = getDoubleOr(ct, ConstantsKeys.INTAKE_SETPOINT, cfg.IntakeConstants.setPoint);

            // IntakeDeployer
            cfg.IntakeDeployerConstants.deployerCanId = getIntOr(ct, ConstantsKeys.DEPLOYER_ID, cfg.IntakeDeployerConstants.deployerCanId);
            cfg.IntakeDeployerConstants.kP = getDoubleOr(ct, ConstantsKeys.DEPLOYER_KP, cfg.IntakeDeployerConstants.kP);
            cfg.IntakeDeployerConstants.kI = getDoubleOr(ct, ConstantsKeys.DEPLOYER_KI, cfg.IntakeDeployerConstants.kI);
            cfg.IntakeDeployerConstants.kD = getDoubleOr(ct, ConstantsKeys.DEPLOYER_KD, cfg.IntakeDeployerConstants.kD);
            cfg.IntakeDeployerConstants.upSetPoint = getDoubleOr(ct, ConstantsKeys.DEPLOYER_UP, cfg.IntakeDeployerConstants.upSetPoint);
            cfg.IntakeDeployerConstants.downSetPoint = getDoubleOr(ct, ConstantsKeys.DEPLOYER_DOWN, cfg.IntakeDeployerConstants.downSetPoint);
            cfg.IntakeDeployerConstants.kG = getDoubleOr(ct, ConstantsKeys.DEPLOYER_KG, cfg.IntakeDeployerConstants.kG);
            cfg.IntakeDeployerConstants.kV = getDoubleOr(ct, ConstantsKeys.DEPLOYER_KV, cfg.IntakeDeployerConstants.kV);
            cfg.IntakeDeployerConstants.kA = getDoubleOr(ct, ConstantsKeys.DEPLOYER_KA, cfg.IntakeDeployerConstants.kA);
            cfg.IntakeDeployerConstants.extendedLimit = getDoubleOr(ct, ConstantsKeys.DEPLOYER_EXTENDED, cfg.IntakeDeployerConstants.extendedLimit);
            cfg.IntakeDeployerConstants.retractLimit = getDoubleOr(ct, ConstantsKeys.DEPLOYER_RETRACT, cfg.IntakeDeployerConstants.retractLimit);
            cfg.IntakeDeployerConstants.extendSpeed = getDoubleOr(ct, ConstantsKeys.DEPLOYER_EXT_SPEED, cfg.IntakeDeployerConstants.extendSpeed);
            cfg.IntakeDeployerConstants.retractSpeed = getDoubleOr(ct, ConstantsKeys.DEPLOYER_RET_SPEED, cfg.IntakeDeployerConstants.retractSpeed);

            // Vision
            cfg.Vision.distanceToFrontX = getDoubleOr(ct, ConstantsKeys.VISION_DIST_FRONT, cfg.Vision.distanceToFrontX);
            cfg.Vision.distanceFromLeftY = getDoubleOr(ct, ConstantsKeys.VISION_DIST_LEFT, cfg.Vision.distanceFromLeftY);
            cfg.Vision.distanceFromFloorZ = getDoubleOr(ct, ConstantsKeys.VISION_DIST_FLOOR, cfg.Vision.distanceFromFloorZ);
            cfg.Vision.cameraName = getStringOr(ct, ConstantsKeys.VISION_CAMERA_NAME, cfg.Vision.cameraName);
            cfg.Vision.useVision = getBooleanOr(ct, ConstantsKeys.VISION_USE, cfg.Vision.useVision);

            // Persist using ConfigIO
            ConfigIO.save(cfg);

        } catch (IOException e) {
            DriverStation.reportError("Failed saving constants: " + e.getMessage(), e.getStackTrace());
        }
    }
}
