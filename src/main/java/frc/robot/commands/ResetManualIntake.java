package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeDeployer;

public class ResetManualIntake extends Command{

    IntakeDeployer intakeDeployer;
    
    public ResetManualIntake(IntakeDeployer intakeDeployer) {
        this.intakeDeployer = intakeDeployer;
        this.addRequirements(intakeDeployer);
    }


    @Override
    public void end(boolean isInterupted) {
        intakeDeployer.stop();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
