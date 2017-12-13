package uk.co.keepawayfromfire.lockout;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
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

        registerUnlockReciever(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("unlock-data",
                Context.MODE_PRIVATE);
        String date = DateFormat.getDateTimeInstance().format(
                new Date(sharedPreferences.getLong("last-unlock", 0)));

        TextView tvLastUnlock = findViewById(R.id.tvLastUnlock);
        tvLastUnlock.setText(date);

        DevicePolicyManager devicePolicyManager = getSystemService(DevicePolicyManager.class);
        ComponentName adminComponentName = new ComponentName(this, AdminReceiver.class);

        CheckBox cbAdmin = findViewById(R.id.cbAdmin);
        cbAdmin.setOnClickListener(null);
        cbAdmin.setChecked(devicePolicyManager.isAdminActive(adminComponentName));
        cbAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(LockoutService.buildAdminSettingsIntent());
            }
        });
    }

    static void registerUnlockReciever(Context context) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");

        context.registerReceiver(new UnlockReceiver(), intentFilter);
    }

}
