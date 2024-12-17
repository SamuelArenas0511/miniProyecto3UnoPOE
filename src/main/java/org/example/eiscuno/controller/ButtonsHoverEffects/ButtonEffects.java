package org.example.eiscuno.controller.ButtonsHoverEffects;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * The `ButtonEffects` class provides static methods to apply visual effects to JavaFX Buttons and ImageViews.
 * It supports hover, click, and default effects, enhancing the visual interactions of UI components.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
public class ButtonEffects {

    /**
     * Applies a hover effect to a Button using an image as a visual effect.
     *
     * @param button         The `Button` to which the hover effect will be applied.
     * @param hoverImagePath The path to the image used for the hover effect.
     */
    public static void applyHoverEffect(Button button, String hoverImagePath) {
        Image hoverEffectImg = new Image(Objects.requireNonNull(ButtonEffects.class.getResource(hoverImagePath)).toExternalForm());
        ImageInput hoverEffect = new ImageInput(hoverEffectImg, 0, 0);
        button.setEffect(hoverEffect);
    }

    /**
     * Applies the default visual effect to a Button by using the specified default image path.
     *
     * @param button         The `Button` to which the default effect will be applied.
     * @param defaultImagePath The path to the image for the default effect.
     */
    public static void applyDefaultEffect(Button button, String defaultImagePath) {
        applyHoverEffect(button, defaultImagePath);
    }

    /**
     * Applies a click effect to a Button by using the provided default image path.
     *
     * @param button         The `Button` to which the click effect will be applied.
     * @param defaultImagePath The path to the image for the click effect.
     */
    public static void applyClickEffect(Button button, String defaultImagePath) {
        applyHoverEffect(button, defaultImagePath);
    }

    /**
     * Applies a hover effect to an `ImageView` button by adding a `DropShadow` effect.
     *
     * @param button The `ImageView` representing the button to which the hover effect will be applied.
     */
    public static void applyHoverEffect(ImageView button) {
        String buttonId = button.getId();
        DropShadow dropShadow = new DropShadow();

        switch (buttonId) {
            case "btnBlue" -> dropShadow.setColor(Color.rgb(88, 87, 253));
            case "btnRed" -> dropShadow.setColor(Color.rgb(255, 85, 85));
            case "btnYellow" -> dropShadow.setColor(Color.rgb(255, 170, 0));
            case "btnGreen" -> dropShadow.setColor(Color.rgb(85, 170, 85));
            default -> throw new IllegalArgumentException("Invalid button ID: " + buttonId);
        }

        dropShadow.setRadius(8);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.2);
        colorAdjust.setSaturation(0.3);
        colorAdjust.setContrast(0.2);
        colorAdjust.setInput(dropShadow);

        button.setEffect(colorAdjust);
    }

    /**
     * Clears the hover effect applied to an `ImageView` button.
     *
     * @param button The `ImageView` representing the button from which the hover effect will be removed.
     */
    public static void clearHoverEffect(ImageView button) {
        button.setEffect(null);
    }
}
