package android.com.holoyugioh;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import firebase.GameState;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Check to see if NFC is available on this device
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            Toast.makeText(this, "NFC available on this device", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_SHORT).show();
        }

        initializeButtons();
    }

    private void initializeButtons() {
        Button startP1 = (Button) findViewById(R.id.start_player1);
        Button startP2 = (Button) findViewById(R.id.start_player2);

        startP1.setOnClickListener(this);
        startP2.setOnClickListener(this);
    }

    /**
     * Starts game when duel button is pressed
     */
    private void initializeGame(int player) {
        // Start new game
        GameState.initializePlayer(player);

        // Start game
        Intent intent = new Intent(this, PlayerOptionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_player1:
                initializeGame(1);
                break;

            case R.id.start_player2:
                initializeGame(2);
                break;

            default:
                break;
        }
    }
}