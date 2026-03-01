package frc.robot;

public final class ConstantsKeys {
    private ConstantsKeys() {}

    public static final String TABLE = "Constants";

    // Shooter
    public static final String SHOOTER_MOTOR1 = "Shooter/motor1CanId";
    public static final String SHOOTER_MOTOR2 = "Shooter/motor2CanId";
    public static final String SHOOTER_KP = "Shooter/kP";
    public static final String SHOOTER_KI = "Shooter/kI";
    public static final String SHOOTER_KD = "Shooter/kD";
    public static final String SHOOTER_KFF = "Shooter/kFF";
    public static final String SHOOTER_SETPOINT = "Shooter/setPoint";
    public static final String SHOOTER_KMAXI = "Shooter/kMaxI";

    // Feeder
    public static final String FEEDER_MOTOR1 = "Feeder/motor1CanId";
    public static final String FEEDER_KP = "Feeder/kP";
    public static final String FEEDER_KI = "Feeder/kI";
    public static final String FEEDER_KD = "Feeder/kD";
    public static final String FEEDER_KFF = "Feeder/kFF";
    public static final String FEEDER_SETPOINT = "Feeder/setPoint";

    // Roller
    public static final String ROLLER_ID = "Roller/rollerCanId";
    public static final String ROLLER_KP = "Roller/kP";
    public static final String ROLLER_KI = "Roller/kI";
    public static final String ROLLER_KD = "Roller/kD";
    public static final String ROLLER_KFF = "Roller/kFF";
    public static final String ROLLER_SETPOINT = "Roller/setPoint";

    // Intake
    public static final String INTAKE_MOTOR_ID = "Intake/intakeCanId";
    public static final String INTAKE_KP = "Intake/kP";
    public static final String INTAKE_KI = "Intake/kI";
    public static final String INTAKE_KD = "Intake/kD";
    public static final String INTAKE_KFF = "Intake/kFF";
    public static final String INTAKE_SETPOINT = "Intake/setPoint";

    // IntakeDeployer
    public static final String DEPLOYER_ID = "IntakeDeployer/deployerCanId";
    public static final String DEPLOYER_KP = "IntakeDeployer/kP";
    public static final String DEPLOYER_KI = "IntakeDeployer/kI";
    public static final String DEPLOYER_KD = "IntakeDeployer/kD";
    public static final String DEPLOYER_UP = "IntakeDeployer/upSetPoint";
    public static final String DEPLOYER_DOWN = "IntakeDeployer/downSetPoint";
    public static final String DEPLOYER_KG = "IntakeDeployer/kG";
    public static final String DEPLOYER_KV = "IntakeDeployer/kV";
    public static final String DEPLOYER_KA = "IntakeDeployer/kA";
    public static final String DEPLOYER_EXTENDED = "IntakeDeployer/extendedLimit";
    public static final String DEPLOYER_RETRACT = "IntakeDeployer/retractLimit";
    public static final String DEPLOYER_EXT_SPEED = "IntakeDeployer/extendSpeed";
    public static final String DEPLOYER_RET_SPEED = "IntakeDeployer/retractSpeed";

    // Vision
    public static final String VISION_DIST_FRONT = "Vision/distanceToFrontX";
    public static final String VISION_DIST_LEFT = "Vision/distanceFromLeftY";
    public static final String VISION_DIST_FLOOR = "Vision/distanceFromFloorZ";
    public static final String VISION_CAMERA_NAME = "Vision/cameraName";
    public static final String VISION_USE = "Vision/useVision";
}
