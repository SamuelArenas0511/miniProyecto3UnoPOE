package org.example.eiscuno.controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.util.Duration;
import org.example.eiscuno.controller.ButtonsHoverEffects.ButtonEffects;
import org.example.eiscuno.controller.animationsUtils.AnimationUtils;
import org.example.eiscuno.controller.resultalerts.ResultAlerts;
import org.example.eiscuno.model.card.Card;

import org.example.eiscuno.model.command.InvokerCommand;
import org.example.eiscuno.model.command.specific_commads.ShowResult;
import org.example.eiscuno.model.command.specific_commads.ShowResultDeck;
import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.machine.ThreadPlayMachine;
import org.example.eiscuno.model.machine.ThreadSingUNOMachine;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.sound.Sound;
import org.example.eiscuno.model.sound.music.MusicGame;
import org.example.eiscuno.model.table.Table;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;

import java.io.IOException;
import java.util.Objects;


/**
 * Controller class for the Uno game.
 */
public class GameUnoController {

    @FXML
    private GridPane gridPaneCardsMachine;

    @FXML
    private GridPane gridPaneCardsPlayer;

    @FXML
    private ImageView tableImageView;

    @FXML
    private BorderPane borderPaneGame;

    @FXML
    private Pane pnBtnChooseColor;

    @FXML
    private ImageView cardEat;

    @FXML
    private Button unoButton;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBackCard;

    @FXML
    private Button btnNextCard;

    @FXML
    private Button takeCardButton;

    @FXML
    private ImageView imgReverse;

    @FXML
    private ImageView imgSkip;

    @FXML
    private ImageView imgUno;

    public Player humanPlayer;
    private Player machinePlayer;
    private Table table;
    private GameUno gameUno;
    private int posInitCardToShow;
    private boolean startGame;

    private ThreadPlayMachine threadPlayMachine;

