package firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Constants.Constants;

public class GameState {

    private static DatabaseReference mDatabase;

    private static final String TOP_REFERENCE = "game";
    private static final String PLAYERS = "Players";
    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";

    private static final String LIFE_POINTS = "Life Points";

    private static final String FIELD = "Field";
    private static final String MONSTER = "Monster";
    private static final String SPELL_TRAP = "Spell-Trap";

    private static final String CARD1 = "Card1";
    private static final String CARD2 = "Card2";
    private static final String CARD3 = "Card3";
    private static final String CARD4 = "Card4";
    private static final String CARD5 = "Card5";

    private static final String NAME = "Name";
    private static final String POSITION = "Position";

    public static void initializeDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference(TOP_REFERENCE);
        DatabaseReference player1 = mDatabase.child(PLAYERS).child(PLAYER_1);
        DatabaseReference player2 = mDatabase.child(PLAYERS).child(PLAYER_2);

        player1.child(LIFE_POINTS).setValue(Constants.INITIAL_LIFE_POINTS);
        player2.child(LIFE_POINTS).setValue(Constants.INITIAL_LIFE_POINTS);

        DatabaseReference field1 = player1.child(FIELD);
        DatabaseReference field2 = player2.child(FIELD);

//        mDatabase = GameState.getInstance().getReference("cards");
//        String name = "Polymerization";
//        String desc = "Cool card 4 fusion";
//        Card card = new Card(name, desc, "Spell");
//        mDatabase.setValue(card);

    }
}
