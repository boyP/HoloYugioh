package api;

import android.util.Log;


import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import game.Card;

/**
 * Class used to get card information using the YugiohPrices API. Current works with asynchronous
 * but we may need to change it to synchronous call or use listeners to access the data when ready.
 */
public class CardDatabase {

    private static final String ERROR_TAG = "API ERROR";

    public CardDatabase() {

    }

    public void getCardDetails(String cardName) {

        YugiohRestClient.get(cardName, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                try {
                    Log.d("SUCCESS", "returned " + statusCode);
                    JSONObject data = response.getJSONObject("data");
                    Card card = new Card();
                    card.createCardFromJSON(data);

                    Log.d("SUCCESS", card.getDescription());
                }
                catch (JSONException e) {
                    Log.e(ERROR_TAG, e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e(ERROR_TAG, "returned " + statusCode);
            }
        });
    }
}
