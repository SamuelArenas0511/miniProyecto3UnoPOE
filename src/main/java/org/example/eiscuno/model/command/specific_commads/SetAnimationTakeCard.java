package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

public class SetAnimationTakeCard implements Command {
    private final GameUnoController controller;
    private final boolean player;
    private final int numCard;

    public SetAnimationTakeCard(GameUnoController controller, boolean player, int numCard) {
        this.controller = controller;
        this.player = player;
        this.numCard = numCard;
    }

    @Override
    public void execute() {
        controller.setAnimationTakeCard(player, numCard);
    }

}
