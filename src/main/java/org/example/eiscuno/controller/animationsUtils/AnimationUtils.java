package org.example.eiscuno.controller.animationsUtils;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class AnimationUtils {

    public static SequentialTransition createTakeCardAnimation(Node card, boolean isPlayer, int numCards, Runnable onFinishAction) {
        SequentialTransition animation = new SequentialTransition();

        for (int i = 0; i < numCards; i++) {
            TranslateTransition moveTransition = createMoveTransition(card, isPlayer);
            ScaleTransition scaleUp = createScaleTransition(card, Duration.seconds(0.25), 1.3);
            ScaleTransition scaleDown = createScaleTransition(card, Duration.seconds(0.25), 0.0);
            TranslateTransition resetPosition = createResetTransition(card);

            animation.getChildren().addAll(scaleUp, moveTransition, scaleDown, resetPosition);
        }

        animation.setOnFinished(event -> {
            if (onFinishAction != null) {
                onFinishAction.run();
            }
        });

        return animation;
    }

    private static TranslateTransition createMoveTransition(Node card, boolean isPlayer) {
        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(0.5), card);
        if (isPlayer) {
            moveTransition.setToX(250 - card.getLayoutX());
            moveTransition.setToY(300 - card.getLayoutY());
        } else {
            System.out.println("si entre aqui compadre");
            moveTransition.setToX(300 - card.getLayoutX());
            moveTransition.setToY(-250 - card.getLayoutY());
        }
        return moveTransition;
    }

    private static ScaleTransition createScaleTransition(Node card, Duration duration, double scale) {
        ScaleTransition scaleTransition = new ScaleTransition(duration, card);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        return scaleTransition;
    }

    private static TranslateTransition createResetTransition(Node card) {
        TranslateTransition resetTransition = new TranslateTransition(Duration.seconds(0.1), card);
        resetTransition.setToX(0);
        resetTransition.setToY(0);
        return resetTransition;
    }

    public static void applyTableEffect(ImageView table, String color) {
        switch (color.toUpperCase()) {
            case "BLUE" -> table.setStyle("-fx-effect: dropshadow(gaussian, blue, 20, 0, 0, 0)");
            case "RED" -> table.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0, 0, 0)");
            case "YELLOW" -> table.setStyle("-fx-effect: dropshadow(gaussian, yellow, 20, 0, 0, 0)");
            case "GREEN" -> table.setStyle("-fx-effect: dropshadow(gaussian, green, 20, 0, 0, 0)");
            default -> throw new IllegalArgumentException("Invalid color: " + color);
        }
    }

    public static void setDealAnimation(int index, ImageView cardImageView, GridPane gridPane, Runnable onAnimationComplete) {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.2 * index));
        delay.setOnFinished(event -> {
            gridPane.add(cardImageView, index, 0);
            animateCardDeal(cardImageView, onAnimationComplete);
        });
        delay.play();
    }

    private static void animateCardDeal(ImageView cardImageView, Runnable onAnimationComplete) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), cardImageView);
        scaleTransition.setFromX(1.5);
        scaleTransition.setFromY(1.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.setOnFinished(event -> {
            cardImageView.setMouseTransparent(false);
            if (onAnimationComplete != null) {
                onAnimationComplete.run();
            }
        });
        scaleTransition.play();
    }

    public static void animateSkipCard(ImageView skipCardImageView) {
        skipCardImageView.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.7), skipCardImageView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.8), skipCardImageView);
        scaleUp.setFromX(1.0);
        scaleUp.setFromY(1.0);
        scaleUp.setToX(1.5);
        scaleUp.setToY(1.5);

        ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.8), skipCardImageView);
        scaleDown.setFromX(1.5);
        scaleDown.setFromY(1.5);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);


        DropShadow glowEffect = new DropShadow();
        glowEffect.setColor(Color.GOLD);
        glowEffect.setRadius(10);
        glowEffect.setSpread(0.5);

        skipCardImageView.setEffect(glowEffect);

        SequentialTransition animation = new SequentialTransition(
                fadeIn,
                scaleUp,
                scaleDown
        );

        animation.setOnFinished(event -> {
            skipCardImageView.setEffect(null);
            skipCardImageView.setOpacity(0);
        });

        animation.play();
    }

    public static void animateReverseCard(ImageView reverseCardImageView) {
        System.out.println("animacion de reverse ");
        reverseCardImageView.setOpacity(100);

        RotateTransition rotate = new RotateTransition(Duration.seconds(0.4), reverseCardImageView);
        rotate.setByAngle(180);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.8), reverseCardImageView);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.7), reverseCardImageView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        DropShadow glowEffect = new DropShadow();
        glowEffect.setColor(Color.GOLD);
        glowEffect.setRadius(10);
        glowEffect.setSpread(0.5);

        reverseCardImageView.setEffect(glowEffect);


        SequentialTransition animation = new SequentialTransition(
                rotate,
                scaleTransition,
                fadeOut
        );

        animation.setOnFinished(event -> {
            reverseCardImageView.setRotate(0);
            reverseCardImageView.setOpacity(0);
        });

        animation.play();
    }

    public static void unoAnimation(ImageView unoImageView){
        unoImageView.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), unoImageView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.5), unoImageView);
        scaleUp.setFromX(1.0);
        scaleUp.setFromY(1.0);
        scaleUp.setToX(1.5);
        scaleUp.setToY(1.5);

        ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.7), unoImageView);
        scaleDown.setFromX(1.6);
        scaleDown.setFromY(1.6);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);


        DropShadow glowEffect = new DropShadow();
        glowEffect.setColor(Color.WHITE);
        glowEffect.setRadius(20);
        glowEffect.setSpread(0.4);

        unoImageView.setEffect(glowEffect);

        SequentialTransition animation = new SequentialTransition(
                fadeIn,
                scaleUp,
                scaleDown
        );

        animation.setOnFinished(event -> {
            unoImageView.setEffect(null);
            unoImageView.setOpacity(0);
        });

        animation.play();
    }

    public static void createConfetti(Pane pane, double startX, double startY) {
        Random random = new Random();

        Rectangle confetti = new Rectangle(random.nextInt(5)+2,random.nextInt(5)+2, getRandomColor());
        confetti.setLayoutX(startX + random.nextInt(2000)); // Posición inicial cercana al TextField
        confetti.setLayoutY(startY-20);

        pane.getChildren().add(confetti);

        double paneHeight = pane.getHeight();

        TranslateTransition transition = new TranslateTransition(Duration.seconds(random.nextDouble(7)+2), confetti);
        transition.setByY(paneHeight - startY); // Caída hasta el final de la pantalla
        transition.setByX(random.nextInt(200) - 25); // Movimiento lateral aleatorio
        transition.setInterpolator(Interpolator.LINEAR); // Movimiento suave y constante
        transition.setOnFinished(e -> pane.getChildren().remove(confetti)); // Eliminar confeti cuando caiga
        transition.play();
    }

    /**
     * Generates a random color by creating an RGB color with random values for red, green, and blue components.
     *
     * @return A randomly generated Color object.
     */
    private static Color getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
