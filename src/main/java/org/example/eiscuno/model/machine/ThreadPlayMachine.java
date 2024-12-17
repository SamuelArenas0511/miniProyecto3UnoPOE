package org.example.eiscuno.model.machine;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.command.Command;
import org.example.eiscuno.model.command.InvokerCommand;
import org.example.eiscuno.model.command.specific_commads.*;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.sound.music.MusicGame;
import org.example.eiscuno.model.table.Table;

import java.awt.*;
import java.util.Random;

/**
 * The {@code ThreadPlayMachine} class represents a thread that handles the game logic
 * for the machine (AI) player in a UNO game. It continuously checks if the player
 * has played a turn and then determines the machine's move.
 * <p>
 * This class interacts with the game state, processes card plays, and handles
 * special card effects while updating the game interface accordingly.
 * It also includes animations and sound effects for a smoother user experience.
 *
 * <p>
 * @author Samuel Arenas Valencia
 * @author Maria Juliana Saavedra
 * @author Juan Esteban Rodriguez
 * @version 1.0
 */
public class ThreadPlayMachine extends Thread {
    private final Player machinePlayer;
    private final Player humanPlayer;
    private final ImageView tableImageView;
    private final GameUno gameUno;
    private final GridPane gridPaneCardsMachine;
    private volatile boolean hasPlayerPlayed;
    private volatile boolean running = true;
    private boolean startGame = false;
    private GameUnoController gameUnoController;
    private MusicGame musicGame;


    /**
     * Constructs a {@code ThreadPlayMachine} object that initializes the machine
     * player and other game components for managing machine moves.
     *
     * @param table                 The {@code Table} object representing the game table.
     * @param machinePlayer         The machine player participating in the game.
     * @param humanPlayer           The human player participating in the game.
     * @param tableImageView        The {@code ImageView} representing the image of the current card on the table.
     * @param gameUno               The {@code GameUno} object containing the game logic.
     * @param gridPaneCardsMachine  The {@code GridPane} where machine player's cards are displayed.
     * @param gameUnoController     The controller managing the game's UI and logic.
     */

    public ThreadPlayMachine(Table table, Player machinePlayer, Player humanPlayer, ImageView tableImageView, GameUno gameUno, GridPane gridPaneCardsMachine, GameUnoController gameUnoController) {
        this.machinePlayer = machinePlayer;
        this.humanPlayer = humanPlayer;
        this.tableImageView = tableImageView;
        this.gameUno = gameUno;
        this.hasPlayerPlayed = false;
        this.gridPaneCardsMachine = gridPaneCardsMachine;
        this.gameUnoController = gameUnoController;
        this.musicGame = new MusicGame();
    }

    /**
     * Runs the thread, continuously checking if the machine should play its turn.
     */

