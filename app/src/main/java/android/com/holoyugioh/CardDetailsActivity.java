package android.com.holoyugioh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Constants.Constants;
import game.Card;

public class CardDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        initializeButtons();

        // Get card information from previous activity
        Card card = getIntent().getParcelableExtra(Constants.CARD_PARCEL);
        populateCardDetails(card);
    }

    private void initializeButtons() {
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    private void populateCardDetails(Card card) {
        TextView name = (TextView) findViewById(R.id.card_name);

        TextView atk = (TextView) findViewById(R.id.atk_field);
        TextView def = (TextView) findViewById(R.id.def_field);

        TextView cardType = (TextView) findViewById(R.id.card_type_field);

        TextView type = (TextView) findViewById(R.id.type_field);
        TextView attribute = (TextView) findViewById(R.id.attribute_field);
        TextView effect = (TextView) findViewById(R.id.description_field);


        name.setText(card.getName());
        cardType.setText(card.getCardType());
        effect.setText(card.getDescription());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:

                finish();
                break;

            default:
                break;
        }
    }
}