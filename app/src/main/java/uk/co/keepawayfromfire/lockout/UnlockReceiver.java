package uk.co.keepawayfromfire.lockout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by cj on 11/12/17.
 */

public class UnlockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("unlock-data",
                Context.MODE_PRIVATE);

        long unlockTime = new Date().getTime();

        sharedPreferences.edit().putLong("last-unlock", unlockTime).apply();

        Toast.makeText(context, "Unlocked", Toast.LENGTH_SHORT).show();
    }
}
