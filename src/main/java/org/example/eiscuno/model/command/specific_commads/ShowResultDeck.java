package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

/**
 * This class implements the Command pattern to show the result of the deck comparison between
 * the human and machine players in the UNO game.
 * When the command is executed, it calls the `showResultDeckAlert()` method in the game controller.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
public class ShowResultDeck implements Command {

    private final GameUnoController controller;
    private final Boolean success;

    /**
     * Constructs an instance of `ShowResultDeck`.
     *
     * @param controller The `GameUnoController` instance.
     *                   This parameter is necessary to access methods for displaying the results.
     * @param success A `Boolean` representing the result of the comparison:
     *                 - `true` for a win.
     *                 - `false` for a loss.
     *                 - `null` for a draw.
     */
    public ShowResultDeck(GameUnoController controller, Boolean success) {
        this.controller = controller;
        this.success = success;
    }

    /**
     * Executes the command to show the result of the deck comparison alert.
     * Calls the `showResultDeckAlert()` method in the `GameUnoController`.
     */
    @Override
    public void execute() {
        controller.showResultDeckAlert(success);
    }

}
