package android.com.holoyugioh;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import Constants.Constants;
import api.CardDatabase;
import api.OnDataReadyListener;
import game.Card;

/**
 * This activity reads for NFC Cards
 */
public class NfcActivity extends AppCompatActivity implements View.OnClickListener, OnDataReadyListener{

    private static final String MIME_TEXT_PLAIN = "text/plain";
    final protected static String API_ERROR_MSG = "Card does not exist in database";
    final protected static String INCORRECT_TAG = "Expected NDEF TAG";
    final protected static String INCORRECT_TAG_MSG = "Expected plain text msg";

    // For NFC Dispatching
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilterArray;
    private String[][] techListArray;
    private NfcAdapter mNfcAdapter;

    private CardDatabase cardDatabase;
    private int fieldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Pending Intent
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Intercept only NDEF Intents
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType(MIME_TEXT_PLAIN);
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Failed", e);
        }

        intentFilterArray = new IntentFilter[] {ndef};
        techListArray = new String[][] { new String[] {Ndef.class.getName()}};

        initializeButtons();

        // Card API Initialization
        cardDatabase = new CardDatabase(this);

        // Retrieve card field position from intent
        fieldPosition = getIntent().getIntExtra(Constants.FIELD_POSITION, 0);

    }

    private void initializeButtons() {
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilterArray, techListArray);
    }

    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        byte[] payload = record.getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 51;
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Context context = getApplicationContext();

        // Checks if the intent is the NDEF DISCOVERED intent. Only those supported will go in here
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {

            // Get payload from NFC Tag
            String type = intent.getType();
            if (type.equals(MIME_TEXT_PLAIN)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Ndef ndef = Ndef.get(tag);
                NdefMessage message = ndef.getCachedNdefMessage();
                NdefRecord[] records = message.getRecords();
                for (NdefRecord record : records) {
                    if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                        try {
                            String cardName = readText(record);
                            Log.d("NDEF", cardName);
                            cardDatabase.getCardDetails(cardName);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                Toast.makeText(context, INCORRECT_TAG_MSG, Toast.LENGTH_SHORT).show();
            }
        }
        // Handles all Non-supported NFC cards such as MIFARE classic which is what I'm testing with
        else if(intent != null && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Toast.makeText(context, INCORRECT_TAG, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:

                Intent intent = new Intent(this, PlayerFieldActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onDataReady(Card card) {

        if (card == null) {
            Toast.makeText(this, API_ERROR_MSG, Toast.LENGTH_LONG).show();
        }
        else {
            Intent placementIntent = new Intent(this, PlaceCardActivity.class);
            placementIntent.putExtra(Constants.CARD_PARCEL, card);
            placementIntent.putExtra(Constants.FIELD_POSITION, fieldPosition);
            startActivity(placementIntent);
            finish();
        }
    }
}
