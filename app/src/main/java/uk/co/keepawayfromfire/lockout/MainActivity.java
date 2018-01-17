package uk.co.keepawayfromfire.lockout;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final DevicePolicyManager devicePolicyManager = getSystemService(DevicePolicyManager.class);
        final ComponentName adminComponentName = new ComponentName(this, AdminReceiver.class);

        CheckBox cbAdmin = findViewById(R.id.cbAdmin);
        cbAdmin.setOnClickListener(null);
        cbAdmin.setChecked(devicePolicyManager.isAdminActive(adminComponentName));
        cbAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (devicePolicyManager.isAdminActive(adminComponentName)) {
                    intent.setClassName("com.android.settings", "com.android.settings.DeviceAdminSettings");
                } else {
                    intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponentName);
                }

                view.getContext().startActivity(intent);
            }
        });
    }
}
