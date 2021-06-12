package com.example.android.sayido;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Import extends Activity {

    ArrayList<User> usersArrayList;
    int position = 0;
    String urlstring;
    User user;
    ImageView imageView;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        Intent intent = getIntent();
        urlstring = intent.getStringExtra("url");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                load();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        show();
                    }
                });
            }
        });

        thread.start();
    }

    private void load() {

        String line = "";
        TextView view = (TextView) findViewById(R.id.text);

        try {

            URL url = new URL(urlstring);
            //URL url = new URL("http://10.0.2.2/android/pictures.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();

            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            line = content.toString();
            parse(line);
        } catch (Exception ex) {
            line = ex.getMessage();
            ex.printStackTrace();
        }

    }

    private void parse(String xml) {

        String category = "";
        usersArrayList = new ArrayList<>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xml));

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {


                if (event == XmlPullParser.START_TAG &&
                        parser.getName().equals("User")) {

                    String name = parser.getAttributeValue(null, "name");
                    String email = parser.getAttributeValue(null, "email");
                    String phone = parser.getAttributeValue(null, "phone");
                    String password = parser.getAttributeValue(null, "password");
                    usersArrayList.add(new User(name, password,email,phone));
                }

                event = parser.next();
            }
        } catch (Exception ex) {
        }
    }

    private void show() {
        if (usersArrayList != null) {
            user = usersArrayList.get(position);
            if (user == null) return;
            TextView text2 = (TextView) findViewById(R.id.text2);
            text2.setText("Name:" + user.name );

            TextView text = (TextView) findViewById(R.id.text);
            text.setText("Roll Number:" + user.email );
        }
    }

    public void buttonClick(View view) {
        if (view.getId() == R.id.previous) {
            position--;
            if (position < 0) position = usersArrayList.size() - 1;
        }

        if (view.getId() == R.id.next) {
            position = (position + 1) % usersArrayList.size();
        }

        show();
    }

}
