package org.example.eiscuno.model.factoryMethod;

import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.unoenum.EISCUnoEnum;

/**
 * Factory class responsible for creating UNO cards based on the card name and URL.
 * Implements the `CardFactory` interface to generate `Card` objects with specified attributes.
 *
 * The factory determines the card's color, type, and value by analyzing the card's name.
 * It handles different UNO cards including number cards (0-9), special cards like SKIP, RESERVE,
 * and wild cards (WILD, TWO_WILD, FOUR_WILD).
 *  * @author Samuel Arenas Valencia
 *  * @author Maria Juliana Saavedra
 *  * @author Juan Esteban Rodriguez
 *  * @version 1.0
 */
public class UnoCardFactory implements CardFactory {

    /**
     * Creates and returns a `Card` object with the provided URL, name, color, type, and value.
     *
     * @param url  A string representing the URL or path to the card's image resource.
     * @param name A string representing the card's name, which contains information about its type, color, and value.
     * @return A new instance of `Card` with properties based on the provided `name` string.
     */
    @Override
    public Card createCard(String url, String name) {

        String color = getCardColor(name);
        String type = getCardType(name);
        String value = getCardValue(name);

        return new Card(url, value, color, type);
    }

    /**
     * Determines the value of the card (0-9) based on the card's name.
     *
     * @param name A string representing the card's name.
     * @return A string representing the card's value (e.g., "0", "1", "2", ..., "9") or null if not a number card.
     */
    private String getCardValue(String name) {
        if (name.endsWith("0")){
            return "0";
        } else if (name.endsWith("1")){
            return "1";
        } else if (name.endsWith("2")){
            return "2";
        } else if (name.endsWith("3")){
            return "3";
        } else if (name.endsWith("4")){
            return "4";
        } else if (name.endsWith("5")){
            return "5";
        } else if (name.endsWith("6")){
            return "6";
        } else if (name.endsWith("7")){
            return "7";
        } else if (name.endsWith("8")){
            return "8";
        } else if (name.endsWith("9")){
            return "9";
        } else {
            return null;
        }

    }

    /**
     * Determines the color of the card based on its name.
     *
     * @param name A string representing the card's name.
     * @return A string representing the card's color (e.g., "RED", "GREEN", "YELLOW", "BLUE") or null if not found.
     */
    private String getCardColor(String name){
        if(name.startsWith("GREEN") || name.endsWith("GREEN")){
            return "GREEN";
        } else if(name.startsWith("YELLOW") || name.endsWith("YELLOW")){
            return "YELLOW";
        } else if(name.startsWith("BLUE") || name.endsWith("BLUE")){
            return "BLUE";
        } else if(name.startsWith("RED") || name.endsWith("RED")){
            return "RED";
        } else {
            return null;
        }
    }

    /**
     * Determines the type of the card (e.g., WILD, SKIP, RESERVE) based on its name.
     *
     * @param name A string representing the card's name.
     * @return A string representing the type of the card (e.g., "WILD", "SKIP", "RESERVE") or null if not a special type.
     */
    private String getCardType(String name){
        if(name.startsWith("TWO_WILD") || name.endsWith("TWO_WILD")){
            return "TWO_WILD";
        } else if(name.startsWith("FOUR_WILD") || name.endsWith("FOUR_WILD")){
            return "FOUR_WILD";
        } else if(name.startsWith("SKIP") || name.endsWith("SKIP")){
            return "SKIP";
        } else if(name.startsWith("RESERVE") || name.endsWith("RESERVE")){
            return "RESERVE";
        } else if(name.startsWith("WILD") || name.endsWith("WILD")){
            return "WILD";
        }else {
            return null;
        }
    }
}
