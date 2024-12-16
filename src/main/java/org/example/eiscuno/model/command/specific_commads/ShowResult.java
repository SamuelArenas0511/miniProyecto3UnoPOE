package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

public class ShowResult implements Command {

    private final GameUnoController controller;
    private final boolean success;

    public ShowResult(GameUnoController controller, boolean success) {
        this.controller = controller;
        this.success = success;
    }

    @Override
    public void execute() {
        controller.showResultAlert(success);
    }

}
