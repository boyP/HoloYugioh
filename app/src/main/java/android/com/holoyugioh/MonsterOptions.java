package android.com.holoyugioh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Constants.Constants;
import firebase.GameState;
import game.Card;

public class MonsterOptions extends Fragment implements View.OnClickListener {

    private Card card;
    private int fieldPosition;

    public MonsterOptions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setCardAndPosition(Card card, int fieldPosition) {
        this.card = card;
        this.fieldPosition = fieldPosition;
    }

    private void initializeButtons(View v) {
        Button faceUpATK = (Button) v.findViewById(R.id.face_up);
        Button faceUpDEF = (Button) v.findViewById(R.id.face_up_def);
        Button faceDown = (Button) v.findViewById(R.id.face_down);

        Button destroy = (Button) v.findViewById(R.id.destroy);
        Button removePlay = (Button) v.findViewById(R.id.remove_play);

        faceUpATK.setOnClickListener(this);
        faceUpDEF.setOnClickListener(this);
        faceDown.setOnClickListener(this);
        destroy.setOnClickListener(this);
        removePlay.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monster_options, container, false);
        initializeButtons(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {

            case R.id.destroy:

                GameState.writeRemoveCard(card, fieldPosition);
                intent = new Intent(getContext(), PlayerFieldActivity.class);
                startActivity(intent);
                break;

            case R.id.face_up:

                GameState.writeChangeCardPosition(card, fieldPosition, Constants.FACE_UP_POSITION);
                intent = new Intent(getContext(), PlayerFieldActivity.class);
                startActivity(intent);
                break;

            case R.id.face_up_def:

                GameState.writeChangeCardPosition(card, fieldPosition, Constants.FACE_UP_DEF_POSITION);
                intent = new Intent(getContext(), PlayerFieldActivity.class);
                startActivity(intent);
                break;

            case R.id.face_down:

                GameState.writeChangeCardPosition(card, fieldPosition, Constants.FACE_DOWN_POSITION);
                intent = new Intent(getContext(), PlayerFieldActivity.class);
                startActivity(intent);
                break;

            case R.id.remove_play:

                GameState.writeRemoveCard(card, fieldPosition);
                intent = new Intent(getContext(), PlayerFieldActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
