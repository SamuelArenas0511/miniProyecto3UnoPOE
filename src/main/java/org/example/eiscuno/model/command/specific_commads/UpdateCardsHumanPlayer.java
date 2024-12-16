package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

public class UpdateCardsHumanPlayer implements Command {
    private final GameUnoController controller;

    public UpdateCardsHumanPlayer(GameUnoController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.printCardsHumanPlayer();
    }

}
