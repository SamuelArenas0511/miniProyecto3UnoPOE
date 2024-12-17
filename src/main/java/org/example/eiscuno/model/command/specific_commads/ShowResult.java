package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

/**
 * This class implements the Command pattern to display the game result.
 * It shows an alert dialog indicating whether the game was a success or failure by calling the
 * `showResultAlert()` method in the `GameUnoController`.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
public class ShowResult implements Command {

    private final GameUnoController controller;
    private final boolean success;

    /**
     * Constructs an instance of `ShowResult`.
     *
     * @param controller The `GameUnoController` instance.
     *                   This is required to access methods that handle game interactions.
     * @param success A `boolean` representing the game outcome:
     *                 - `true` if the human player won the game.
     *                 - `false` if the human player lost the game.
     */
    public ShowResult(GameUnoController controller, boolean success) {
        this.controller = controller;
        this.success = success;
    }

    /**
     * Executes the command to display the game result.
     * Calls the `showResultAlert()` method in the `GameUnoController` to show the alert dialog.
     */
    @Override
    public void execute() {
        controller.showResultAlert(success);
    }

}
