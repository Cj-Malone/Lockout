package uk.co.keepawayfromfire.lockout;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by cj on 10/12/17.
 */

public class AdminReceiver extends DeviceAdminReceiver {

    void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        showToast(context, context.getString(R.string.admin_receiver_status_enabled));
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        super.onDisableRequested(context, intent);
        return context.getString(R.string.admin_receiver_status_disable_warning);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        showToast(context, context.getString(R.string.admin_receiver_status_disabled));
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent, UserHandle user) {
        super.onPasswordSucceeded(context, intent, user);
        SharedPreferences sharedPreferences = context.getSharedPreferences("unlock-data",
                Context.MODE_PRIVATE);

        long unlockTime = new Date().getTime();

        sharedPreferences.edit().putLong("last-unlock", unlockTime).apply();

        showToast(context, "Unlocked");
    }
}
