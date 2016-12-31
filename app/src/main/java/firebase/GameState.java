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

    private static String CURRENT_PLAYER = "Player1";
    private static int CURRENT_PLAYER_NUM = 1;

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

    /**
     * Initializes firebase for a new game
     */
    public static void initializePlayer(int playerNum) {
        mDatabase = FirebaseDatabase.getInstance().getReference(TOP_REFERENCE);

        CURRENT_PLAYER_NUM = playerNum;
        CURRENT_PLAYER = playerNum == 1 ? PLAYER_1 : PLAYER_2;

        DatabaseReference player = mDatabase.child(PLAYERS).child(CURRENT_PLAYER);

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

    public static int getPlayer() {
        return CURRENT_PLAYER_NUM;
    }

    public static int getOpponentPlayer() {
        return CURRENT_PLAYER_NUM == 1 ? 2 : 1;
    }

    public static DatabaseReference getLifePointsPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(LIFE_POINTS);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(LIFE_POINTS);
        }
    }

    /**
     * Get the Monster card path in the firebase database for reading and writing given a player
     * @param player : The player. Player 1 = 1, Player 2 = 2
     * @return The path to the player's monster zone in firebase
     */
    public static DatabaseReference getMonsterCardPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD).child(MONSTER);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(FIELD).child(MONSTER);
        }
    }

    /**
     * Get the Spell/Trap card path in the firebase database for reading and writing given a player
     * @param player : The player. Player 1 = 1, Player 2 = 2
     * @return The path to the player's spell/trap zone in firebase
     */
    public static DatabaseReference getSpellCardPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD).child(SPELL_TRAP);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(FIELD).child(SPELL_TRAP);
        }
    }

    /**
     * Get the Field Spell card path in the firebase database for reading and writing given a player
     * @return The path to the field spell zone in firebase
     */
    public static DatabaseReference getFieldSpellPath() {
        return mDatabase.child(FIELD_SPELL);
    }

    /**
     * Get the Left Pendulum card path in the firebase database for reading and writing given a player
     * @param player : The player. Player 1 = 1, Player 2 = 2
     * @return The path to the player's left pendulum zone in firebase
     */
    public static DatabaseReference getPendulumLeftPath(int player) {
        if (player == 1) {
            return mDatabase.child(PLAYERS).child(PLAYER_1).child(FIELD).child(PENDULUM_LEFT);
        }
        else {
            return mDatabase.child(PLAYERS).child(PLAYER_2).child(FIELD).child(PENDULUM_LEFT);
        }
    }

    /**
     * Get the Right Pendulum card path in the firebase database for reading and writing given a player
     * @param player : The player. Player 1 = 1, Player 2 = 2
     * @return The path to the player's right pendulum zone in firebase
     */
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
    public static void writeLifePoints(int newLifePoints, int player) {
        if (player == 1) {
            mDatabase.child(PLAYERS).child(PLAYER_1).child(LIFE_POINTS).setValue(newLifePoints);
        }
        else {
            mDatabase.child(PLAYERS).child(PLAYER_2).child(LIFE_POINTS).setValue(newLifePoints);
        }
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

    /**
     * Helper function to get the firebase path given a card position on the field. A mapping from the UI to firebase.
     * @param fieldPosition A position number ranging from 1 to 13.
     * @return The database reference to the card at fieldPosition
     */
    private static DatabaseReference getFirebasePathFromCardPosition(int fieldPosition) {

        DatabaseReference field = mDatabase.child(PLAYERS).child(CURRENT_PLAYER).child(FIELD);
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
