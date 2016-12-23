package firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Constants.Constants;
import game.Card;

public class GameState {

    private static DatabaseReference mDatabase;

    private static final String TOP_REFERENCE = "Game";
    private static final String PLAYERS = "Players";
    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";

    private static final String LIFE_POINTS = "Life Points";

    private static final String FIELD = "Field";
    private static final String MONSTER = "Monster";
    private static final String SPELL_TRAP = "Spell-Trap";
    private static final String PENDULUM_RIGHT = "Pendulum Left";
    private static final String PENDULUM_LEFT = "Pendulum Right";
    private static final String FIELD_SPELL = "Field Spell";

    public static final String CARD1 = "Card1";
    public static final String CARD2 = "Card2";
    public static final String CARD3 = "Card3";
    public static final String CARD4 = "Card4";
    public static final String CARD5 = "Card5";

    public static final String NAME = "Name";
    public static final String POSITION = "Position";

    public static void initializePlayer(int playerNum) {
        mDatabase = FirebaseDatabase.getInstance().getReference(TOP_REFERENCE);

        DatabaseReference player;
        if (playerNum == 1) {
            player = mDatabase.child(PLAYERS).child(PLAYER_1);
        }
        else {
            player = mDatabase.child(PLAYERS).child(PLAYER_2);
        }

        // Initialize Life Points
        player.child(LIFE_POINTS).setValue(Constants.INITIAL_LIFE_POINTS);

        // Set field spells
        mDatabase.child(FIELD_SPELL).child(NAME).setValue("");
        mDatabase.child(FIELD_SPELL).child(POSITION).setValue(0);

        // Player field
        DatabaseReference field = player.child(FIELD);

        // Pendulum
        field.child(PENDULUM_LEFT).child(NAME).setValue("");
        field.child(PENDULUM_LEFT).child(POSITION).setValue(0);
        field.child(PENDULUM_RIGHT).child(NAME).setValue("");
        field.child(PENDULUM_RIGHT).child(POSITION).setValue(0);

        // Monster
        DatabaseReference monster = field.child(MONSTER);
        monster.child(CARD1).child(NAME).setValue("");
        monster.child(CARD1).child(POSITION).setValue(0);

        monster.child(CARD2).child(NAME).setValue("");
        monster.child(CARD2).child(POSITION).setValue(0);

        monster.child(CARD3).child(NAME).setValue("");
        monster.child(CARD3).child(POSITION).setValue(0);

        monster.child(CARD4).child(NAME).setValue("");
        monster.child(CARD4).child(POSITION).setValue(0);

        monster.child(CARD5).child(NAME).setValue("");
        monster.child(CARD5).child(POSITION).setValue(0);

        // Spell
        DatabaseReference spell = field.child(SPELL_TRAP);
        spell.child(CARD1).child(NAME).setValue("");
        spell.child(CARD1).child(POSITION).setValue(0);

        spell.child(CARD2).child(NAME).setValue("");
        spell.child(CARD2).child(POSITION).setValue(0);

        spell.child(CARD3).child(NAME).setValue("");
        spell.child(CARD3).child(POSITION).setValue(0);

        spell.child(CARD4).child(NAME).setValue("");
        spell.child(CARD4).child(POSITION).setValue(0);

        spell.child(CARD5).child(NAME).setValue("");
        spell.child(CARD5).child(POSITION).setValue(0);
    }

    public static DatabaseReference getMonsterCardPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD).child(MONSTER);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(FIELD).child(MONSTER);
        }
    }

    public static DatabaseReference getSpellCardPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD).child(SPELL_TRAP);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(FIELD).child(SPELL_TRAP);
        }
    }

    public static DatabaseReference getFieldSpellPath() {
        return mDatabase.child(FIELD_SPELL);
    }

    public static DatabaseReference getPendulumLeftPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD).child(PENDULUM_LEFT);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(FIELD).child(PENDULUM_LEFT);
        }
    }

    public static DatabaseReference getPendulumRightPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD).child(PENDULUM_RIGHT);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(FIELD).child(PENDULUM_RIGHT);
        }
    }

    /**
     * Writes new life points to current player
     * @param newLifePoints : New life points of player
     */
    public static void writeLifePoints(int newLifePoints) {
        mDatabase.child(PLAYERS).child(PLAYER_1).child(LIFE_POINTS).setValue(newLifePoints);
    }

    /**
     * Writes the card to the firebase database at the fieldPosition to the positionToChange
     * @param card : Card to be changed
     * @param fieldPosition : Position of card on field
     * @param positionToChange : New position of card
     */
    public static void writeChangeCardPosition(Card card, int fieldPosition, int positionToChange) {
        DatabaseReference path = getFirebasePathFromCardPosition(fieldPosition);
        path.child(NAME).setValue(card.getName());
        path.child(POSITION).setValue(positionToChange);
    }

    /**
     * Places the card to the firebase database at the fieldPosition
     * @param card : Card to be changed
     * @param fieldPosition : Position of card on field
     */
    public static void writePlaceCard(Card card, int fieldPosition, int positionToChange) {
        DatabaseReference path = getFirebasePathFromCardPosition(fieldPosition);
        path.child(NAME).setValue(card.getName());
        path.child(POSITION).setValue(positionToChange);
    }

    /**
     * Removes the card from the firebase database at the fieldPosition
     * @param card : Card to be changed
     * @param fieldPosition : Position of card on field
     */
    public static void writeRemoveCard(Card card, int fieldPosition) {
        DatabaseReference path = getFirebasePathFromCardPosition(fieldPosition);
        path.child(NAME).setValue("");
        path.child(POSITION).setValue(Constants.DESTROY_POSITION);
    }

    private static DatabaseReference getFirebasePathFromCardPosition(int fieldPosition) {

        DatabaseReference field = mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD);
        DatabaseReference path;

        switch (fieldPosition) {
            //**************//
            // Monster Zone
            //**************//
            case 1:
                path = field.child(MONSTER).child(CARD1);
                break;
            case 2:
                path = field.child(MONSTER).child(CARD2);
                break;
            case 3:
                path = field.child(MONSTER).child(CARD3);
                break;
            case 4:
                path = field.child(MONSTER).child(CARD4);
                break;
            case 5:
                path = field.child(MONSTER).child(CARD5);
                break;

            //*****************//
            // Spell/Trap Zone
            //*****************//
            case 6:
                path = field.child(SPELL_TRAP).child(CARD1);
                break;
            case 7:
                path = field.child(SPELL_TRAP).child(CARD2);
                break;
            case 8:
                path = field.child(SPELL_TRAP).child(CARD3);
                break;
            case 9:
                path = field.child(SPELL_TRAP).child(CARD4);
                break;
            case 10:
                path = field.child(SPELL_TRAP).child(CARD5);
                break;

            //*************//
            // Extra Zones
            //*************//
            case 11:
                path = mDatabase.child(FIELD_SPELL);
                break;
            case 12:
                path = field.child(PENDULUM_LEFT);
                break;
            case 13:
                path = field.child(PENDULUM_RIGHT);
                break;

            default:
                path = null;
                break;
        }

        return path;
    }
}
