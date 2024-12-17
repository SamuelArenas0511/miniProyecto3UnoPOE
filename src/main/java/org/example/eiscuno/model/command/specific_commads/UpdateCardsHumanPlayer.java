package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;
/**
 * This class implements the Command pattern to update the human player's cards in the UNO game.
 * When the command is executed, it calls the `printCardsHumanPlayer` method in the main game controller.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
public class UpdateCardsHumanPlayer implements Command {
    private final GameUnoController controller;

    /**
     * Constructs an instance of `UpdateCardsHumanPlayer`.
     *
     * @param controller The `GameUnoController` instance.
     *                   This parameter is necessary to access methods and perform updates in the game logic.
     */
    public UpdateCardsHumanPlayer(GameUnoController controller) {
        this.controller = controller;
    }

    /**
     * Executes the command to update the human player's cards.
     * Calls the `printCardsHumanPlayer()` method on the controller to display the player's current cards.
     */
    @Override
    public void execute() {
        controller.printCardsHumanPlayer();
    }

}
