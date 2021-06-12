package com.example.android.sayido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Exporting extends Activity {

    Handler handler = new Handler();
    String urlstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exporting);
        Intent intent = getIntent();
        urlstring = intent.getStringExtra("url");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                post();

            }
        });

        thread.start();
    }
    public String post(){

        StringBuilder content = new StringBuilder();

        try{

            URL url = new URL(urlstring);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type","text/xml");
            connection.setDoOutput(true);
            connection.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(getStudentContent());
            writer.flush();

            BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );

            String line = "";
            while( (line = reader.readLine()) != null ){
                content.append(line);
            }

            connection.disconnect();
            TextView textView = findViewById(R.id.text3);
            textView.setText("The data is exported successfully");

        } catch(Exception ex) {
            TextView textView = findViewById(R.id.text3);
            textView.setText("Url Connection can not be formed");
            ex.printStackTrace();
        }

        return content.toString();
    }

    public String getStudentContent(){


        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.android.sayido/users"),null,null,null,null);

        StringBuilder result = new StringBuilder();
        while (cursor != null && cursor.moveToNext()){
            String name = cursor.getString(1);
            String email = cursor.getString(3);
            String phone = cursor.getString(4);
            result.append ("<User Name='" + name + "' Email='" + name + "' Phone_No='" + phone + "'/>");
        }

        return "<?xml version=\\'1.0\\' encoding=\\'UTF-8\\' standalone=\\'yes\\' ?><SayIDo Version = '1.0' >" + result.toString() + "</SayIDo";
    }
}
