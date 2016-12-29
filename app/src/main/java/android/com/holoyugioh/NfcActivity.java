package android.com.holoyugioh;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import Constants.Constants;
import api.CardDatabase;
import api.OnDataReadyListener;
import game.Card;

/**
 * This activity reads for NFC Cards
 */
public class NfcActivity extends AppCompatActivity implements View.OnClickListener, OnDataReadyListener{

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    final protected static String API_ERROR_MSG = "Card does not exist in database";

    // For NFC Dispatching
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilterArray;
    private String[][] techListArray;
    private NfcAdapter mNfcAdapter;

    private CardDatabase cardDatabase;
    private int fieldPosition;

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    // TODO Accept NDEF cards rather than Tag Discovered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Pending Intent
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter tagDiscovery = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

        // Intercept only NDEF Intents
//        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
//        try {
//            ndef.addDataType("*/*");
//        }
//        catch (IntentFilter.MalformedMimeTypeException e) {
//            throw new RuntimeException("Failed", e);
//        }

        intentFilterArray = new IntentFilter[] {tagDiscovery};
        techListArray = new String[][] { new String[] {MifareClassic.class.getName()}};

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Context context = getApplicationContext();

        // Checks if the intent is the NDEF DISCOVERED intent. Only those supported will go in here
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                    Log.d("NDEF", rawMessages[i].toString());
                    Toast toast = Toast.makeText(context, "NDEF", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        // Handles all Non-supported NFC cards such as MIFARE classic which is what I'm testing with
        else if(intent != null && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String str = bytesToHex(tag.getId());
            Log.d("TAG", str);

            // Perform action when card read
            cardDatabase.getCardDetails("Junk Synchron");
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
