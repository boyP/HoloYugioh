package android.com.holoyugioh;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Constants.Constants;
import game.Card;

public class CardOptionsActivity extends FragmentActivity implements View.OnClickListener{

    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_options);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int fieldPosition = getIntent().getIntExtra(Constants.FIELD_POSITION, 0);
        card = getIntent().getParcelableExtra(Constants.CARD_PARCEL);

        TextView name = (TextView) findViewById(R.id.card_name);
        name.setText(card.getName());

        initializeButtons();

        if (card.getCardType().equals(Constants.MONSTER)) {
            MonsterOptions fragment = new MonsterOptions();
            fragment.setCardAndPosition(card, fieldPosition);
            transitionFragment(fragment);
        }
        else {
            SpellTrapOptions fragment = new SpellTrapOptions();
            fragment.setCardAndPosition(card, fieldPosition);
            transitionFragment(fragment);
        }
    }

    private void initializeButtons() {
        Button back = (Button) findViewById(R.id.back);
        Button viewDetails = (Button) findViewById(R.id.view_details);

        back.setOnClickListener(this);
        viewDetails.setOnClickListener(this);
    }

    private void transitionFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment).commit();
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
