package org.example.eiscuno.model.command.specific_commads;

import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;

/**
 * This class implements the Command pattern to set the visual style indicating the turn of a player.
 * It updates the visual representation of the current player's turn by calling the
 * `turnPlayerStyle()` method in the `GameUnoController`.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
public class SetStyleTurnPlayer implements Command {
    private final GameUnoController controller;
    private final boolean turnPlayer;

    /**
     * Constructs an instance of `SetStyleTurnPlayer`.
     *
     * @param controller The `GameUnoController` instance.
     *                   This reference allows access to game interface and logic methods.
     * @param turnPlayer A `boolean` indicating whose turn it is:
     *                   - `true` if it's the human player's turn.
     *                   - `false` if it's the machine's turn.
     */
    public SetStyleTurnPlayer(GameUnoController controller, boolean turnPlayer) {
        this.controller = controller;
        this.turnPlayer = turnPlayer;
    }

    /**
     * Executes the command to update the visual style of the current player's turn.
     * Calls the `turnPlayerStyle()` method in the `GameUnoController` to apply the style changes.
     */
    @Override
    public void execute() {
        controller.turnPlayerStyle(turnPlayer);
    }
}
