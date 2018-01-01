package uk.co.keepawayfromfire.lockout;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().setStatusBarColor(android.R.attr.colorPrimary);
        getWindow().setNavigationBarColor(android.R.attr.colorPrimary);

        SharedPreferences sharedPreferences = getSharedPreferences("unlock-data",
                Context.MODE_PRIVATE);
        String date = DateFormat.getDateTimeInstance().format(
                new Date(sharedPreferences.getLong("last-unlock", 0)));

        TextView tvLastUnlock = findViewById(R.id.tvLastUnlock);
        tvLastUnlock.setText(date);

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
