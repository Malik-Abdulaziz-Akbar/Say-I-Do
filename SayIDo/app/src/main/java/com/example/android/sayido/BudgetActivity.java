package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        TextView venue= findViewById(R.id.venue);
        TextView vendor= findViewById(R.id.vendor);
        TextView decor= findViewById(R.id.decor);
        TextView total= findViewById(R.id.price);
        DBHelper db = DBHelper.getDB(this);
        ArrayList<Integer> arr = db.getAllPrices();
        int decor_price=0;
        int vendor_price=0;
        int venue_price=0;
        int total_price = 0;
        vendor_price= arr.get(0);
        venue_price = arr.get(1);
        decor_price = arr.get(2);
        total_price = vendor_price+venue_price+decor_price;
        venue.setText("Venue Price :   "+String.valueOf(venue_price));
        vendor.setText("Vendor Price :  "+String.valueOf(vendor_price));
        decor.setText("Decor Price :   "+String.valueOf(decor_price));
        total.setText("Total Price :   "+String.valueOf(total_price));


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
}
