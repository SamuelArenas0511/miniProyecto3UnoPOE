package org.example.eiscuno.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.machine.ThreadPlayMachine;
import org.example.eiscuno.model.machine.ThreadSingUNOMachine;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;

import java.awt.*;
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

    private Player humanPlayer;
    private Player machinePlayer;
    private Deck deck;
    private Table table;
    private GameUno gameUno;
    private int posInitCardToShow;
    private String colorChoose;

    private ThreadSingUNOMachine threadSingUNOMachine;
    private ThreadPlayMachine threadPlayMachine;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        initVariables();
        setBackgroundImage();
        this.gameUno.startGame();
        printCardTable();
        printCardsHumanPlayer();
        setVisibilityButtonsChooseColor();


        threadSingUNOMachine = new ThreadSingUNOMachine(this.humanPlayer.getCardsPlayer());
        Thread t = new Thread(threadSingUNOMachine, "ThreadSingUNO");
        t.start();


        threadPlayMachine = new ThreadPlayMachine(this.table, this.machinePlayer, this.tableImageView, this.gameUno, this.gridPaneCardsMachine);
        threadPlayMachine.start();
        threadPlayMachine.printCardsMachinePlayer();
    }

    private void setVisibilityButtonsChooseColor() {
        pnBtnChooseColor.setVisible(!pnBtnChooseColor.isVisible());
    }

    /**
     * Initializes the variables for the game.
     */
    private void initVariables() {
        this.humanPlayer = new Player("HUMAN_PLAYER");
        this.machinePlayer = new Player("MACHINE_PLAYER");
        this.deck = new Deck();
        this.table = new Table();
        this.gameUno = new GameUno(this.humanPlayer, this.machinePlayer, this.deck, this.table);
        this.posInitCardToShow = 0;
        this.colorChoose = "";
    }
    /**
     * Set background Image in the game
     */
    public void setBackgroundImage() {
        Image backgroundImage = new Image(getClass().getResource("/org/example/eiscuno/images/backgroud_game.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        borderPaneGame.setBackground(new Background(background));
    }


    private void printCardTable() {
        tableImageView.setImage(table.getCurrentCardOnTheTable().getImage());
    }
    /**
     * Prints the human player's cards on the grid pane.
     */
    private void printCardsHumanPlayer() {
        this.gridPaneCardsPlayer.getChildren().clear();
        Card[] currentVisibleCardsHumanPlayer = this.gameUno.getCurrentVisibleCardsHumanPlayer(this.posInitCardToShow);
        for (int i = 0; i < currentVisibleCardsHumanPlayer.length; i++) {
            Card card = currentVisibleCardsHumanPlayer[i];
            ImageView cardImageView = card.getCard();
            cardImageView.getStyleClass().add("card-image");
            cardImageView.setOnMouseClicked((MouseEvent event) -> {
                selectedCard(card);
            });
            this.gridPaneCardsPlayer.add(cardImageView, i, 0);
        }
    }

    private void selectedCard(Card card){
        if (Objects.equals(card.getColor(), table.getCurrentCardOnTheTable().getColor()) ||
                Objects.equals(card.getValue(), table.getCurrentCardOnTheTable().getValue())
                || Objects.equals(card.getType(), "WILD") || Objects.equals(card.getType(), "FOUR_WILD") || Objects.equals(card.getColor(), colorChoose)) {
            gameUno.playCard(card);
            tableImageView.setImage(card.getImage());
            humanPlayer.removeCard(findPosCardsHumanPlayer(card));
            if(gameUno.isEspecialCard(card)){
                playEspecialCard(machinePlayer, card);
                threadPlayMachine.printCardsMachinePlayer();
            }else{
                threadPlayMachine.setHasPlayerPlayed(true);
            }
            printCardsHumanPlayer();
        }
    }

    private void playEspecialCard(Player player, Card card) {
        if(card.getType().equals("FOUR_WILD")) {;
            gameUno.eatCard(player, 4);
            setVisibilityButtonsChooseColor();
        }else if(card.getType().equals("TWO_WILD")) {
            gameUno.eatCard(player, 2);
        }else if (card.getType().equals("WILD")) {
            setVisibilityButtonsChooseColor();
        }
    }

    public void onHandleClickChooseColor(MouseEvent actionEvent) {
        setVisibilityButtonsChooseColor();
        ImageView sourceButton = (ImageView) actionEvent.getSource();
        String buttonId = sourceButton.getId();
        if(Objects.equals(buttonId, "btnBlue")){
            colorChoose = "BLUE";
        }else if(Objects.equals(buttonId, "btnRed")){
            colorChoose = "RED";
        }else if(Objects.equals(buttonId, "btnYellow")){
            colorChoose = "YELLOW";
        }else if(Objects.equals(buttonId, "btnGreen")){
            colorChoose = "GREEN";
        }
    }

    public void onHandleMouseHover(MouseEvent mouseEvent) {
        ImageView sourceButton = (ImageView) mouseEvent.getSource();
        String buttonId = sourceButton.getId();
        DropShadow dropShadow = new DropShadow();
        if(Objects.equals(buttonId, "btnBlue")){
            dropShadow.setColor(Color.rgb(88, 87, 253));
        }else if(Objects.equals(buttonId, "btnRed")){
            dropShadow.setColor(Color.rgb(255, 85, 85));
        }else if(Objects.equals(buttonId, "btnYellow")){
            dropShadow.setColor(Color.rgb(255, 170, 0));
        }else if(Objects.equals(buttonId, "btnGreen")){
            dropShadow.setColor(Color.rgb(85, 170, 85));
        }
        dropShadow.setRadius(8);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.2);
        colorAdjust.setSaturation(0.3);
        colorAdjust.setContrast(0.2);
        colorAdjust.setInput(dropShadow);
        sourceButton.setEffect(colorAdjust);
    }

    public void onHandleMouseExited(MouseEvent mouseEvent) {
        ImageView sourceButton = (ImageView) mouseEvent.getSource();
        sourceButton.setEffect(null);
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
        if (this.posInitCardToShow > 0) {
            this.posInitCardToShow--;
            printCardsHumanPlayer();
        }
    }

    /**
     * Handles the "Next" button action to show the next set of cards.
     *
     * @param event the action event
     */
    @FXML
    void onHandleNext(ActionEvent event) {
        if (this.posInitCardToShow < this.humanPlayer.getCardsPlayer().size() - 4) {
            this.posInitCardToShow++;
            printCardsHumanPlayer();
        }
    }

    /**
     * Handles the action of taking a card.
     *
     * @param event the action event
     */
    @FXML
    void onHandleTakeCard(ActionEvent event) {
        gameUno.eatCard(humanPlayer,1);
        printCardsHumanPlayer();
        threadPlayMachine.setHasPlayerPlayed(true);
    }

    /**
     * Handles the action of saying "Uno".
     *
     * @param event the action event
     */
    @FXML
    void onHandleUno(ActionEvent event) {
        // Implement logic to handle Uno event here
    }

    @FXML
    void onHandleReturnMenuGame(ActionEvent event) throws IOException {
        GameUnoStage.deleteInstance();
        WelcomeGameUnoStage.getInstance();
    }



}
