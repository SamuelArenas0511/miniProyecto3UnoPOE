package org.example.eiscuno.model.card;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Represents a card in the Uno game.
 */
public class Card {
    private String url;
    private String value;
    private String color;
    private String type;
    private Image image;
    private ImageView cardImageView;

    /**
     * Constructs a Card with the specified image URL and name.
     *
     * @param url the URL of the card image
     * @param value of the card
     */
    public Card(String url, String value, String color, String type) {
        this.url = url;
        this.value = value;
        this.color = color;
        this.type = type;
        this.image = new Image(String.valueOf(getClass().getResource(url)));
        this.cardImageView = createCardImageView();
    }

    public Card(String value, String color, String type) {
        this.value = value;
        this.color = color;
        this.type = type;
    }

    /**
     * Creates and configures the ImageView for the card.
     *
     * @return the configured ImageView of the card
     */
    private ImageView createCardImageView() {
        ImageView card = new ImageView(this.image);
        card.setY(0);
        card.setFitHeight(164);
        card.setFitWidth(115);
        return card;
    }

    /**
     * Gets the ImageView representation of the card.
     *
     * @return the ImageView of the card
     */
    public ImageView getCard() {
        return cardImageView;
    }

    /**
     * Gets the image of the card.
     *
     * @return the Image of the card
     */
    public Image getImage() {
        return image;
    }

    public String getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public String getType() {return type;}
}
