package org.example.eiscuno.model.factoryMethod;

import org.example.eiscuno.model.card.Card;

public class UnoCardFactory implements CardFactory{
    @Override
    public Card createCard(String url, String value, String color, String type) {
        return new Card(url, value, color, type);
    }
}
