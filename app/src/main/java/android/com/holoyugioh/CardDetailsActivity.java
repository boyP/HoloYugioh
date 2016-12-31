package android.com.holoyugioh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

    private void populateCardDetails(Card card) {
        final ArrayList<String> titles = new ArrayList<>();
        final ArrayList<String> details = new ArrayList<>();

        if (card.getCardType().equals(Constants.MONSTER.toLowerCase())) {
            titles.add(Constants.NAME);
            titles.add(Constants.EFFECT);
            titles.add(Constants.CARD_TYPE);
            titles.add(Constants.TYPE);
            titles.add(Constants.FAMILY);
            titles.add(Constants.ATK);
            titles.add(Constants.DEF);
            titles.add(Constants.LEVEL);

            details.add(card.getName());
            details.add(card.getDescription());
            details.add(card.getCardType());
            details.add(card.getType());
            details.add(card.getFamily());
            details.add(Integer.toString(card.getAtk()));
            details.add(Integer.toString(card.getDef()));
            details.add(Integer.toString(card.getLevel()));
        }
        else {
            titles.add(Constants.NAME);
            titles.add(Constants.EFFECT);
            titles.add(Constants.CARD_TYPE);
            titles.add(Constants.PROPERTY);

            details.add(card.getName());
            details.add(card.getDescription());
            details.add(card.getCardType());
            details.add(card.getProperty());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, details) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(titles.get(position));
                text2.setText(details.get(position));
                return view;
            }
        };
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