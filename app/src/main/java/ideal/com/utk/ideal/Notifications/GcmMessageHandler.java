package ideal.com.utk.ideal.notifications;

/**
 * Created by Utkarsh on 28-04-2016 with the help of SWAG.
 */
import com.google.android.gms.gcm.GcmListenerService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import ideal.com.utk.ideal.LogInActivity;
import ideal.com.utk.ideal.R;

public class GcmMessageHandler extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String title = data.getString("gcm.notification.title");
        String body = data.getString("gcm.notification.body");

        //Log.d("GCMlistener",bundle2string(data));
        createNotification(title,body);
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body) {

        Intent myIntent = new Intent(getApplicationContext(), LogInActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body).setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

    /*public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key *//*+ bundle.get(key)*//* + ";";
        }
        string += " }Bundle";
        return string;
    }*/
}