package org.example.eiscuno.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.machine.ThreadPlayMachine;
import org.example.eiscuno.model.machine.ThreadShowResultGame;
import org.example.eiscuno.model.machine.ThreadSingUNOMachine;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private Button takeCardButton;

    private Player humanPlayer;
    private Player machinePlayer;
    private Deck deck;
    private Table table;
    private GameUno gameUno;
    private int posInitCardToShow;
    private boolean startGame;

    private ThreadSingUNOMachine threadSingUNOMachine;
    private ThreadPlayMachine threadPlayMachine;
    private ThreadShowResultGame threadShowResultGame;

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


        threadSingUNOMachine = new ThreadSingUNOMachine(this.humanPlayer, this.machinePlayer, this.gameUno, this.gridPaneCardsPlayer, this);
        Thread t = new Thread(threadSingUNOMachine, "ThreadSingUNO");
        t.start();


        threadPlayMachine = new ThreadPlayMachine(this.table, this.machinePlayer, this.humanPlayer, this.tableImageView, this.gameUno, this.gridPaneCardsMachine, this);
        threadPlayMachine.start();
        threadPlayMachine.printCardsMachinePlayer();

        threadShowResultGame = new ThreadShowResultGame( this.gridPaneCardsMachine,this.gridPaneCardsPlayer, this, threadPlayMachine);
        Thread threadShowResult = new Thread(threadShowResultGame, "ThreadSingUNO");
        threadShowResult.start();
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
        this.startGame = true;
        cardEat.setVisible(false);
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
    public void printCardsHumanPlayer() {
        this.gridPaneCardsPlayer.getChildren().clear();
        Card[] currentVisibleCardsHumanPlayer = this.gameUno.getCurrentVisibleCardsHumanPlayer(this.posInitCardToShow);
        for (int i = 0; i < currentVisibleCardsHumanPlayer.length; i++) {
            Card card = currentVisibleCardsHumanPlayer[i];
            ImageView cardImageView = card.getCard();
            cardImageView.getStyleClass().add("card-image");
            cardImageView.setOnMouseClicked((MouseEvent event) -> {
                System.out.println("hiciste click");
                selectedCard(card, cardImageView);
            });
            if(startGame) {
                setAnimation(i, cardImageView);
            }else{
                this.gridPaneCardsPlayer.add(cardImageView, i, 0);
            }
        }
        startGame = false;
    }

    private void setAnimation(int finalI, ImageView cardImageView) {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.2 * finalI));
        delay.setOnFinished(event -> {
            gridPaneCardsPlayer.add(cardImageView, finalI, 0);
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

    private void selectedCard(Card card, ImageView cardImageView) {
        if (gameUno.isCardPlayable(card) && !threadPlayMachine.isHasPlayerPlayed()) {
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
            setAnimationTakeCard(false,4);
            gameUno.eatCard(player, 4);
            setVisibilityButtonsChooseColor();
        }else if(card.getType().equals("TWO_WILD")) {
            setAnimationTakeCard(false,2);
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
            gameUno.setColorChoose("BLUE");
        }else if(Objects.equals(buttonId, "btnRed")){
            gameUno.setColorChoose("RED");
        }else if(Objects.equals(buttonId, "btnYellow")){
            gameUno.setColorChoose("YELLOW");
        }else if(Objects.equals(buttonId, "btnGreen")){
            gameUno.setColorChoose("GREEN");
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
        if(gameUno.isPlayerSingUno()){
            gameUno.setPlayerSingUno(false);
        }else if(!threadPlayMachine.isHasPlayerPlayed()){
            gameUno.eatCard(humanPlayer,1);
            threadPlayMachine.setHasPlayerPlayed(true);
            setAnimationTakeCard(true,1);
        }
    }

    public void setAnimationTakeCard(boolean player, int numCards){
        cardEat.setVisible(true);

        SequentialTransition animation = new SequentialTransition();

        for (int i = 0; i < numCards; i++) {
            TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(0.5), cardEat);
            if (player) {
                moveTransition.setToX(250 - cardEat.getLayoutX());
                moveTransition.setToY(300 - cardEat.getLayoutY());
            } else {
                moveTransition.setToX(300 - cardEat.getLayoutX());
                moveTransition.setToY(-250 - cardEat.getLayoutY());
            }

            ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.25), cardEat);
            scaleUp.setToX(1.3);
            scaleUp.setToY(1.3);

            ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.25), cardEat);
            scaleDown.setToX(0);
            scaleDown.setToY(0);

            TranslateTransition moveTransition2 = new TranslateTransition(Duration.seconds(0.1), cardEat);
            moveTransition2.setToX(0);
            moveTransition2.setToY(0);

            animation.getChildren().addAll(scaleUp, moveTransition, scaleDown, moveTransition2);
        }

        animation.setOnFinished(event -> {
            threadPlayMachine.printCardsMachinePlayer();
            printCardsHumanPlayer();
        });

        animation.play();
    }

    /**
     * Handles the action of saying "Uno".
     *
     * @param event the action event
     */
    @FXML
    void onHandleUno(ActionEvent event) {
        if((machinePlayer.getCardsPlayer().size() == 1)&&(!gameUno.isMachineSingUno())){
            gameUno.setPlayerSingUno(true);
            gameUno.eatCard(machinePlayer,1);
            threadPlayMachine.printCardsMachinePlayer();
            gameUno.setPlayerSingUno(false);
            System.out.println("maquina come una por no decir uno");
        }
        if(humanPlayer.getCardsPlayer().size() == 1){
            gameUno.setPlayerSingUno(true);
            System.out.println("digo uno");
        }
        // Implement logic to handle Uno event here
    }

    @FXML
    void onHandleReturnMenuGame(ActionEvent event) throws IOException {
        GameUnoStage.deleteInstance();
        WelcomeGameUnoStage.getInstance();
    }

    public void showResultAlert(boolean isWinner) {
        threadPlayMachine.setHasPlayerPlayed(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado del Juego");
        alert.setHeaderText(null);
        Button btnAceptar = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnAceptar.setOnAction(event -> {
            threadShowResultGame.stopThread();
            GameUnoStage.deleteInstance();
            try {
                WelcomeGameUnoStage.getInstance();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        alert.setContentText(isWinner ? "¡Ganaste!" : "Perdiste. ¡Mejor suerte la próxima vez!");
        alert.showAndWait();
    }


}
