package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Rollers;

public class RollerCommand extends Command {

    Rollers rollers; 

    public RollerCommand(Rollers rollers) {
        this.rollers = rollers;
        this.addRequirements(rollers);
    }

    @Override
    public void initialize() {
        rollers.start();
    }

    public void end(boolean isInterupted) {
        rollers.stop();
    }
}
