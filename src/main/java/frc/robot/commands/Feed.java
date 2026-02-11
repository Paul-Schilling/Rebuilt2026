package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;

public class Feed extends Command {
    Feeder feeder;

    public Feed(Feeder feeder) {
        this.feeder = feeder;
        this.addRequirements(feeder);
    }
       @Override
    public void initialize() {
        feeder.feed();
    }
    
    public void end (boolean isInterupted) {
        feeder.stop();
    }

}