    private MusicGame musicGame;
    private Sound mainMusicGame;
    private Sound winSound;
    private Sound loseSound;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        initVariables();
        initMusic();
        setBackgroundImage();
        setDisableNextBackBtn();
        this.gameUno.startGame();
        printCardTable();
        printCardsHumanPlayer();
        setVisibilityButtonsChooseColor();
        initThreads();
    }

    /**
     * Initializes the music for the game.
     */
    private void initMusic() {
        musicGame = new MusicGame();

        mainMusicGame = new Sound();
        mainMusicGame.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/main_game_sound.WAV");
        mainMusicGame.loopSound();

        winSound = new Sound();
        winSound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/win_game_sound.WAV");
        loseSound = new Sound();
        loseSound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/lose_game_sound.WAV");
    }

    /**
     * Initializes the treads for the game.
     */
    private void initThreads() {
        ThreadSingUNOMachine threadSingUNOMachine = new ThreadSingUNOMachine(this.humanPlayer, this.machinePlayer, this.gameUno, this.gridPaneCardsPlayer, this);
        Thread t = new Thread(threadSingUNOMachine, "ThreadSingUNO");
        t.start();
        threadPlayMachine = new ThreadPlayMachine(this.table, this.machinePlayer, this.humanPlayer, this.tableImageView, this.gameUno, this.gridPaneCardsMachine, this);
        threadPlayMachine.start();
        threadPlayMachine.printCardsMachinePlayer();
    }

    /**
     * Toggles the visibility of the color choice panel.
     * Brings it to the front and shows or hides it.
     */
    private void setVisibilityButtonsChooseColor() {
        pnBtnChooseColor.toFront();
        pnBtnChooseColor.setVisible(!pnBtnChooseColor.isVisible());
    }

    /**
     * Initializes the variables for the game.
     */
    public void initVariables() {
        this.humanPlayer = new Player("HUMAN_PLAYER");
        this.machinePlayer = new Player("MACHINE_PLAYER");
        Deck deck = new Deck();
        this.table = new Table();
        this.gameUno = new GameUno(this.humanPlayer, this.machinePlayer, deck, this.table, this);
        this.posInitCardToShow = 0;
        this.startGame = true;
        cardEat.setVisible(false);
    }

    /**
     * Set background Image in the game
     */

    public void setBackgroundImage() {
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/org/example/eiscuno/images/backgroud_game.png")).toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        borderPaneGame.setBackground(new Background(background));
    }

    /**
     * Prints the first cards on the table.
     */
    private void printCardTable() {
        tableImageView.setImage(table.getCurrentCardOnTheTable().getImage());
    }

    /**
     * Prints the human player's cards on the grid pane.
     */
    public void printCardsHumanPlayer() {
        tableImageView.setStyle(null);
        this.gridPaneCardsPlayer.getChildren().clear();
        Card[] currentVisibleCardsHumanPlayer = this.gameUno.getCurrentVisibleCardsHumanPlayer(this.posInitCardToShow);
        for (int i = 0; i < currentVisibleCardsHumanPlayer.length; i++) {
            Card card = currentVisibleCardsHumanPlayer[i];
            ImageView cardImageView = card.getCard();
            cardImageView.getStyleClass().add("card-image");
            cardImageView.setOnMouseClicked((MouseEvent event) -> {
                selectedCard(card);
            });
            if (startGame) {
                cardImageView.setMouseTransparent(true);
                setAnimation(i, cardImageView);
//                musicGame.playDaleCardSound();
            } else {
                this.gridPaneCardsPlayer.add(cardImageView, i, 0);
            }
        }
        startGame = false;
    }

    /**
     * Determines if the selected card is valid
     */
    public void selectedCard(Card card) {
        int cardsHumanPlayer = humanPlayer.getCardsPlayer().size();
        if (gameUno.isCardPlayable(card) && !threadPlayMachine.isHasPlayerPlayed()) {
            musicGame.playPutCardSound();
            gameUno.playCard(card);
            tableImageView.setImage(card.getImage());
            humanPlayer.removeCard(findPosCardsHumanPlayer(card));
            if (gameUno.isEspecialCard(card)) {
                playEspecialCard(machinePlayer, card);
                threadPlayMachine.printCardsMachinePlayer();
                cardsHumanPlayer--;
                if (cardsHumanPlayer == 0) {
                    showResultAlert(true);
                } else {
                    return;
                }
            } else {
                cardsHumanPlayer--;
                threadPlayMachine.setHasPlayerPlayed(true);
                if (cardsHumanPlayer == 0) {
                    showResultAlert(true);
                }
            }
            printCardsHumanPlayer();
            turnPlayerStyle(false);
        }
    }

    /**
     * Sets the animation for dealing cards to the player
     */
    private void setAnimation(int finalI, ImageView cardImageView) {
        AnimationUtils.setDealAnimation(finalI, cardImageView, gridPaneCardsPlayer, () -> {
            turnPlayerStyle(true);
        });
    }

    /**
     * Executes the special action associated with a special card.
     * Updates the player's cards, applies animations, and manages
     * the interaction flow based on the type of card played.
     *
     * @param player the player who will be affected by the card.
     * @param card   the special card being played.
     */

    private void playEspecialCard(Player player, Card card) {
        printCardsHumanPlayer();
        turnPlayerStyle(true);
        switch (card.getType()) {
            case "FOUR_WILD" -> {
                musicGame.playDrawFourSound();

                if (gameUno.deck.isEmpty()){
                    if (gameUno.getHumanPlayer().getCardsPlayer().size() < gameUno.getMachinePlayer().getCardsPlayer().size()) {
                        showResultDeckAlert(Boolean.TRUE);
                    } else if (gameUno.getHumanPlayer().getCardsPlayer().size() == gameUno.getMachinePlayer().getCardsPlayer().size()) {
                        showResultDeckAlert(null);
                    } else {
                        showResultDeckAlert(Boolean.FALSE);
                    }
                } else {
                    setAnimationTakeCard(false, 4);
                    gameUno.eatCard(player, 4);
                    setVisibilityButtonsChooseColor();
                }
            }
            case "TWO_WILD" -> {
                musicGame.playDrawTwoSound();
                if (gameUno.deck.isEmpty()){
                    if (gameUno.getHumanPlayer().getCardsPlayer().size() < gameUno.getMachinePlayer().getCardsPlayer().size()) {
                        showResultDeckAlert(Boolean.TRUE);
                    } else if (gameUno.getHumanPlayer().getCardsPlayer().size() == gameUno.getMachinePlayer().getCardsPlayer().size()) {
                        showResultDeckAlert(null);
                    } else {
                        showResultDeckAlert(Boolean.FALSE);
                    }
                } else {
                    setAnimationTakeCard(false, 2);
                    gameUno.eatCard(player, 2);
                }
            }
            case "WILD" -> {setVisibilityButtonsChooseColor();
                musicGame.playWildCardSound();
            }case "RESERVE" -> {
                AnimationUtils.animateReverseCard(imgReverse);
                musicGame.playReverseSound();
            }case "SKIP" -> {
                AnimationUtils.animateSkipCard(imgSkip);
                musicGame.playSkipSound();
            }
        }
    }

    /**
     * Handles the event triggered when the player selects a color button.
     * Updates the chosen color for the game and applies a visual effect
     * to the table to indicate the chosen color.
     *
     * @param actionEvent the mouse event triggered when a color button is clicked.
     * @throws IllegalArgumentException if the button ID does not match a valid color.
     */

    public void onHandleClickChooseColor(MouseEvent actionEvent) {
        setVisibilityButtonsChooseColor();
        ImageView sourceButton = (ImageView) actionEvent.getSource();
        String buttonId = sourceButton.getId();

        String color = switch (buttonId) {
            case "btnBlue" -> "BLUE";
            case "btnRed" -> "RED";
            case "btnYellow" -> "YELLOW";
            case "btnGreen" -> "GREEN";
            default -> throw new IllegalArgumentException("Invalid button ID: " + buttonId);
        };

        playColorSound(color);
        gameUno.setColorChoose(color);
        AnimationUtils.applyTableEffect(tableImageView, color);
    }

    private void playColorSound(String color) {
        switch (color) {
            case "BLUE" -> musicGame.playBlueSound();
            case "RED" -> musicGame.playRedSound();
            case "YELLOW" -> musicGame.playYellowSound();
            case "GREEN" -> musicGame.playGreenSound();
        }
    }

    /**
     * Handles the hover effect for color selection buttons.
     * Adds a hover style effect to the button being hovered over.
     *
     * @param mouseEvent the mouse event triggered when hovering over a button.
     */
    public void onHandleMouseHover(MouseEvent mouseEvent) {
        ImageView sourceButton = (ImageView) mouseEvent.getSource();
        ButtonEffects.applyHoverEffect(sourceButton);
    }

    /**
     * Clears the hover effect from a color selection button.
     * Resets the visual style of the button after the mouse exits.
     *
     * @param mouseEvent the mouse event triggered when the mouse exits the button.
     */
    public void onHandleMouseExited(MouseEvent mouseEvent) {
        ImageView sourceButton = (ImageView) mouseEvent.getSource();
        ButtonEffects.clearHoverEffect(sourceButton);
    }

    /**
     * Finds the position of a specific card in the human player's hand.
     *
     * @param card the card to find
     * @return the position of the card, or -1 if not found
     */
    private Integer findPosCardsHumanPlayer(Card card) {
        for (int i = 0; i < this.humanPlayer.getCardsPlayer().size(); i++) {
            if (this.humanPlayer.getCardsPlayer().get(i).equals(card)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Handles the "Back" button action to show the previous set of cards.
     *
     * @param event the action event
     */
    @FXML
    void onHandleBack(ActionEvent event) {
        ButtonEffects.applyClickEffect(btnBackCard, "/org/example/eiscuno/images/button_back_card_click.png");
        if (this.posInitCardToShow > 0) {
            this.posInitCardToShow--;
            printCardsHumanPlayer();
        }
        turnPlayerStyle(!threadPlayMachine.isHasPlayerPlayed());
    }

    /**
     * Handles the "Next" button action to show the next set of cards.
     *
     * @param event the action event
     */
    @FXML
    void onHandleNext(ActionEvent event) {
        ButtonEffects.applyClickEffect(btnNextCard, "/org/example/eiscuno/images/button_next_card_click.png");
        if (this.posInitCardToShow < this.humanPlayer.getCardsPlayer().size() - 4) {
            this.posInitCardToShow++;
            printCardsHumanPlayer();
        }
        turnPlayerStyle(!threadPlayMachine.isHasPlayerPlayed());

    }

    /**
     * Handles the action of taking a card.
     *
     * @param event the action event
     */
    @FXML
    void onHandleTakeCard(ActionEvent event) {
        if (!threadPlayMachine.isHasPlayerPlayed()) {
            gameUno.eatCard(humanPlayer, 1);
            threadPlayMachine.setHasPlayerPlayed(true);
            setAnimationTakeCard(true, 1);
        }
    }

    /**
     * Animates the process of a player taking cards from the table.
     *
     * @param player A boolean indicating if it's the human player (true) or machine (false).
     * @param numCards The number of cards the player takes in this animation.
     */
    public void setAnimationTakeCard(boolean player, int numCards) {
        cardEat.setVisible(true);
        cardEat.setScaleX(1.0);
        cardEat.setScaleY(1.0);
        cardEat.setTranslateX(0);
        cardEat.setTranslateY(0);

        Runnable onFinishAction = () -> {
            threadPlayMachine.printCardsMachinePlayer();
            printCardsHumanPlayer();
            turnPlayerStyle(!threadPlayMachine.isHasPlayerPlayed());
        };

        SequentialTransition animation = AnimationUtils.createTakeCardAnimation(cardEat, player, numCards, onFinishAction);
        animation.play();
    }

    /**
     * Handles the action of saying "Uno".
     *
     * @param event the action event
     */
    @FXML
    void onHandleUno(ActionEvent event) {
        ButtonEffects.applyHoverEffect(unoButton, "/org/example/eiscuno/images/button_uno_click.png");
        if ((machinePlayer.getCardsPlayer().size() == 1) && (!gameUno.isMachineSingUno())) {
            gameUno.eatCard(machinePlayer, 1);
            System.out.println("MAQUINA COME POR NO CANTAR");
            threadPlayMachine.printCardsMachinePlayer();
        }
        if ((humanPlayer.getCardsPlayer().size() == 1)&&(!gameUno.isMachineSingUno())&&(!gameUno.isPlayerSingUno())) {
            gameUno.setPlayerSingUno(true);
            musicGame.playUnoSound();
            AnimationUtils.unoAnimation(imgUno);
        }

    }

    /**
     * Handles returning to the main menu from the game interface.
     *
     * @param event The action event triggered by clicking the return button.
     * @throws IOException If an error occurs while loading the main menu.
     */
    @FXML
    void onHandleReturnMenuGame(ActionEvent event) throws IOException {
        GameUnoStage.deleteInstance();
        WelcomeGameUnoStage.getInstance();
        mainMusicGame.stopSound();
    }

    /**
     * Displays an alert showing the game result.
     * Shows victory or defeat with animations and sound effects.
     *
     * @param isWinner true if the player wins, false if the player loses.
     */
    public void showResultAlert(boolean isWinner) {
        mainMusicGame.stopSound();
        if (isWinner) {
            for (int k = 0; k<500;k++){
                AnimationUtils.createConfetti(borderPaneGame,borderPaneGame.getLayoutX(),borderPaneGame.getLayoutY());
            }
            winSound.playSound();
            ResultAlerts.winAlert(winSound, "¡¡Felicidades Has Ganado!! \n", "\t  !VICTORIA!\n");
        } else {
            loseSound.playSound();
            ResultAlerts.LoseAlert(loseSound,"Ha ganado la maquina :( \n", "\tDERROTA\n" );
        }
    }

    /**
     * Displays an alert showing the game outcome based on the comparison of remaining cards.
     * Handles three cases: a tie, a win, and a loss, each with animations and sound effects.
     *
     * @param isWinner null for a tie, true if the player wins, false if the player loses.
     */
    public void showResultDeckAlert(Boolean isWinner) {
        mainMusicGame.stopSound();
        if (isWinner == null) {
            for (int k = 0; k<500;k++){
                AnimationUtils.createConfetti(borderPaneGame,borderPaneGame.getLayoutX(),borderPaneGame.getLayoutY());
            }
            winSound.playSound();
            ResultAlerts.winAlert(winSound, "\t     ¡¡Han quedado en Empate,\n ambos tienen el mismo numero de cartas!! \n", "\t    SE ACABARON LAS CARTAS\n");
        } else if (isWinner) {
            for (int k = 0; k<500;k++){
                AnimationUtils.createConfetti(borderPaneGame,borderPaneGame.getLayoutX(),borderPaneGame.getLayoutY());
            }
            winSound.playSound();
            ResultAlerts.winAlert(winSound, "¡¡Felicidades , Has ganado, tienes menos cartas!! \n", "\t\tSE ACABARON LAS CARTAS\n");
        } else {
            loseSound.playSound();
            ResultAlerts.LoseAlert(loseSound,"Has perdido, tu opente tiene menos cartas \n", "\tSE ACABARON LAS CARTAS\n" );
        }
    }

    /**
     * Updates the visual style of the player's cards based on whose turn it is.
     *
     * Applies a white drop shadow effect for an active player's turn.
     * Sets semi-transparent opacity and a default cursor style for an inactive player's turn.
     *
     * @param turnPlayer true if it's the player's turn, false otherwise.
     */
    public void turnPlayerStyle(boolean turnPlayer) {
        String style = turnPlayer
                ? "-fx-effect: dropshadow(gaussian, white, 8, 0, 0, 0)"
                : "-fx-cursor: default; -fx-opacity: 0.7";
        gridPaneCardsPlayer.getChildren()
                .stream()
                .filter(node -> node instanceof ImageView)
                .forEach(node -> node.setStyle(style));
    }


    public void onHandleUnoHover(MouseEvent mouseEvent) {
        ButtonEffects.applyHoverEffect(unoButton, "/org/example/eiscuno/images/button_uno_hover.png");
    }

    public void onHandleUnoExited(MouseEvent mouseEvent) {
        ButtonEffects.applyDefaultEffect(unoButton, "/org/example/eiscuno/images/button_uno.png");
    }

    public void onHandleBtnExitHover(MouseEvent mouseEvent) {
        ButtonEffects.applyHoverEffect(btnExit, "/org/example/eiscuno/images/button_exit_hover.png");
        musicGame.playBtnHoverSound();
    }

    public void onHandleBtnExitExited(MouseEvent mouseEvent) {
        ButtonEffects.applyDefaultEffect(btnExit, "/org/example/eiscuno/images/button_exit.png");
    }

    public void onHandleBtnBackHover(MouseEvent mouseEvent) {
        ButtonEffects.applyHoverEffect(btnBackCard, "/org/example/eiscuno/images/button_back_card_hover.png");
    }

    public void onHandleBtnBackExited(MouseEvent mouseEvent) {
        ButtonEffects.applyDefaultEffect(btnBackCard, "/org/example/eiscuno/images/button_back_card.png");
    }

    public void onHandlebtnNextCardHover(MouseEvent mouseEvent) {
        ButtonEffects.applyHoverEffect(btnNextCard, "/org/example/eiscuno/images/button_next_card_hover.png");
    }

    public void onHandlebtnNextCardExited(MouseEvent mouseEvent) {
        ButtonEffects.applyDefaultEffect(btnNextCard, "/org/example/eiscuno/images/button_next_card.png");
    }

    public void onHanldeDeckCardHover(MouseEvent mouseEvent) {
        ButtonEffects.applyHoverEffect(takeCardButton, "/org/example/eiscuno/cards-uno/deck_of_cards_hover.png");
    }

    public void onHanldeDeckCardExited(MouseEvent mouseEvent) {
        ButtonEffects.applyDefaultEffect(takeCardButton, "/org/example/eiscuno/cards-uno/deck_of_cards.png");
    }

    /**
     * Disables the "Next Card" and "Back Card" buttons for 1.5 seconds to prevent rapid actions.
     * After a delay of 1.5 seconds, re-enables these buttons automatically.
     * This helps ensure proper animation or transitions are completed before interacting with the UI again.
     */
    private void setDisableNextBackBtn() {
        btnNextCard.setDisable(true);
        btnBackCard.setDisable(true);
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(event -> {
            btnNextCard.setDisable(false);
            btnBackCard.setDisable(false);
        });
        delay.play();
    }
}
