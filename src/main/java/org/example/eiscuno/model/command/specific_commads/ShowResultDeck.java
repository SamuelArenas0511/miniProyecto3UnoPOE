package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

public class ShowResultDeck implements Command {

    private final GameUnoController controller;
    private final Boolean success;

    public ShowResultDeck(GameUnoController controller, Boolean success) {
        this.controller = controller;
        this.success = success;
    }

    @Override
    public void execute() {
        controller.showResultDeckAlert(success);
    }

}
