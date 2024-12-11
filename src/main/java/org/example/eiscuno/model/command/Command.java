package org.example.eiscuno.model.command;

public interface Command {
    void execute();
    void showResult(boolean success);
    void setAnimationTakeCard(boolean playerm, int numCard);
    void setStyleTurnPlayer (boolean turnPlayer);
}
