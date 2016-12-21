package game;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The card class is used to represent a single card in the Yu-Gi-Oh game
 */
public class Card implements Parcelable {

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
    public Card(String cardName, String description, String cardType) {
        this.name = cardName;
        this.description = description;
        this.cardType = cardType;
    }

    private Card(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.name = data[0];
        this.cardType = data[1];
        this.description = data[2];
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.name, this.cardType, this.description
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
