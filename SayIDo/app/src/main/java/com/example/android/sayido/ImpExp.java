package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ImpExp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_exp);
        final DBHelper db = DBHelper.getDB(this);
        final RadioGroup radioGroup = findViewById(R.id.radio3);
        TextView updatebtn = findViewById(R.id.impexpbtn);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                //Create the intent to start another activity
                EditText editText = findViewById(R.id.edit_text10);
                String text = editText.getText().toString();
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                String radio_text = (String) radioButton.getText();
                String s = "Enter Something";
                if (text.equals("")) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
                else{
                    if(radio_text.equals("Import")){
                        Intent startIntent = new Intent(getApplicationContext(), Import.class);
                        startIntent.putExtra("url", text);
                        startActivity(startIntent);
                    }
                    else{
                        Intent startIntent = new Intent(getApplicationContext(), Exporting.class);
                        startIntent.putExtra("url", text);
                        startActivity(startIntent);
                    }
                }
            }

        });
    }
}
