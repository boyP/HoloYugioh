package android.com.holoyugioh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Constants.Constants;
import api.CardDatabase;
import api.OnDataReadyListener;
import game.Card;

public class CardDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnDataReadyListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        initializeButtons();

        CardDatabase cardDatabase = new CardDatabase(this);

        // Get card information from previous activity
        Card card = getIntent().getParcelableExtra(Constants.CARD_PARCEL);
        cardDatabase.getCardDetails(card.getName());
    }

    private void initializeButtons() {
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void populateCardDetails(Card card) {
        ArrayList<String> details = new ArrayList<>();

        if (card.getCardType().equals(Constants.MONSTER.toLowerCase())) {
            details.add("Name: " + card.getName());
            details.add("Effect: " + card.getDescription());
            details.add("Card Type: " + card.getCardType());
            details.add("Type: " + card.getType());
            details.add("Family: " + card.getFamily());
            details.add("ATK: " + card.getAtk());
            details.add("DEF: " + card.getDef());
            details.add("Level: " + card.getLevel());
        }
        else {
            details.add("Name: " + card.getName());
            details.add("Effect: " + card.getDescription());
            details.add("Card Type: " + card.getCardType());
            details.add("Property: " + card.getProperty());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, details);
        ListView listView = (ListView) findViewById(R.id.card_details);
        listView.setAdapter(adapter);
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

    @Override
    public void onDataReady(Card card) {
        if (card == null) {
            Toast.makeText(this, "API Error", Toast.LENGTH_LONG).show();
        }
        else {
            populateCardDetails(card);
        }
    }
}