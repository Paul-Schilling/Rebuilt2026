// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.ConstantValues.ShooterConstants;
import frc.robot.commands.Feed;
import frc.robot.commands.RollerCommand;
import frc.robot.commands.ShooterDefaultCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakeDeployerDefaultCommand;
import frc.robot.commands.IntakeExtend;
import frc.robot.commands.IntakeRetract;
import frc.robot.commands.ResetManualIntake;
import frc.robot.commands.SaveConstantsNetworkTablesCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeDeployer;
import frc.robot.subsystems.Rollers;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController driveTrainController = new CommandXboxController(0);
    private final CommandXboxController shooterController = new CommandXboxController(1);


    public CommandSwerveDrivetrain drivetrain;
    private Shooter shooter;
    private Feeder feeder;
    private Rollers rollers;
    private Intake intake;
    private IntakeDeployer intakeDeployer;


    /* Path follower */
    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        drivetrain = TunerConstants.createDrivetrain();
        InitializeSubsystems();
        registerCommands();
        autoChooser = AutoBuilder.buildAutoChooser("Tests");
        SmartDashboard.putData("Auto Mode", autoChooser);
        // Publish current constants into NetworkTables so Shuffleboard widgets can edit them
        publishConstantsToNetworkTables();
        // Add Shuffleboard button to save current NetworkTables constants to disk
        SmartDashboard.putData("Save Constants", new SaveConstantsNetworkTablesCommand());
        configureBindings();

        // Warmup PathPlanner to avoid Java pauses
        FollowPathCommand.warmupCommand().schedule();
    }

    private void publishConstantsToNetworkTables() {
        var inst = NetworkTableInstance.getDefault();
        NetworkTable ct = inst.getTable("Constants");

        // Shooter
        ct.getEntry(ConstantsKeys.SHOOTER_MOTOR1).setDouble(ConstantValues.ShooterConstants.motor1CanId);
        ct.getEntry(ConstantsKeys.SHOOTER_MOTOR2).setDouble(ConstantValues.ShooterConstants.motor2CanId);
        ct.getEntry(ConstantsKeys.SHOOTER_KP).setDouble(ConstantValues.ShooterConstants.kP);
        ct.getEntry(ConstantsKeys.SHOOTER_KI).setDouble(ConstantValues.ShooterConstants.kI);
        ct.getEntry(ConstantsKeys.SHOOTER_KD).setDouble(ConstantValues.ShooterConstants.kD);
        ct.getEntry(ConstantsKeys.SHOOTER_KFF).setDouble(ConstantValues.ShooterConstants.kFF);
        ct.getEntry(ConstantsKeys.SHOOTER_SETPOINT).setDouble(ConstantValues.ShooterConstants.setPoint);
        ct.getEntry(ConstantsKeys.SHOOTER_KMAXI).setDouble(ConstantValues.ShooterConstants.kMaxI);

        // Feeder
        ct.getEntry(ConstantsKeys.FEEDER_MOTOR1).setDouble(ConstantValues.FeederConstants.motor1CanId);
        ct.getEntry(ConstantsKeys.FEEDER_KP).setDouble(ConstantValues.FeederConstants.kP);
        ct.getEntry(ConstantsKeys.FEEDER_KI).setDouble(ConstantValues.FeederConstants.kI);
        ct.getEntry(ConstantsKeys.FEEDER_KD).setDouble(ConstantValues.FeederConstants.kD);
        ct.getEntry(ConstantsKeys.FEEDER_KFF).setDouble(ConstantValues.FeederConstants.kFF);
        ct.getEntry(ConstantsKeys.FEEDER_SETPOINT).setDouble(ConstantValues.FeederConstants.setPoint);

        // Roller
        ct.getEntry(ConstantsKeys.ROLLER_ID).setDouble(ConstantValues.RollerConstants.rollerCanId);
        ct.getEntry(ConstantsKeys.ROLLER_KP).setDouble(ConstantValues.RollerConstants.kP);
        ct.getEntry(ConstantsKeys.ROLLER_KI).setDouble(ConstantValues.RollerConstants.kI);
        ct.getEntry(ConstantsKeys.ROLLER_KD).setDouble(ConstantValues.RollerConstants.kD);
        ct.getEntry(ConstantsKeys.ROLLER_KFF).setDouble(ConstantValues.RollerConstants.kFF);
        ct.getEntry(ConstantsKeys.ROLLER_SETPOINT).setDouble(ConstantValues.RollerConstants.setPoint);

        // Intake
        ct.getEntry(ConstantsKeys.INTAKE_MOTOR_ID).setDouble(ConstantValues.IntakeConstants.intakeCanId);
        ct.getEntry(ConstantsKeys.INTAKE_KP).setDouble(ConstantValues.IntakeConstants.kP);
        ct.getEntry(ConstantsKeys.INTAKE_KI).setDouble(ConstantValues.IntakeConstants.kI);
        ct.getEntry(ConstantsKeys.INTAKE_KD).setDouble(ConstantValues.IntakeConstants.kD);
        ct.getEntry(ConstantsKeys.INTAKE_KFF).setDouble(ConstantValues.IntakeConstants.kFF);
        ct.getEntry(ConstantsKeys.INTAKE_SETPOINT).setDouble(ConstantValues.IntakeConstants.setPoint);

        // IntakeDeployer
        ct.getEntry(ConstantsKeys.DEPLOYER_ID).setDouble(ConstantValues.IntakeDeployerConstants.deployerCanId);
        ct.getEntry(ConstantsKeys.DEPLOYER_KP).setDouble(ConstantValues.IntakeDeployerConstants.kP);
        ct.getEntry(ConstantsKeys.DEPLOYER_KI).setDouble(ConstantValues.IntakeDeployerConstants.kI);
        ct.getEntry(ConstantsKeys.DEPLOYER_KD).setDouble(ConstantValues.IntakeDeployerConstants.kD);
        ct.getEntry(ConstantsKeys.DEPLOYER_UP).setDouble(ConstantValues.IntakeDeployerConstants.upSetPoint);
        ct.getEntry(ConstantsKeys.DEPLOYER_DOWN).setDouble(ConstantValues.IntakeDeployerConstants.downSetPoint);
        ct.getEntry(ConstantsKeys.DEPLOYER_KG).setDouble(ConstantValues.IntakeDeployerConstants.kG);
        ct.getEntry(ConstantsKeys.DEPLOYER_KV).setDouble(ConstantValues.IntakeDeployerConstants.kV);
        ct.getEntry(ConstantsKeys.DEPLOYER_KA).setDouble(ConstantValues.IntakeDeployerConstants.kA);
        ct.getEntry(ConstantsKeys.DEPLOYER_EXTENDED).setDouble(ConstantValues.IntakeDeployerConstants.extendedLimit);
        ct.getEntry(ConstantsKeys.DEPLOYER_RETRACT).setDouble(ConstantValues.IntakeDeployerConstants.retractLimit);
        ct.getEntry(ConstantsKeys.DEPLOYER_EXT_SPEED).setDouble(ConstantValues.IntakeDeployerConstants.extendSpeed);
        ct.getEntry(ConstantsKeys.DEPLOYER_RET_SPEED).setDouble(ConstantValues.IntakeDeployerConstants.retractSpeed);

        // Vision
        ct.getEntry(ConstantsKeys.VISION_DIST_FRONT).setDouble(ConstantValues.Vision.distanceToFrontX);
        ct.getEntry(ConstantsKeys.VISION_DIST_LEFT).setDouble(ConstantValues.Vision.distanceFromLeftY);
        ct.getEntry(ConstantsKeys.VISION_DIST_FLOOR).setDouble(ConstantValues.Vision.distanceFromFloorZ);
        ct.getEntry(ConstantsKeys.VISION_CAMERA_NAME).setString(ConstantValues.Vision.cameraName);
        ct.getEntry(ConstantsKeys.VISION_USE).setBoolean(ConstantValues.Vision.useVision);
    }

    private void registerCommands() {
        NamedCommands.registerCommand("FireShooter",getFireCommand().withTimeout(3.0));
        NamedCommands.registerCommand("DeployIntake", new IntakeExtend(intakeDeployer));
        NamedCommands.registerCommand("runIntake", new IntakeCommand(intake));
        NamedCommands.registerCommand("stopIntake", new InstantCommand(() -> intake.stop(), intake));
    }

    private Command getFireCommand() {
        return new Feed(feeder).alongWith(new RollerCommand(rollers));
    }

    private Command runToPath(String pathName) {
      //  Command command = AutoBuilder.pathfindToPoseFlipped(new Pose2d(2.271, 3.991, new Rotation2d(Math.toRadians(180.000))), PathConstraints.unlimitedConstraints(12.0));
      //  return command.andThen(getFireCommand().withTimeout(2.0));
      try {
        PathPlannerPath goalPath = PathPlannerPath.fromPathFile(pathName);
        PathConstraints constraints = new PathConstraints(3.0, 4.0, Units.degreesToRadians(540), Units.degreesToRadians(720));
        return AutoBuilder.pathfindThenFollowPath(goalPath, constraints).andThen(drivetrain.applyRequest(() -> brake)).withTimeout(1);
      } catch(Exception e) {
        return drivetrain.applyRequest(() -> brake);
      }

    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-driveTrainController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driveTrainController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-driveTrainController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        driveTrainController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driveTrainController.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driveTrainController.getLeftY(), -driveTrainController.getLeftX()))
        ));

        driveTrainController.povUp().whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(0.5).withVelocityY(0))
        );
        driveTrainController.povDown().whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(-0.5).withVelocityY(0))
        );

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        driveTrainController.back().and(driveTrainController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driveTrainController.back().and(driveTrainController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        driveTrainController.start().and(driveTrainController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driveTrainController.start().and(driveTrainController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        driveTrainController.y().onTrue(runToPath("Tel Score From Middle"));

        // Reset the field-centric heading on left bumper press.
        driveTrainController.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    private void InitializeSubsystems() {
        try {
            shooter = new Shooter();
            ShooterDefaultCommand shooterCmd = new ShooterDefaultCommand(shooter);
            shooter.setDefaultCommand(shooterCmd);
        } catch(Throwable error) {
            System.out.println(error.getMessage());
        }
        try {
            feeder = new Feeder();
        } catch(Throwable error) {
            System.out.println(error.getMessage());
        }
        try {
            rollers = new Rollers();
        } catch(Throwable error) {
            System.out.println(error.getMessage());
        }
        try {
            intake = new Intake();
        } catch(Throwable error) {
            System.out.println(error.getMessage());
        }
        try {
            intakeDeployer = new IntakeDeployer();  
            intakeDeployer.setDefaultCommand(new IntakeDeployerDefaultCommand(intakeDeployer, shooterController));
            shooterController.rightBumper().onTrue(new IntakeExtend(intakeDeployer));
            shooterController.leftBumper().onTrue(new IntakeRetract(intakeDeployer));
            shooterController.leftStick().onTrue(new ResetManualIntake(intakeDeployer));
        } catch(Throwable error){
            System.out.println(error.getMessage());
        }
        if (rollers != null && feeder != null) {
            shooterController.leftTrigger().whileTrue(new Feed(feeder).alongWith(new RollerCommand(rollers)));
            shooterController.y().onTrue(new Feed(feeder).alongWith(new RollerCommand(rollers)).withTimeout(3));
        } 
        if (intake != null && rollers != null) {
            IntakeCommand intakeCmd = new IntakeCommand(intake);
            RollerCommand rollerCmd = new RollerCommand(rollers);
            shooterController.rightTrigger().whileTrue(intakeCmd.alongWith(rollerCmd));
        }


    }
}
