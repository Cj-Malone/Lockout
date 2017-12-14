package uk.co.keepawayfromfire.lockout;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cj on 14/12/17.
 */

public class TaskerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DevicePolicyManager devicePolicyManager = context.getSystemService(DevicePolicyManager.class);

        ComponentName adminComponentName = new ComponentName(context, AdminReceiver.class);
        if (!devicePolicyManager.isAdminActive(adminComponentName)) {
            context.startActivity(LockoutService.buildAdminSettingsIntent());
        } else {
            devicePolicyManager.lockNow();
        }
    }
}
