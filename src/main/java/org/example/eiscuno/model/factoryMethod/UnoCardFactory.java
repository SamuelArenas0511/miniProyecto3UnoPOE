package org.example.eiscuno.model.factoryMethod;

import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.unoenum.EISCUnoEnum;

public class UnoCardFactory implements CardFactory {

    @Override
    public Card createCard(String url, String name) {

        String color = getCardColor(name);
        String type = getCardType(name);
        String value = getCardValue(name);

        return new Card(url, value, color, type);
    }

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
