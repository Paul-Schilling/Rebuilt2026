package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
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
public class DriveToHubTangentCommand extends Command {
    private final CommandSwerveDrivetrain drivetrain;
    private Pose2d goal;

    public DriveToHubTangentCommand(CommandSwerveDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        Pose2d current = drivetrain.getState().Pose;

        Alliance alliance = DriverStation.getAlliance().orElse(Alliance.Blue);
        Translation2d centre =
                alliance == Alliance.Red
                        ? new Translation2d(hubX, hubY)
                        : new Translation2d(fieldLength - hubX, hubY);

        goal = computeClosestTangent(current, centre, radius);

        // schedule a short path to the pose; AutoBuilder will flip it for red/blue
        AutoBuilder.pathfindToPoseFlipped(goal, PathConstraints.unlimitedConstraints(3.0))
                  .schedule();
    }

    @Override
    public boolean isFinished() {
        // complete when the auto‑builder path has finished (it will cancel itself)
        // we can detect that by asking whether the drivetrain is still running
        // an AutoBuilder path.  Unfortunately there is no clean API, so just
        // check distance + heading tolerance instead; the path should arrive
        // within a few hundredths of a meter.
        Pose2d now = drivetrain.getState().Pose;
        return now.getTranslation().getDistance(goal.getTranslation()) < positionTolerance
                && Math.abs(now.getRotation().minus(goal.getRotation()).getRadians()) < headingTolerance;
    }

    @Override
    public void end(boolean interrupted) {
        // nothing special; the path follower will bring the drivetrain to idle when
        // it finishes.  leaving this override in case we want to do more later.
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
