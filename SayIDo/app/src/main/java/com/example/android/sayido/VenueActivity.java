package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VenueActivity extends Activity {
    private Switch wifiSwitch;
    private WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);

        wifiSwitch = findViewById(R.id.wifi_switch3);
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

        ArrayList<VenueOptions> venueOptions = new ArrayList<VenueOptions>();
        final DBHelper db = DBHelper.getDB(this);

        venueOptions.add(new VenueOptions("Chateaux Vaux-le-Vicomte, Maincy,"," France",R.drawable.france,1300));
        venueOptions.add(new VenueOptions("Laucala Island Resort,"," Fiji", R.drawable.fiji,1300));
        venueOptions.add(new VenueOptions("Little Palm Island, Key West,"," Florida", R.drawable.florida,1500));
        venueOptions.add(new VenueOptions("Pelican Hill, Newport Beach,"," California", R.drawable.california,2000));
        venueOptions.add(new VenueOptions("Musha Cay,"," Bahamas", R.drawable.bahamas,2300));
        venueOptions.add(new VenueOptions("Hotel Caruso, Ravello,"," Italy", R.drawable.italy,2100));
        venueOptions.add(new VenueOptions("Oberoi Udaivilas, Udaipur,"," India", R.drawable.india,2000));
        venueOptions.add(new VenueOptions("The Biltmore Estate,"," North Carolina", R.drawable.northcarolina,1200));
        venueOptions.add(new VenueOptions("Umaid Bhawan Palace Jodhpur Rajasthan,"," India", R.drawable.rajasthanindia,2000));
        venueOptions.add(new VenueOptions("MolenVliet Wine Estate, Stellenbosch,"," South Africa", R.drawable.southafrica,2000));

        db.insertVenue(new VenueOptions("Chateaux Vaux-le-Vicomte, Maincy,"," France",R.drawable.france,1300));
        db.insertVenue(new VenueOptions("Laucala Island Resort,"," Fiji", R.drawable.fiji,1300));
        db.insertVenue(new VenueOptions("Little Palm Island, Key West,"," Florida", R.drawable.florida,1500));
        db.insertVenue(new VenueOptions("Pelican Hill, Newport Beach,"," California", R.drawable.california,2000));
        db.insertVenue(new VenueOptions("Musha Cay,"," Bahamas", R.drawable.bahamas,2300));
        db.insertVenue(new VenueOptions("Hotel Caruso, Ravello,"," Italy", R.drawable.italy,2100));
        db.insertVenue(new VenueOptions("Oberoi Udaivilas, Udaipur,"," India", R.drawable.india,2000));
        db.insertVenue(new VenueOptions("The Biltmore Estate,"," North Carolina", R.drawable.northcarolina,1200));
        db.insertVenue(new VenueOptions("Umaid Bhawan Palace Jodhpur Rajasthan,"," India", R.drawable.rajasthanindia,2000));
        db.insertVenue(new VenueOptions("MolenVliet Wine Estate, Stellenbosch,"," South Africa", R.drawable.southafrica,2000));

        final VenuesAdapter optionsAdapter = new VenuesAdapter(this, venueOptions);

        ListView listView = (ListView) findViewById(R.id.venuepage);

        listView.setAdapter(optionsAdapter);
        ImageView save  =  (ImageView) findViewById(R.id.selectedtick);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(VenueActivity.this, "Total Venues Selected "+"-"+String.valueOf(optionsAdapter.selectedOptions.size()), Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
    @Override
    public void onPause() {
        if (isApplicationSentToBackground(this)){
            // Do what you want to do on detecting Home Key being Pressed
            final ImageButton imageButton = findViewById(R.id.imagebtn);
            imageButton.setTag("vol-down");
            imageButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
            stopService(new Intent(this, MusicService.class));

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
