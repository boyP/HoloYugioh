package game;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The card class is used to represent a single card in the Yu-Gi-Oh game
 */
public class Card {

    private static final String NAME = "name";
    private static final String TEXT = "text";
    private static final String CARD_TYPE = "card_type";

    private String name;
    private String description;
    private String cardType;

    public Card() {
        this.name = "";
        this.description = "";
        this.cardType = "";
    }
    public Card(String cardName, String description) {
        this.name = name;
        this.description = description;
        this.cardType = "";
    }

    public void createCardFromJSON(JSONObject data) throws JSONException {
        this.name = data.getString(NAME);
        this.description = data.getString(TEXT);
        this.cardType = data.getString(CARD_TYPE);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCardType() {
        return cardType;
    }


}
