package frc.robot;

import com.google.gson.JsonObject;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;

public class ConstantValues {
    private static final JsonObject shooterConst = ConstantsLoader.getShooterConstants();
    private static final JsonObject feederConst = ConstantsLoader.getFeederConstants();
    private static final JsonObject rollerConst = ConstantsLoader.getRollerConstants();
    private static final JsonObject intakeConst = ConstantsLoader.getIntakeConstants();
    private static final JsonObject intakeDeployerConst = ConstantsLoader.getIntakeDeployerConstants();
    private static final JsonObject visionConst = ConstantsLoader.getVisionConstants();
    private static final JsonObject hubConst = ConstantsLoader.getHubConstants();

    public static class ShooterConstants {
        public static final int motor1CanId = ConstantsLoader.getInt(shooterConst, "motor1CanId", 21);
        public static final int motor2CanId = ConstantsLoader.getInt(shooterConst, "motor2CanId", 22);
        public static final double kP = ConstantsLoader.getDouble(shooterConst, "kP", 0.0005);
        public static final double kI = ConstantsLoader.getDouble(shooterConst, "kI", 0.0000005);
        public static final double kD = ConstantsLoader.getDouble(shooterConst, "kD", 0.0);
        public static final double kFF = ConstantsLoader.getDouble(shooterConst, "kFF", 5.20);
        public static final double setPoint = ConstantsLoader.getDouble(shooterConst, "setPoint", 3000.0);
        public static final double kMaxI = ConstantsLoader.getDouble(shooterConst, "kMaxI", 100.0);
    }

    public static class FeederConstants {
        public static final int motor1CanId = ConstantsLoader.getInt(feederConst, "motor1CanId", 25);
        // secondary feeder motor ID (was accidentally removed/commented out)
        public static final int motor2CanId = ConstantsLoader.getInt(feederConst, "motor2CanId", 26);
        public static final double kP = ConstantsLoader.getDouble(feederConst, "kP", 0.0005);
        public static final double kI = ConstantsLoader.getDouble(feederConst, "kI", 0.00000005);
        public static final double kD = ConstantsLoader.getDouble(feederConst, "kD", 0.0);
        public static final double kFF = ConstantsLoader.getDouble(feederConst, "kFF", 5.0);
        public static final double setPoint = ConstantsLoader.getDouble(feederConst, "setPoint", 4000.0);
    }

    public static class RollerConstants {
        public static final int rollerCanId = ConstantsLoader.getInt(rollerConst, "rollerCanId", 27);
        public static final double kP = ConstantsLoader.getDouble(rollerConst, "kP", 0.0);
        public static final double kI = ConstantsLoader.getDouble(rollerConst, "kI", 0.0);
        public static final double kD = ConstantsLoader.getDouble(rollerConst, "kD", 0.0);
        public static final double kFF = ConstantsLoader.getDouble(rollerConst, "kFF", 0.00015);
        public static final double setPoint = ConstantsLoader.getDouble(rollerConst, "setPoint", 3000.0);
    }

    public static class IntakeConstants {
        public static final int intakeCanId = ConstantsLoader.getInt(intakeConst, "intakeCanId", 28);
        public static final double kP = ConstantsLoader.getDouble(intakeConst, "kP", 0.0);
        public static final double kI = ConstantsLoader.getDouble(intakeConst, "kI", 0.0);
        public static final double kD = ConstantsLoader.getDouble(intakeConst, "kD", 0.0);
        public static final double kFF = ConstantsLoader.getDouble(intakeConst, "kFF", 20);
        public static final double setPoint = ConstantsLoader.getDouble(intakeConst, "setPoint", 3000.0);
    }

    public static class IntakeDeployerConstants {
        public static final int deployerCanId = ConstantsLoader.getInt(intakeDeployerConst, "deployerCanId", 30);
        public static final double kP = ConstantsLoader.getDouble(intakeDeployerConst, "kP", 2.16);
        public static final double kI = ConstantsLoader.getDouble(intakeDeployerConst, "kI", 0.0);
        public static final double kD = ConstantsLoader.getDouble(intakeDeployerConst, "kD", 0.01);
        public static final double upSetPoint = ConstantsLoader.getDouble(intakeDeployerConst, "upSetPoint", 0.074);
        public static final double downSetPoint = ConstantsLoader.getDouble(intakeDeployerConst, "downSetPoint", 0.388);
        public static final double kG = ConstantsLoader.getDouble(intakeDeployerConst, "kG", 0.16);
        public static final double kV = ConstantsLoader.getDouble(intakeDeployerConst, "kV", 6.33);
        public static final double kA = ConstantsLoader.getDouble(intakeDeployerConst, "kA", 0.02);
        public static final double extendedLimit = ConstantsLoader.getDouble(intakeDeployerConst, "extendedLimit", 0.32);
        public static final double retractLimit = ConstantsLoader.getDouble(intakeDeployerConst, "retractLimit", 0.01);
        public static final double extendSpeed = ConstantsLoader.getDouble(intakeDeployerConst, "extendSpeed", -0.1);
        public static final double retractSpeed = ConstantsLoader.getDouble(intakeDeployerConst, "retractSpeed", 0.1);
    }

    public static class Vision {
        public static final double distanceToFrontX = ConstantsLoader.getDouble(visionConst, "distanceToFrontX", 0.38);
        public static final double distanceFromLeftY = ConstantsLoader.getDouble(visionConst, "distanceFromLeftY", 0.39);
        public static final double distanceFromFloorZ = ConstantsLoader.getDouble(visionConst, "distanceFromFloorZ", 0.85);
        public static final String cameraName = ConstantsLoader.getString(visionConst, "cameraName", "PC_Camera");
        public static final boolean useVision = ConstantsLoader.getBoolean(visionConst, "useVision", true);
        public static final AprilTagFieldLayout tagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);
        public static final Transform3d robotToCam = new Transform3d(distanceToFrontX, distanceFromLeftY, distanceFromFloorZ, new Rotation3d(0,0,0));
    }

    public static class HubConstants {
        public static final double fieldLength = ConstantsLoader.getDouble(hubConst, "fieldLength", 16.541);
        public static final double hubX = ConstantsLoader.getDouble(hubConst, "hubX", 4.6228);
        public static final double hubY = ConstantsLoader.getDouble(hubConst, "hubY", 4.034536);
        public static final double radius = ConstantsLoader.getDouble(hubConst, "radius", 2.286);
        public static final double positionTolerance = ConstantsLoader.getDouble(hubConst, "positionTolerance", 0.05);
        public static final double headingTolerance = ConstantsLoader.getDouble(hubConst, "headingTolerance", 0.05);
        public static final double tangentAngleDeg = ConstantsLoader.getDouble(hubConst, "tangentAngleDeg", 90.0);
    }
}
