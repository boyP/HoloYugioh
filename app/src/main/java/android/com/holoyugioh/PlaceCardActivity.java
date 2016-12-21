package android.com.holoyugioh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import game.Card;

public class PlaceCardActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String CARD_PARCEL = "CARD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_card);

        initializeButtons();

        Card card = getIntent().getParcelableExtra(CARD_PARCEL);
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

        // Bundle card information and button press

        Intent intent = null;

        switch (view.getId()) {
            case R.id.face_down:

                intent = new Intent(this, PlayerFieldActivity.class);

                break;

            case R.id.face_up:

                intent = new Intent(this, PlayerFieldActivity.class);
                break;

            default:
                // Back button is pressed

                intent = new Intent(this, NfcActivity.class);
                break;
        }

        // TODO UPDATE FIREBASE WITH NEW INFORMATION IF FACEDOWN OR FACEUP WAS PRESSED

        startActivity(intent);
        finish();
    }
}
