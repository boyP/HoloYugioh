package android.com.holoyugioh;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import firebase.GameState;

/**
 * This screen is for letting the player choose the next step if it is their turn
 */
public class PlayerOptionActivity extends AppCompatActivity implements View.OnClickListener{

    // Need game state to fill in current life points and current player's turn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_option);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        initializeButtons();
        TextView textView = (TextView) findViewById(R.id.player_name);
        String playerName = "Player " + GameState.getPlayer();
//        textView.setText(playerName);
    }

    private void initializeButtons() {
        Button viewOppField = (Button) findViewById(R.id.view_opp_field);
        Button viewYourField = (Button) findViewById(R.id.view_player_field);

        Button menu = (Button) findViewById(R.id.menu);
        final TextView playerLP = (TextView) findViewById(R.id.yourLPLabel);
        final TextView oppLP = (TextView) findViewById(R.id.oppLPLabel);

        // Set onClick Listeners
        viewOppField.setOnClickListener(this);
        viewYourField.setOnClickListener(this);
        menu.setOnClickListener(this);
        playerLP.setOnClickListener(this);
        oppLP.setOnClickListener(this);

        GameState.getLifePointsPath(GameState.getPlayer()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long val = (Long) dataSnapshot.getValue();
                playerLP.setText(Long.toString(val));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GameState.getLifePointsPath(GameState.getOpponentPlayer()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long val = (Long) dataSnapshot.getValue();
                oppLP.setText(Long.toString(val));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

            case R.id.oppLPLabel:
                intent = new Intent(this, LifePointsCalculatorActivity.class);
                startActivity(intent);
                break;

            case R.id.yourLPLabel:
                intent = new Intent(this, LifePointsCalculatorActivity.class);
                startActivity(intent);
                break;

            case R.id.menu:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
