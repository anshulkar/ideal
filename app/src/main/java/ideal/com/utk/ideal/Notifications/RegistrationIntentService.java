package ideal.com.utk.ideal.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ideal.com.utk.ideal.R;

/**
 * Created by Utkarsh on 28-04-2016 with the help of SWAG.
 */
// abbreviated tag name
public class RegistrationIntentService extends IntentService {

    // abbreviated tag name
    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        InstanceID instanceID = InstanceID.getInstance(this);
        String senderId = getResources().getString(R.string.gcm_senderId);
        // Fetch token here
        try {
            String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d(TAG, "GCM Registration Token: " + token);
            // save token
            sharedPreferences.edit().putString(GCM_TOKEN, token).apply();
            // pass along this data
            sendRegistrationToServer(token);
        } catch (IOException e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    private void sendRegistrationToServer(String token) {

        // send network request

        String URL_Address = getResources().getString(R.string.server_url)+"index.php/apps/setGCM31337";
        Log.d("dsf",URL_Address);

        try {
            URL url;
            HttpURLConnection urlConnection;

            url = new URL(URL_Address);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            urlConnection.setRequestProperty("Token",token);

            urlConnection.connect();
            urlConnection.disconnect();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        // if registration sent was successful, store a boolean that indicates whether the generated token has been sent to server
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }
}