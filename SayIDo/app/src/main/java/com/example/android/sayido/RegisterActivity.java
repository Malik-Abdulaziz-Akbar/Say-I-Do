package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name = (EditText) findViewById(R.id.name);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText confirm_password = (EditText) findViewById(R.id.confirm_password);
        final DBHelper db = DBHelper.getDB(this);
        TextView signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Name: " + name.getText() + "\nEmail: " + email.getText() + "\nPhone: " + phone.getText() + "\nPassword: " + password.getText() + "\nConfirm_password: " + confirm_password.getText(),
                        Toast.LENGTH_SHORT);
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                String mail = email.getText().toString().trim();
                if (!(name.length() > 0) || !(email.length() > 0) || !(phone.length() > 0) || !(password.length() > 0) || !(confirm_password.length() > 0)) {
                    Toast.makeText(getApplicationContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show();

                } else {
                    if (!(mail.matches(emailPattern))) {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!((phone.getText().toString().matches("03[0-9]{9}")))) {
                            Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!(password.getText().toString().contains(confirm_password.getText().toString()) && (password.length() > 0))) {
                                Toast.makeText(getApplicationContext(),
                                        "Password and Confirm_password does not match",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                User u = new User(name.getText().toString(),password.getText().toString(),email.getText().toString(),phone.getText().toString());
                                if(db.emailExist(u)){
                                    Toast.makeText(getApplicationContext(), "Email Already exists Please enter a unique email", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    db.insertUser(u);
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                            }
                        }

                    }

                }


            }
        });
        TextView LogIn = (TextView) findViewById(R.id.LogIn);
        LogIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }

    @Override
    public void onPause() {
        if (isApplicationSentToBackground(this)) {
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
