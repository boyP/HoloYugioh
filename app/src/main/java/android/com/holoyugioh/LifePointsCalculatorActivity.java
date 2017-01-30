package android.com.holoyugioh;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import firebase.GameState;

public class LifePointsCalculatorActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int NUMBER_LENGTH_LIMIT = 7;

    private String currentNumber;
    private int yourLP;
    private int oppLP;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_points_calculator);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        currentNumber = "";
        initializeButtons();

        final TextView playerLP = (TextView) findViewById(R.id.yourLPLabel);
        final TextView opp = (TextView) findViewById(R.id.oppLPLabel);
        editText = (EditText) findViewById(R.id.output);
        editText.setText("");

        GameState.getLifePointsPath(GameState.getPlayerNum()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long val = (Long) dataSnapshot.getValue();
                yourLP = (int) val;
                String lp = "" + yourLP;
                playerLP.setText(lp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GameState.getLifePointsPath(GameState.getOpponentPlayerNum()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long val = (Long) dataSnapshot.getValue();
                oppLP = (int) val;
                String lp = "" + oppLP;
                opp.setText(lp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addLifePoints(int lifePoints, int player) {
        if (!currentNumber.isEmpty() && currentNumber.length() < NUMBER_LENGTH_LIMIT) {
            GameState.writeLifePoints(lifePoints + Integer.parseInt(currentNumber), player);
        }
        currentNumber = "";
        editText.setText("");

    }

    private void subtractLifePoints(int lifePoints, int player) {
        if (!currentNumber.isEmpty() && currentNumber.length() < NUMBER_LENGTH_LIMIT) {
            GameState.writeLifePoints(lifePoints - Integer.parseInt(currentNumber), player);
        }
        currentNumber = "";
        editText.setText("");
    }

    private void initializeButtons() {

        Button back = (Button) findViewById(R.id.back);
        Button menu = (Button) findViewById(R.id.menu);
        back.setOnClickListener(this);
        menu.setOnClickListener(this);

        // Plus and Minus Buttons
        Button plusPlayer1 = (Button) findViewById(R.id.plus_p1);
        Button minusPlayer1 = (Button) findViewById(R.id.minus_p1);

        Button plusPlayer2 = (Button) findViewById(R.id.plus_p2);
        Button minusPlayer2 = (Button) findViewById(R.id.minus_p2);

        plusPlayer1.setOnClickListener(this);
        minusPlayer1.setOnClickListener(this);
        plusPlayer2.setOnClickListener(this);
        minusPlayer2.setOnClickListener(this);

        // Calculator Buttons
        Button calc1 = (Button) findViewById(R.id.calculator_1);
        Button calc2 = (Button) findViewById(R.id.calculator_2);
        Button calc3 = (Button) findViewById(R.id.calculator_3);
        Button calc4 = (Button) findViewById(R.id.calculator_4);
        Button calc5 = (Button) findViewById(R.id.calculator_5);
        Button calc6 = (Button) findViewById(R.id.calculator_6);
        Button calc7 = (Button) findViewById(R.id.calculator_7);
        Button calc8 = (Button) findViewById(R.id.calculator_8);
        Button calc9 = (Button) findViewById(R.id.calculator_9);
        Button calc0 = (Button) findViewById(R.id.calculator_0);
        Button calc00 = (Button) findViewById(R.id.calculator_00);
        Button calcC = (Button) findViewById(R.id.calculator_C);

        calc1.setOnClickListener(this);
        calc2.setOnClickListener(this);
        calc3.setOnClickListener(this);
        calc4.setOnClickListener(this);
        calc5.setOnClickListener(this);
        calc6.setOnClickListener(this);
        calc7.setOnClickListener(this);
        calc8.setOnClickListener(this);
        calc9.setOnClickListener(this);
        calc0.setOnClickListener(this);
        calc00.setOnClickListener(this);
        calcC.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // Menu buttons
            case R.id.menu:
                break;
            case R.id.back:
                finish();
                break;

            // Write to Life Points buttons
            case R.id.plus_p1:
                addLifePoints(yourLP, GameState.getPlayerNum());
                break;
            case R.id.minus_p1:
                subtractLifePoints(yourLP, GameState.getPlayerNum());
                break;
            case R.id.plus_p2:
                addLifePoints(oppLP, GameState.getOpponentPlayerNum());
                break;
            case R.id.minus_p2:
                subtractLifePoints(oppLP, GameState.getOpponentPlayerNum());
                break;

            // Calculator Buttons
            case R.id.calculator_1:
                currentNumber += "1";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_2:
                currentNumber += "2";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_3:
                currentNumber += "3";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_4:
                currentNumber += "4";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_5:
                currentNumber += "5";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_6:
                currentNumber += "6";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_7:
                currentNumber += "7";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_8:
                currentNumber += "8";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_9:
                currentNumber += "9";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_0:
                currentNumber += "0";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_00:
                currentNumber += "00";
                editText.setText(currentNumber);
                break;
            case R.id.calculator_C:
                currentNumber = "";
                editText.setText(currentNumber);
                break;
        }
    }
}