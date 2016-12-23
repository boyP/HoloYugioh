package android.com.holoyugioh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * This screen is for letting the player choose the next step if it is their turn
 */
public class PlayerOptionActivity extends AppCompatActivity implements View.OnClickListener{

    // Need game state to fill in current life points and current player's turn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_option);
        
        initializeButtons();
    }

    private void initializeButtons() {
        Button menu = (Button) findViewById(R.id.menu);
        Button viewOppField = (Button) findViewById(R.id.view_opp_field);
        Button viewYourField = (Button) findViewById(R.id.view_player_field);

        // Set onClick Listeners
        menu.setOnClickListener(this);
        viewOppField.setOnClickListener(this);
        viewYourField.setOnClickListener(this);
    }

    private void populateLifePoints() {
        // TODO fill life points
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {
            case R.id.view_opp_field:

                // Show Opponent Field screen
                intent = new Intent(this, OppFieldActivity.class);
                startActivity(intent);

                break;
            case R.id.view_player_field:

                // Show Your Field screen
                intent = new Intent(this, PlayerFieldActivity.class);
                startActivity(intent);

                break;
            case R.id.menu:

                // Show Menu screen
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
        }
    }
}
