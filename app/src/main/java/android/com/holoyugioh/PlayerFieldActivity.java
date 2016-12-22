package android.com.holoyugioh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import Constants.Constants;
import game.Card;
import game.CardButton;

public class PlayerFieldActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_field);

        initializeButtons();
        // TODO Update field from firebase
    }

    private void initializeButtons() {
        CardButton mon1 = (CardButton) findViewById(R.id.monster_1);
        CardButton mon2 = (CardButton) findViewById(R.id.monster_2);
        CardButton mon3 = (CardButton) findViewById(R.id.monster_3);
        CardButton mon4 = (CardButton) findViewById(R.id.monster_4);
        CardButton mon5 = (CardButton) findViewById(R.id.monster_5);

        CardButton magic1 = (CardButton) findViewById(R.id.magic_1);
        CardButton magic2 = (CardButton) findViewById(R.id.magic_2);
        CardButton magic3 = (CardButton) findViewById(R.id.magic_3);
        CardButton magic4 = (CardButton) findViewById(R.id.magic_4);
        CardButton magic5 = (CardButton) findViewById(R.id.magic_5);

        CardButton grave = (CardButton) findViewById(R.id.grave);
        CardButton field = (CardButton) findViewById(R.id.field_spell);

        CardButton pend_right = (CardButton) findViewById(R.id.pendulum_right);
        CardButton pend_left = (CardButton) findViewById(R.id.pendulum_left);

        Button back = (Button) findViewById(R.id.back);
        Button endTurn = (Button) findViewById(R.id.end_turn);

        // Attach OnClickListeners
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
        endTurn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {
            case R.id.back:

                finish();
                break;
            case R.id.end_turn:

                // End Turn logic
                break;

            default:
                // Card press
                CardButton button = (CardButton) findViewById(view.getId());
                Log.d("TEST", "The answer is: " + button.isEmptySlot());

                if (button.isEmptySlot()) {

                    // TODO bundle position information?
                    intent = new Intent(this, NfcActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    intent = new Intent(this, CardOptionsActivity.class);

                    Card card = button.getCard();
                    intent.putExtra(Constants.CARD_PARCEL, card);

                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
