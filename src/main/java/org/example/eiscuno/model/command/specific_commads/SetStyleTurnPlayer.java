package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

public class SetStyleTurnPlayer implements Command {
    private final GameUnoController controller;
    private final boolean turnPlayer;

    public SetStyleTurnPlayer(GameUnoController controller, boolean turnPlayer) {
        this.controller = controller;
        this.turnPlayer = turnPlayer;
    }

    @Override
    public void execute() {
        controller.turnPlayerStyle(turnPlayer);
    }
}
