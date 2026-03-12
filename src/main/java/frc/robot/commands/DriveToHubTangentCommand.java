package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import static frc.robot.ConstantValues.HubConstants.*;

/**
 * Drives the robot to the closest point on a circle (radius 2.286 m) surrounding
 * the alliance hub and faces the tangent of that circle.  When the command ends
 * it leaves the drivetrain stopped (open‑loop zero velocities).
 * <p>
 * The target is computed when the command initializes, using the drivetrain's
 * current pose and the current alliance color.
 */
public class DriveToHubTangentCommand extends edu.wpi.first.wpilibj2.command.SequentialCommandGroup {
    /**
     * A placeholder to hold the goal between the two steps of the group.
     * Using an array/AtomicReference allows us to mutate the value inside the
     * lambdas passed to {@code InstantCommand}.
     */
    private final java.util.concurrent.atomic.AtomicReference<Pose2d> goalRef =
            new java.util.concurrent.atomic.AtomicReference<>();

    public DriveToHubTangentCommand(CommandSwerveDrivetrain drivetrain) {
        // the drivetrain is required by both steps (the second step actually
        // schedules a command that also requires it), so we declare the
        // requirement here on the group itself.
        addRequirements(drivetrain);

        // first compute the goal
        addCommands(
                new edu.wpi.first.wpilibj2.command.InstantCommand(() -> {
                    Pose2d current = drivetrain.getState().Pose;

                    Alliance alliance = DriverStation.getAlliance().orElse(Alliance.Blue);
                    Translation2d centre =
                            alliance == Alliance.Red
                                    ? new Translation2d(hubX, hubY)
                                    : new Translation2d(fieldLength - hubX, hubY);

                    goalRef.set(computeClosestTangent(current, centre, radius));
                }),
                // then schedule the auto‑builder path using the previously computed
                // pose.  scheduling a command with drivetrain requirements is safe
                // because the previous instant command has already finished.
                new edu.wpi.first.wpilibj2.command.InstantCommand(() ->
                        AutoBuilder.pathfindToPoseFlipped(goalRef.get(),
                                PathConstraints.unlimitedConstraints(3.0))
                                  .schedule())
        );
    }


    private static Pose2d computeClosestTangent(Pose2d robot,
                                                 Translation2d centre,
                                                 double radius) {
        Translation2d delta = robot.getTranslation().minus(centre);
        if (delta.getNorm() < 1e-6) { // should never happen, but guard
            delta = new Translation2d(1, 0);
        }
        Translation2d radial = delta.div(delta.getNorm());
        Translation2d pointOnCircle = centre.plus(radial.times(radius));
        Rotation2d tangent = radial.getAngle().plus(Rotation2d.fromDegrees(tangentAngleDeg));
        return new Pose2d(pointOnCircle, tangent);
    }
}
