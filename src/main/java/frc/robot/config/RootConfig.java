package frc.robot.config;

public class RootConfig {
    public ShooterConfig ShooterConstants = new ShooterConfig();
    public FeederConfig FeederConstants = new FeederConfig();
    public RollerConfig RollerConstants = new RollerConfig();
    public IntakeConfig IntakeConstants = new IntakeConfig();
    public IntakeDeployerConfig IntakeDeployerConstants = new IntakeDeployerConfig();
    public VisionConfig Vision = new VisionConfig();

    public static class ShooterConfig {
        public int motor1CanId = 21;
        public int motor2CanId = 22;
        public double kP = 0.0005;
        public double kI = 0.0000005;
        public double kD = 0.0;
        public double kFF = 5.20;
        public double setPoint = 3000.0;
        public double kMaxI = 100.0;
    }

    public static class FeederConfig {
        public int motor1CanId = 25;
        // extra motor for two‑motor feeder
        public int motor2CanId = 26;
        public double kP = 0.0005;
        public double kI = 0.00000005;
        public double kD = 0.0;
        public double kFF = 5.0;
        public double setPoint = 4000.0;
    }

    public static class RollerConfig {
        public int rollerCanId = 27;
        public double kP = 0.0;
        public double kI = 0.0;
        public double kD = 0.0;
        public double kFF = 0.00015;
        public double setPoint = 3000.0;
    }

    public static class IntakeConfig {
        public int intakeCanId = 28;
        public double kP = 0.0;
        public double kI = 0.0;
        public double kD = 0.0;
        public double kFF = 20;
        public double setPoint = 3000.0;
    }

    public static class IntakeDeployerConfig {
        public int deployerCanId = 30;
        public double kP = 2.16;
        public double kI = 0.0;
        public double kD = 0.01;
        public double upSetPoint = 0.074;
        public double downSetPoint = 0.388;
        public double kG = 0.16;
        public double kV = 6.33;
        public double kA = 0.02;
        public double extendedLimit = 0.32;
        public double retractLimit = 0.01;
        public double extendSpeed = -0.1;
        public double retractSpeed = 0.1;
    }

    public static class VisionConfig {
        public double distanceToFrontX = 0.38;
        public double distanceFromLeftY = 0.39;
        public double distanceFromFloorZ = 0.85;
        public String cameraName = "PC_Camera";
        public boolean useVision = true;
    }
}
