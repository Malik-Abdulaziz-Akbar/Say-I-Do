package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DecorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decors);
        ArrayList<DecorOptions> DecorOptions = new ArrayList<DecorOptions>();
        final DBHelper db = DBHelper.getDB(this);

        DecorOptions.add(new DecorOptions("Chateaux Vaux-le-Vicomte, Maincy,"," France",R.drawable.france,100000));
        DecorOptions.add(new DecorOptions("Laucala Island Resort,"," Fiji", R.drawable.fiji,1300000));
        DecorOptions.add(new DecorOptions("Little Palm Island, Key West,"," Florida", R.drawable.florida,1350000));
        DecorOptions.add(new DecorOptions("Pelican Hill, Newport Beach,"," California", R.drawable.california,1500000));
        DecorOptions.add(new DecorOptions("Musha Cay,"," Bahamas", R.drawable.bahamas,1400000));
        DecorOptions.add(new DecorOptions("Hotel Caruso, Ravello,"," Italy", R.drawable.italy,100000));
        DecorOptions.add(new DecorOptions("Oberoi Udaivilas, Udaipur,"," India", R.drawable.india,2300000));
        DecorOptions.add(new DecorOptions("The Biltmore Estate,"," North Carolina", R.drawable.northcarolina,1300000));
        DecorOptions.add(new DecorOptions("Umaid Bhawan Palace Jodhpur Rajasthan,"," India", R.drawable.rajasthanindia,20000));
        DecorOptions.add(new DecorOptions("MolenVliet Wine Estate, Stellenbosch,"," South Africa", R.drawable.southafrica,240000));

       db.insertDecor(new DecorOptions("Chateaux Vaux-le-Vicomte, Maincy,"," France",R.drawable.france,100000));
        db.insertDecor(new DecorOptions("Laucala Island Resort,"," Fiji", R.drawable.fiji,1300000));
        db.insertDecor(new DecorOptions("Little Palm Island, Key West,"," Florida", R.drawable.florida,1350000));
        db.insertDecor(new DecorOptions("Pelican Hill, Newport Beach,"," California", R.drawable.california,1500000));
        db.insertDecor(new DecorOptions("Musha Cay,"," Bahamas", R.drawable.bahamas,1400000));
        db.insertDecor(new DecorOptions("Hotel Caruso, Ravello,"," Italy", R.drawable.italy,100000));
        db.insertDecor(new DecorOptions("Oberoi Udaivilas, Udaipur,"," India", R.drawable.india,2300000));
        db.insertDecor(new DecorOptions("The Biltmore Estate,"," North Carolina", R.drawable.northcarolina,1300000));
        db.insertDecor(new DecorOptions("Umaid Bhawan Palace Jodhpur Rajasthan,"," India", R.drawable.rajasthanindia,20000));
        db.insertDecor(new DecorOptions("MolenVliet Wine Estate, Stellenbosch,"," South Africa", R.drawable.southafrica,240000));

        final DecorsAdapter optionsAdapter = new DecorsAdapter(this, DecorOptions);

        ListView listView = (ListView) findViewById(R.id.decorpage);

        listView.setAdapter(optionsAdapter);

        ImageView save  =  (ImageView) findViewById(R.id.selectedtick);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(DecorActivity.this, "Total Decors Selected "+"-"+String.valueOf(optionsAdapter.selectedOptions.size()), Toast.LENGTH_SHORT).show();
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

}
