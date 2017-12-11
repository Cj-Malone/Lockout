package uk.co.keepawayfromfire.lockout;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.service.quicksettings.TileService;

/**
 * Created by cj on 10/12/17.
 */

public class LockoutService extends TileService {

    @Override
    public void onClick() {
        super.onClick();

        DevicePolicyManager devicePolicyManager = getSystemService(DevicePolicyManager.class);

        ComponentName adminComponentName = new ComponentName(this, AdminReceiver.class);
        if (!devicePolicyManager.isAdminActive(adminComponentName)) {
            Intent intent = new Intent();
//            try {
//                intent.setClassName("com.android.settings",
//                        "com.android.settings.DeviceAdminAdd");
//                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminInfo);
//            } catch (Exception e) {
//                Log.e("ERROR", e.toString());
//
            intent.setClassName("com.android.settings", "com.android.settings.DeviceAdminSettings");
//            }

            this.startActivityAndCollapse(intent);
        } else {
            devicePolicyManager.lockNow();
        }
    }
}
