package android.com.holoyugioh;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import Constants.Constants;
import firebase.GameState;
import game.Card;
import game.CardButton;

/**
 * Show the opponents field
 */
public class OppFieldActivity extends AppCompatActivity implements View.OnClickListener{

    private static int OPP_PLAYER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opp_field);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        OPP_PLAYER = GameState.getOpponentPlayerNum();

        initializeButtons();
        TextView playerName = (TextView) findViewById(R.id.card_name);
        Log.d("PLAYER", "opp player num " + OPP_PLAYER);
        String player = OPP_PLAYER == 1 ? "Player 1's Field" : "Player 2's Field";
        playerName.setText(player);
    }

    private void initializeButtons() {

        final CardButton mon1 = (CardButton) findViewById(R.id.monster_1);
        final CardButton mon2 = (CardButton) findViewById(R.id.monster_2);
        final CardButton mon3 = (CardButton) findViewById(R.id.monster_3);
        final CardButton mon4 = (CardButton) findViewById(R.id.monster_4);
        final CardButton mon5 = (CardButton) findViewById(R.id.monster_5);

        final CardButton magic1 = (CardButton) findViewById(R.id.magic_1);
        final CardButton magic2 = (CardButton) findViewById(R.id.magic_2);
        final CardButton magic3 = (CardButton) findViewById(R.id.magic_3);
        final CardButton magic4 = (CardButton) findViewById(R.id.magic_4);
        final CardButton magic5 = (CardButton) findViewById(R.id.magic_5);

        final CardButton grave = (CardButton) findViewById(R.id.grave);
        final CardButton field = (CardButton) findViewById(R.id.field_spell);

        final CardButton pend_right = (CardButton) findViewById(R.id.pendulum_right);
        final CardButton pend_left = (CardButton) findViewById(R.id.pendulum_left);

        Button back = (Button) findViewById(R.id.back);

        //*************************
        //     Populate Field
        //*************************
        GameState.getFieldSpellPath().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map fieldSpell = (Map) dataSnapshot.getValue();
                String name = (String) fieldSpell.get(GameState.NAME);
                long pos = (Long) fieldSpell.get(GameState.POSITION);

                // Remove card
                if (name.equals("")) {
                    field.setCard(null);
                    field.setImageResource(R.mipmap.ic_launcher);
                }

                // Place card
                else {
                    field.setCard(new Card(name, Constants.SPELL, pos));
                    if (pos == Constants.FACE_UP_POSITION) {
                        field.setImageResource(R.mipmap.spell);
                    }
                    else {
                        field.setImageResource(R.mipmap.set_spell);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GameState.getPendulumLeftPath(OPP_PLAYER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map pend = (Map) dataSnapshot.getValue();
                String name = (String) pend.get(GameState.NAME);
                long pos = (Long) pend.get(GameState.POSITION);

                // Remove card
                if (name.equals("")) {
                    pend_left.setCard(null);
                    pend_left.setImageResource(R.mipmap.ic_launcher);
                }

                else {
                    pend_left.setCard(new Card(name, Constants.SPELL, pos));
                    if (pos == Constants.FACE_UP_POSITION) {
                        pend_left.setImageResource(R.mipmap.spell);
                    }
                    else {
                        pend_left.setImageResource(R.mipmap.set_spell);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GameState.getPendulumRightPath(OPP_PLAYER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map pend = (Map) dataSnapshot.getValue();
                String name = (String) pend.get(GameState.NAME);
                long pos = (Long) pend.get(GameState.POSITION);

                // Remove card
                if (name.equals("")) {
                    pend_right.setCard(null);
                    pend_right.setImageResource(R.mipmap.ic_launcher);
                }

                // Place card
                else {
                    pend_right.setCard(new Card(name, Constants.SPELL, pos));
                    if (pos == Constants.FACE_UP_POSITION) {
                        pend_right.setImageResource(R.mipmap.spell);
                    }
                    else {
                        pend_right.setImageResource(R.mipmap.set_spell);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Monster value listener
        GameState.getMonsterCardPath(OPP_PLAYER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map monster = (Map) dataSnapshot.getValue();

                Map card1 = (Map) monster.get(GameState.CARD1);
                placeMonsterCard(card1, mon1);

                Map card2 = (Map) monster.get(GameState.CARD2);
                placeMonsterCard(card2, mon2);

                Map card3 = (Map) monster.get(GameState.CARD3);
                placeMonsterCard(card3, mon3);

                Map card4 = (Map) monster.get(GameState.CARD4);
                placeMonsterCard(card4, mon4);

                Map card5 = (Map) monster.get(GameState.CARD5);
                placeMonsterCard(card5, mon5);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Spell value listener
        GameState.getSpellCardPath(OPP_PLAYER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map spell = (Map) dataSnapshot.getValue();

                Map card1 = (Map) spell.get(GameState.CARD1);
                placeSpellCard(card1, magic1);

                Map card2 = (Map) spell.get(GameState.CARD2);
                placeSpellCard(card2, magic2);

                Map card3 = (Map) spell.get(GameState.CARD3);
                placeSpellCard(card3, magic3);

                Map card4 = (Map) spell.get(GameState.CARD4);
                placeSpellCard(card4, magic4);

                Map card5 = (Map) spell.get(GameState.CARD5);
                placeSpellCard(card5, magic5);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //*************************
        // Attach OnClickListeners
        //*************************
        mon1.setOnClickListener(this);
        mon2.setOnClickListener(this);
        mon3.setOnClickListener(this);
        mon4.setOnClickListener(this);
        mon5.setOnClickListener(this);

        magic1.setOnClickListener(this);
        magic2.setOnClickListener(this);
        magic3.setOnClickListener(this);
        magic4.setOnClickListener(this);
        magic5.setOnClickListener(this);

        grave.setOnClickListener(this);
        field.setOnClickListener(this);

        pend_left.setOnClickListener(this);
        pend_right.setOnClickListener(this);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            default:
                CardButton button = (CardButton) findViewById(view.getId());
                if (!button.isEmptySlot()) {

                    Card card = button.getCard();

                    // Cannot view opponent card if face down
                    if (card.getPosition() == Constants.FACE_DOWN_POSITION) {
                        Toast toast = Toast.makeText(this, Constants.CANNOT_VIEW_CARD, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        intent = new Intent(this, CardDetailsActivity.class);
                        intent.putExtra(Constants.CARD_PARCEL, card);

                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Toast toast = Toast.makeText(this, Constants.NO_CARD_PLACED, Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
        }
    }

    private void placeMonsterCard(Map card, CardButton cardButton) {
        String name = (String) card.get(GameState.NAME);
        long pos = (Long) card.get(GameState.POSITION);

        // Remove card
        if (name.equals("")) {
            cardButton.setCard(null);
            cardButton.setImageResource(R.mipmap.ic_launcher);
        }

        // Place card
        else {
            cardButton.setCard(new Card(name, Constants.MONSTER, pos));
            if (pos == Constants.FACE_UP_POSITION) {
                cardButton.setImageResource(R.mipmap.monster);
            }
            else if (pos == Constants.FACE_DOWN_POSITION) {
                cardButton.setImageResource(R.mipmap.set_monster);
            }
            else {
                cardButton.setImageResource(R.mipmap.def_monster);
            }
        }
    }

    private void placeSpellCard(Map card, CardButton cardButton) {
        String name = (String) card.get(GameState.NAME);
        long pos = (Long) card.get(GameState.POSITION);

        // Remove card
        if (name.equals("")) {
            cardButton.setCard(null);
            cardButton.setImageResource(R.mipmap.ic_launcher);
        }

        // Place Card
        else {
            cardButton.setCard(new Card(name, Constants.SPELL, pos));
            if (pos == Constants.FACE_UP_POSITION) {
                cardButton.setImageResource(R.mipmap.spell);
            }
            else {
                cardButton.setImageResource(R.mipmap.set_spell);
            }
        }
    }
}
