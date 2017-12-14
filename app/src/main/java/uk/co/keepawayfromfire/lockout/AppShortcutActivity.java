package uk.co.keepawayfromfire.lockout;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;

public class AppShortcutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DevicePolicyManager devicePolicyManager = getSystemService(DevicePolicyManager.class);

        ComponentName adminComponentName = new ComponentName(this, AdminReceiver.class);
        if (!devicePolicyManager.isAdminActive(adminComponentName)) {
            startActivity(QuickSettingService.buildAdminSettingsIntent());
        } else {
            devicePolicyManager.lockNow();
        }

        finish();
    }
}
