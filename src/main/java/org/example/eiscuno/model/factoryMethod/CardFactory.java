package org.example.eiscuno.model.factoryMethod;

import org.example.eiscuno.model.card.Card;

public interface CardFactory {
    Card createCard(String url, String value, String color, String type);
}
