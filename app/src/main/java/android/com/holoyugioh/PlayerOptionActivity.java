package android.com.holoyugioh;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import firebase.GameState;

/**
 * This screen is for letting the player choose the next step if it is their turn
 */
public class PlayerOptionActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    // Need game state to fill in current life points and current player's turn

    private GoogleApiClient mGoogleApiClient;
    private static final String CONNECTION_FAILED = "Connection Failed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_option);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        initializeButtons();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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

        GameState.getLifePointsPath(GameState.getPlayerNum()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long val = (Long) dataSnapshot.getValue();
                playerLP.setText(Long.toString(val));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GameState.getLifePointsPath(GameState.getOpponentPlayerNum()).addValueEventListener(new ValueEventListener() {
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

        final Intent intent;

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
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                startActivity(new Intent(PlayerOptionActivity.this, LoginActivity.class));
                            }
                        });
                }
                else {
                    Log.e("TEST", "connecting: " + mGoogleApiClient.isConnected());
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("TEST", CONNECTION_FAILED);
    }
}
