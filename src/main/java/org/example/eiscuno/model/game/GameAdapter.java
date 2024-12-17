package org.example.eiscuno.model.game;

import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.player.Player;

public class GameAdapter implements IGameUno{
    @Override
    public void startGame() {

    }

    @Override
    public void eatCard(Player player, int numberOfCards) {

    }

    @Override
    public void playCard(Card card) {

    }

    @Override
    public boolean isEspecialCard(Card card) {
        return false;
    }

    @Override
    public void haveSungOne(String playerWhoSang) {

    }

    @Override
    public Card[] getCurrentVisibleCardsHumanPlayer(int posInitCardToShow) {
        return new Card[0];
    }

    @Override
    public Boolean isGameOver(Player player) {
        return null;
    }

    @Override
    public Player getHumanPlayer() {
        return null;
    }

    @Override
    public Player getMachinePlayer() {
        return null;
    }
}
