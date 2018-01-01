package uk.co.keepawayfromfire.lockout;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.service.quicksettings.TileService;

/**
 * Created by cj on 10/12/17.
 */

public class QuickSettingService extends TileService {

    @Override
    public void onClick() {
        super.onClick();

        DevicePolicyManager devicePolicyManager = getSystemService(DevicePolicyManager.class);

        ComponentName adminComponentName = new ComponentName(this, AdminReceiver.class);
        if (!devicePolicyManager.isAdminActive(adminComponentName)) {
            Intent intent = new Intent();
            // intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            // intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponentName);
            intent.setClassName("com.android.settings", "com.android.settings.DeviceAdminSettings");

            this.startActivityAndCollapse(intent);
        } else {
            devicePolicyManager.lockNow();
        }
    }
}
