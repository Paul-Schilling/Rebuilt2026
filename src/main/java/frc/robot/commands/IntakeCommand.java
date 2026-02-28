package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends Command {

    Intake intake;

    public IntakeCommand(Intake intake) {
        this.intake = intake;
        this.addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.start();
    }

    public void end(boolean isInterupted) {
        intake.stop();
    }
}