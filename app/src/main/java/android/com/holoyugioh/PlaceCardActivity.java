package android.com.holoyugioh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Constants.Constants;
import firebase.GameState;
import game.Card;

public class PlaceCardActivity extends AppCompatActivity implements View.OnClickListener{

    private Card card;
    private int fieldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_card);

        initializeButtons();

        card = getIntent().getParcelableExtra(Constants.CARD_PARCEL);
        fieldPosition = getIntent().getIntExtra(Constants.FIELD_POSITION, 0);

        TextView cardName = (TextView) findViewById(R.id.card_name);
        cardName.setText(card.getName());
    }

    private void initializeButtons() {

        Button faceUp = (Button) findViewById(R.id.face_up);
        Button faceDown = (Button) findViewById(R.id.face_down);

        Button back = (Button) findViewById(R.id.back);

        faceUp.setOnClickListener(this);
        faceDown.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {
            case R.id.face_down:

                GameState.writePlaceCard(card, fieldPosition, Constants.FACE_DOWN_POSITION);
                intent = new Intent(this, PlayerFieldActivity.class);
                break;

            case R.id.face_up:

                GameState.writePlaceCard(card, fieldPosition, Constants.FACE_UP_POSITION);
                intent = new Intent(this, PlayerFieldActivity.class);
                break;

            default:
                // Back button is pressed

                intent = new Intent(this, NfcActivity.class);
                break;
        }

        startActivity(intent);
        finish();
    }
}
