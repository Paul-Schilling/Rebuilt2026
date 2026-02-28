package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeDeployer;

public class IntakeRetract extends Command{

    IntakeDeployer intakeDeployer;
    
    public IntakeRetract(IntakeDeployer intakeDeployer) {
        this.intakeDeployer = intakeDeployer;
        this.addRequirements(intakeDeployer);
    }

    @Override
    public void execute() {
        intakeDeployer.runToRetractedPosition();
    }

    @Override
    public void end(boolean isInterupted) {
        intakeDeployer.stop();
    }
}