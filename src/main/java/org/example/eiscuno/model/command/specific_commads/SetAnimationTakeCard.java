package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

public class SetAnimationTakeCard implements Command {
    private final GameUnoController controller;
    private final boolean player;
    private final int numCard;

    /**
     * Constructs an instance of `SetAnimationTakeCard`.
     *
     * @param controller The `GameUnoController` instance.
     *                   This reference connects the animation logic to the game controller.
     * @param player     A `boolean` indicating the player taking the card:
     *                   - `true` if it's the human player's turn.
     *                   - `false` if it's the machine player's turn.
     * @param numCard    An `int` representing the card's position or index being taken.
     */
    public SetAnimationTakeCard(GameUnoController controller, boolean player, int numCard) {
        this.controller = controller;
        this.player = player;
        this.numCard = numCard;
    }

    /**
     * Executes the command to trigger the card-taking animation.
     * Calls the `setAnimationTakeCard()` method in the `GameUnoController`.
     */
    @Override
    public void execute() {
        controller.setAnimationTakeCard(player, numCard);
    }

}
