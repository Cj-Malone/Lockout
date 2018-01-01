package uk.co.keepawayfromfire.lockout;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class AppShortcutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DevicePolicyManager devicePolicyManager = getSystemService(DevicePolicyManager.class);

        ComponentName adminComponentName = new ComponentName(this, AdminReceiver.class);
        if (!devicePolicyManager.isAdminActive(adminComponentName)) {
            Intent intent = new Intent();

            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponentName);

            startActivity(intent);
        } else {
            devicePolicyManager.lockNow();
        }

        finish();
    }
}
