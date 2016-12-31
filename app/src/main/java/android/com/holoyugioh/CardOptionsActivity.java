package android.com.holoyugioh;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import Constants.Constants;
import firebase.GameState;
import game.Card;

public class CardOptionsActivity extends AppCompatActivity implements View.OnClickListener{

    private Card card;
    private int fieldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_options);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initializeButtons();

        fieldPosition = getIntent().getIntExtra(Constants.FIELD_POSITION, 0);
        card = getIntent().getParcelableExtra(Constants.CARD_PARCEL);

        TextView name = (TextView) findViewById(R.id.card_name);
        name.setText(card.getName());
    }

    private void initializeButtons() {
        Button back = (Button) findViewById(R.id.back);
        Button viewDetails = (Button) findViewById(R.id.view_details);

        ImageButton destroy = (ImageButton) findViewById(R.id.destroy);
        ImageButton changePos = (ImageButton) findViewById(R.id.change_pos);
        ImageButton removePlay = (ImageButton) findViewById(R.id.remove_play);

        back.setOnClickListener(this);
        viewDetails.setOnClickListener(this);
        destroy.setOnClickListener(this);
        changePos.setOnClickListener(this);
        removePlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {

            case R.id.back:

                intent = new Intent(this, PlayerFieldActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.destroy:

                GameState.writeRemoveCard(card, fieldPosition);
                intent = new Intent(this, PlayerFieldActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.change_pos:

                // TODO Actually change position
                GameState.writeChangeCardPosition(card, fieldPosition, Constants.FACE_UP_DEF_POSITION);
                intent = new Intent(this, PlayerFieldActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.remove_play:

                GameState.writeRemoveCard(card, fieldPosition);
                intent = new Intent(this, PlayerFieldActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.view_details:

                intent = new Intent(this, CardDetailsActivity.class);
                intent.putExtra(Constants.CARD_PARCEL, card);

                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
