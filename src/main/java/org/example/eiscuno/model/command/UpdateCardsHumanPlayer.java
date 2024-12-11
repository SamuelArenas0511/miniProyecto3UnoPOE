package org.example.eiscuno.model.command;

import org.example.eiscuno.controller.GameUnoController;

public class UpdateCardsHumanPlayer implements Command {
    private final GameUnoController controller;
    public UpdateCardsHumanPlayer(GameUnoController controller) {
        this.controller = controller;
    }
    @Override
    public void execute() {
        controller.printCardsHumanPlayer();
    }

    @Override
    public void showResult(boolean success) {
        controller.showResultAlert(success);
    }

    @Override
    public void setAnimationTakeCard(boolean player, int numCard) {
        controller.setAnimationTakeCard(player, numCard);
    }

    @Override
    public void setStyleTurnPlayer(boolean turnPlayer) {
        controller.turnPlayerStyle(turnPlayer);
    }



}