    public void run() {
        while (true) {
            if((hasPlayerPlayed) && (running)){
                try{
                    Thread.sleep(2000);
                    putCardOnTheTable();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handles the logic for the machine to place a card on the table.
     * <p>
     * The method checks for playable cards, processes special cards, and updates
     * the game state accordingly. If no card is playable, the machine draws a card.
     *
     * @throws InterruptedException If the thread sleep is interrupted.
     */
    public void putCardOnTheTable() throws InterruptedException {
        Card card = null;
        int counter = 0;
        int cardsHumanPlayer = humanPlayer.getCardsPlayer().size();
        int cardsMachinePlayer = machinePlayer.getCardsPlayer().size();
        for (Card iter : machinePlayer.getCardsPlayer()) {
            if (gameUno.isCardPlayable(iter)) {
                card = iter;
                break;
            }
            counter++;
        }
        if (card == null && !gameUno.deck.isEmpty()) {
            musicGame.playDrawCardSound();
            System.out.println("La maquina comio carta");
            gameUno.eatCard(machinePlayer, 1);
            hasPlayerPlayed = false;
            Platform.runLater(() -> new InvokerCommand(new SetStyleTurnPlayer(gameUnoController, true)).invoke());
            Platform.runLater(() -> new InvokerCommand(new SetAnimationTakeCard(gameUnoController, false, 1)).invoke());
        } else if (card == null && gameUno.deck.isEmpty()) {
            determineWinner(cardsHumanPlayer, cardsMachinePlayer);
        } else {
            processCardPlay(card, counter, cardsMachinePlayer);
        }
    }

    /**
     * Determines the winner of the game if the deck is empty and no playable card exists.
     *
     * @param cardsHumanPlayer  The number of cards the human player holds.
     * @param cardsMachinePlayer The number of cards the machine player holds.
     */
    private void determineWinner(int cardsHumanPlayer, int cardsMachinePlayer) {
        if (cardsHumanPlayer < cardsMachinePlayer) {
            Platform.runLater(() -> new InvokerCommand(new ShowResultDeck(gameUnoController, Boolean.TRUE)).invoke());
            stopThread();
        } else if (cardsHumanPlayer == cardsMachinePlayer) {
            Platform.runLater(() -> new InvokerCommand(new ShowResultDeck(gameUnoController, null)).invoke());
            stopThread();
        } else {
            Platform.runLater(() -> new InvokerCommand(new ShowResultDeck(gameUnoController, Boolean.FALSE)).invoke());
            stopThread();
        }
    }

    /**
     * Processes the playable card, updating the game state, animations, and results.
     *
     * @param card               The card played by the machine.
     * @param counter            The index of the card in the machine's hand.
     * @param cardsMachinePlayer The current number of cards the machine player holds.
     * @throws InterruptedException If the thread sleep is interrupted.
     */
    private void processCardPlay(Card card, int counter, int cardsMachinePlayer) throws InterruptedException {
        machinePlayer.removeCard(counter);
        gameUno.playCard(card);
        tableImageView.setImage(card.getImage());
        Platform.runLater(this::printCardsMachinePlayer);

        if (gameUno.isEspecialCard(card)) {
            Thread.sleep(1000);
            playEspecialCard(humanPlayer, card);
            Platform.runLater(() -> new InvokerCommand(new UpdateCardsHumanPlayer(gameUnoController)).invoke());
        }
        cardsMachinePlayer--;
        if (cardsMachinePlayer == 0) {
            stopThread();
            Platform.runLater(() -> new InvokerCommand(new ShowResult(gameUnoController, false)).invoke());
        }
    }

    /**
     * Plays the special card and executes its effects, such as Draw Four or Skip.
     *
     * @param player The player affected by the special card.
     * @param card   The special card being played.
     * @throws InterruptedException If the thread sleep is interrupted.
     */

    public void playEspecialCard(Player player, Card card) throws InterruptedException {
        switch (card.getType()) {
            case "FOUR_WILD" -> {
                musicGame.playDrawFourSound();
                Platform.runLater(() -> new InvokerCommand(new SetAnimationTakeCard(gameUnoController, true, 4)).invoke());
                Thread.sleep(1000);
                gameUno.eatCard(player, 4);
                getRandomColor();
            }
            case "TWO_WILD" -> {
                musicGame.playDrawTwoSound();
                Platform.runLater(() -> new InvokerCommand(new SetAnimationTakeCard(gameUnoController, true, 2)).invoke());
                Thread.sleep(1000);
                gameUno.eatCard(player, 2);
            }
            case "WILD" -> {getRandomColor();
                musicGame.playWildCardSound();
            }case "RESERVE" -> {
                musicGame.playReverseSound();
            }case "SKIP" -> {
                musicGame.playSkipSound();
            }
        }
    }

    /**
     * Generates a random color for the machine player and updates the UI and game state.
     * <p>
     * This method selects a random color (BLUE, RED, YELLOW, GREEN), updates the table's
     * visual effects with a drop shadow corresponding to the selected color, plays the
     * relevant sound effect, and updates the game's state by calling {@code gameUno.setColorChoose}.
     */

    private void getRandomColor() {
        String[] colors = {"BLUE", "RED", "YELLOW", "GREEN"};
        Random random = new Random();
        String color = colors[random.nextInt(colors.length)];
        switch (color) {
            case "BLUE" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, blue, 20, 0, 0, 0)");
            case "RED" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0, 0, 0)");
            case "YELLOW" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 20, 0, 0, 0)");
            case "GREEN" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, green, 20, 0, 0, 0)");
        }
        playSoundColor(color);
        System.out.println("la maquina acaba de escoger el color: "+ color);
        gameUno.setColorChoose(color);
    }

    /**
     * Plays a sound corresponding to the specified color.
     * <p>
     * This method maps a given color to its associated sound effect, ensuring that
     * the selected color's auditory feedback is played.
     *
     * @param color A string representing the color ("BLUE", "RED", "YELLOW", "GREEN").
     */

    private void playSoundColor(String color) {
        switch (color) {
            case "BLUE" -> musicGame.playBlueSound();
            case "RED" -> musicGame.playRedSound();
            case "YELLOW" -> musicGame.playYellowSound();
            case "GREEN" -> musicGame.playGreenSound();
        }
    }

    /**
     * Prints the machine player's cards on the grid pane.
     */
    public void printCardsMachinePlayer() {
        tableImageView.setStyle(null);
        this.gridPaneCardsMachine.getChildren().clear();
        Card[] currentVisibleCardsMachinePlayer = this.gameUno.getCurrentVisibleCardsMachinePlayer(0);
        for (int i = 0; i < currentVisibleCardsMachinePlayer.length; i++) {
            ImageView cardImageView = new ImageView(String.valueOf(getClass().getResource("/org/example/eiscuno/cards-uno/card_uno.png")));
            if(startGame) {
                setAnimation(i, cardImageView);
            }else{
                this.gridPaneCardsMachine.add(cardImageView, i, 0);
            }
        }
        startGame = false;
    }

    private void setAnimation(int finalI, ImageView cardImageView) {
        PauseTransition delay = new PauseTransition(Duration.seconds( finalI * 0.5));
        delay.setOnFinished(event -> {
            gridPaneCardsMachine.add(cardImageView, finalI, 0);
            animateDealCard(cardImageView);
        });
        delay.play();
    }

    private void animateDealCard(ImageView cardImageView) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), cardImageView);
        scaleTransition.setFromX(1.5);
        scaleTransition.setFromY(1.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    /**
     * Sets whether the machine player has taken their turn in the game.
     *
     * @param hasPlayerPlayed A boolean value indicating if the machine player has played.
     */
    public void setHasPlayerPlayed(boolean hasPlayerPlayed) {
        this.hasPlayerPlayed = hasPlayerPlayed;
    }

    /**
     * Checks whether the machine player has taken their turn in the game.
     *
     * @return A boolean indicating if the machine player has already made their move.
     */
    public boolean isHasPlayerPlayed(){
        return this.hasPlayerPlayed;
    }


    /**
     * Stops the thread execution and resets relevant flags.
     */
    public void stopThread() {
        running = false;
        hasPlayerPlayed=false;
    }

}