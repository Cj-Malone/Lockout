package uk.co.keepawayfromfire.lockout;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerUnlockReciever(this);

        SharedPreferences sharedPreferences = getSharedPreferences("unlock-data",
                Context.MODE_PRIVATE);
        String date = DateFormat.getDateTimeInstance().format(
                new Date(sharedPreferences.getLong("last-unlock", 0)));

        TextView textView1 = findViewById(R.id.textView1);

        textView1.setText(date);

    }

    static void registerUnlockReciever(Context context) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");

        context.registerReceiver(new UnlockReceiver(), intentFilter);
    }

}
