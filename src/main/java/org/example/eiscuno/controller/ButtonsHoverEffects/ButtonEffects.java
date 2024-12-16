package org.example.eiscuno.controller.ButtonsHoverEffects;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class ButtonEffects {

    public static void applyHoverEffect(Button button, String hoverImagePath) {
        Image hoverEffectImg = new Image(Objects.requireNonNull(ButtonEffects.class.getResource(hoverImagePath)).toExternalForm());
        ImageInput hoverEffect = new ImageInput(hoverEffectImg, 0, 0);
        button.setEffect(hoverEffect);
    }

    public static void applyDefaultEffect(Button button, String defaultImagePath) {
        applyHoverEffect(button, defaultImagePath);
    }

    public static void applyClickEffect(Button button, String defaultImagePath) {
        applyHoverEffect(button, defaultImagePath);
    }

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

    public static void clearHoverEffect(ImageView button) {
        button.setEffect(null);
    }
}
