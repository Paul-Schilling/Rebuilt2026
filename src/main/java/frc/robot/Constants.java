package frc.robot;

import edu.wpi.first.networktables.Publisher;

public class Constants {
    public class ShooterConstants {

        // TODO: replace motor1canID and motor2canID with the actual values.
        public static int motor1CanId = 21;
        public static int motor2CanId = 22;
        public static double kP = 0.0;
        public static double kI = 0.0;
        public static double kD = 0.0;
        public static double kFF = 0.00015;
        public static double setPoint = 500.0;
    }

    public class FeederConstants {
        public static int leftFeederMotorCanId = 25;
        public static int rightFeederMotorCanId = 26;
    }

    public class RollerConstants {
        public static int rollerCanId = 27;
    }

    public class IntakeConstants {
        public static int intakeCanId = 28;
    }

    

}
