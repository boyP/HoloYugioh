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
    private static final String TYPE = "type";
    private static final String FAMILY = "family";
    private static final String ATK = "atk";
    private static final String DEF = "def";
    private static final String LEVEL = "level";
    private static final String PROPERTY = "property";

    private String name;
    private String description;
    private String cardType;
    private String type;
    private String family;
    private int atk;
    private int def;
    private int level;
    private String property;
    private long position;

    public Card(int position) {
        this.name = "";
        this.description = "";
        this.cardType = "";
        this.type = "";
        this.family = "";
        this.atk = 0;
        this.def = 0;
        this.level = 0;
        this.property = "";
        this.position = position;
    }

    public Card(String cardName, String cardType, long position) {
        this.name = cardName;
        this.description = "";
        this.cardType = cardType;
        this.type = "";
        this.family = "";
        this.atk = 0;
        this.def = 0;
        this.level = 0;
        this.property = "";
        this.position = position;
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
        this.type = data.getString(TYPE);
        this.family = data.getString(FAMILY);
        this.atk = data.isNull(ATK)     ? 0 : data.getInt(ATK);
        this.def = data.isNull(DEF)     ? 0 : data.getInt(DEF);
        this.level = data.isNull(LEVEL) ? 0 : data.getInt(LEVEL);
        this.property = data.getString(PROPERTY);
    }

    public String getType() {
        return type;
    }

    public String getFamily() {
        return family;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getLevel() {
        return level;
    }

    public String getProperty() {
        return property;
    }

    public long getPosition() {
        return position;
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
