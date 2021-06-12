package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {
    private AdView mAdView;
    private Switch wifiSwitch;
    private WifiManager wifiManager;
    Dialog myDialog;
    static int vendor_price;
    static int venue_price;
    static int decor_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if ((savedInstanceState != null) && (savedInstanceState.getSerializable("venue2") != null)) {
            venue_price = (int) savedInstanceState.getSerializable("venue2");
        }
        if ((savedInstanceState != null) && (savedInstanceState.getSerializable("vendor2") != null)) {
            vendor_price = (int) savedInstanceState.getSerializable("vendor2");
        }
        if ((savedInstanceState != null) && (savedInstanceState.getSerializable("decor2") != null)) {
            decor_price = (int) savedInstanceState.getSerializable("decor2");
        }
        Intent intent = getIntent();
        final String ids = intent.getStringExtra("userid");
        if (intent.getStringExtra("venue_price") != null) {
            venue_price = Integer.parseInt(intent.getStringExtra("venue_price"));
        }
        if (intent.getStringExtra("vendor_price") != null) {
            vendor_price = Integer.parseInt(intent.getStringExtra("vendor_price"));
        }
        if (intent.getStringExtra("decor_price") != null) {
            decor_price = Integer.parseInt(intent.getStringExtra("decor_price"));
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        wifiSwitch = findViewById(R.id.wifi_switch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setText("WiFi");
                } else {
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setText("WiFi");
                }
            }
        });

        ArrayList<HomeOptions> homeOptions = new ArrayList<HomeOptions>();

        homeOptions.add(new HomeOptions("Vendors", R.drawable.vendor, 0));
        homeOptions.add(new HomeOptions("Venues", R.drawable.venue, 1));
        homeOptions.add(new HomeOptions("Decors", R.drawable.decors, 2));
        homeOptions.add(new HomeOptions("Budget", R.drawable.budgett, 3));


        OptionsAdapter optionsAdapter = new OptionsAdapter(this, homeOptions, venue_price, vendor_price, decor_price);

        ListView listView = (ListView) findViewById(R.id.homepage);

        listView.setAdapter(optionsAdapter);
        myDialog = new Dialog(this);
        final ImageButton imageButton = findViewById(R.id.imagebtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(HomeActivity.this, MusicService.class));
                String tag = (String) imageButton.getTag();
                if (tag.equals("vol-up")) {
                    imageButton.setTag("vol-down");
                    imageButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
                    stopService(new Intent(HomeActivity.this, MusicService.class));
                } else {
                    startService(new Intent(HomeActivity.this, MusicService.class));
                    imageButton.setImageResource(R.drawable.ic_volume_up_black_24dp);
                    imageButton.setTag("vol-up");
                }

            }
        });
        final ImageButton imageButton2 = findViewById(R.id.imagebtn2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (ids != null) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);

                    intent.putExtra("userid1", ids);
                    startActivity(intent);
                }
            }
        });

    }

    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.custompopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        Button btnimportexport = (Button) myDialog.findViewById(R.id.btnimportexport);
        btnimportexport.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, ImpExp.class));

            }
        });
        Button btnlogout = (Button) myDialog.findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, LoginActivity.class));

            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("venue2", vendor_price);
        state.putSerializable("vendor2", venue_price);
        state.putSerializable("decor2", decor_price);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        venue_price = (int) savedInstanceState.getSerializable("venue2");
        vendor_price = (int) savedInstanceState.getSerializable("vendor2");
        decor_price = (int) savedInstanceState.getSerializable("decor2");

    }

    @Override
    public void onPause() {
        if (isApplicationSentToBackground(this)) {
            // Do what you want to do on detecting Home Key being Pressed
            final ImageButton imageButton = findViewById(R.id.imagebtn);
            imageButton.setTag("vol-down");
            imageButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
            stopService(new Intent(HomeActivity.this, MusicService.class));

        }
        super.onPause();
    }

    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked(true);

                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked(false);

                    break;
            }
        }
    };


}
