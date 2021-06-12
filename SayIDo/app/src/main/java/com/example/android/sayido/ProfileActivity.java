package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final DBHelper db = DBHelper.getDB(this);
        Intent intent  =getIntent();
        int id = Integer.parseInt(intent.getStringExtra("userid1"));
        User u = db.getUser(id);
        TextView textView1 = findViewById(R.id.tv4);
        TextView textView2 = findViewById(R.id.tv5);
        TextView textView3 = findViewById(R.id.tv6);
        textView1.setText(u.name);
        textView2.setText(u.email);
        textView3.setText(u.phone);

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
