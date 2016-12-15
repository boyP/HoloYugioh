package android.com.holoyugioh;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import api.CardDatabase;
import game.Card;

public class MainActivity extends AppCompatActivity {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private DatabaseReference mDatabase;

    // For NFC Dispatching
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilterArray;
    private String[][] techListArray;
    private NfcAdapter mNfcAdapter;

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Check to see if NFC is available on this device
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            Toast.makeText(this, "NFC available on this device", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_SHORT).show();
        }

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

        // Set up database for handling game state
        mDatabase = FirebaseDatabase.getInstance().getReference("cards");
        String name = "Polymerization";
        String desc = "Cool card 4 fusion";
        Card card = new Card(name, desc);
        mDatabase.setValue(card);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    /**
     * Receives new intent
     *
     * @param intent
     */
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

            showCardPlacementDialog(str);
        }
    }

    /**
     * Card placement Dialog
     */
    private void showCardPlacementDialog(String cardName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Where do you want to place " + cardName);

        // Put options here

        // Add onclick events to all options

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}