package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.IntakeDeployer;

public class IntakeDeployerDefaultCommand  extends Command {
    private IntakeDeployer intakeDeployer;
    private CommandXboxController controller;
    
    public IntakeDeployerDefaultCommand(IntakeDeployer intakeDeployer, CommandXboxController controller) {
        this.intakeDeployer = intakeDeployer;
        this.controller = controller;
        this.addRequirements(intakeDeployer);
    }

    @Override
    public void execute() {
        
        if (controller.getHID().getPOV() == 0){
            intakeDeployer.retract();
        } else if (controller.getHID().getPOV() == 180) {
            intakeDeployer.extend();
        } else {
            intakeDeployer.stop();
        }
    }

}
