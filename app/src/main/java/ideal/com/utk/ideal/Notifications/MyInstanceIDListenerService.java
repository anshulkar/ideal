package ideal.com.utk.ideal.notifications;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Utkarsh on 28-04-2016 with the help of SWAG.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}