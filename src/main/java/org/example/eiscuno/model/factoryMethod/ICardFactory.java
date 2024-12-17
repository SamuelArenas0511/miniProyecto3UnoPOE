package org.example.eiscuno.model.factoryMethod;

import org.example.eiscuno.model.card.Card;

public interface ICardFactory {
    Card createCard(String url,String name);
}
