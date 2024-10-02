package com.example.exempel_api_xml;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "YOU_API_KEY";
    private TextView cityText, sunText, setText, tempText;
    private EditText cityET;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        cityText = findViewById(R.id.tv_city);
        sunText = findViewById(R.id.tv_sun);
        setText = findViewById(R.id.tv_set);
        tempText = findViewById(R.id.tv_temp);
        cityET = findViewById(R.id.city_ET);
        searchButton = findViewById(R.id.search_btn);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });



    }

    private void getData(){
        String city = "Karlstad";

        city = cityET.getText().toString();

        URL url;
        String apiString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&mode=xml";
        Log.e("TAG", apiString);
        try {
            url = new URL(apiString);

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);


            int parserEvent = parser.getEventType();
            String tagName = "";

            Log.e("TAG", "getData: ");

            while(parserEvent != XmlPullParser.END_DOCUMENT){
                if(parserEvent == XmlPullParser.START_TAG){
                    tagName = parser.getName();
                    if(tagName.contains("city")){
                        String name = parser.getAttributeValue(null, "name");

                        cityText.setText("City: " + name);
                    }

                    if(tagName.contains("sun")){
                        String rise = parser.getAttributeValue(0);
                        String set = parser.getAttributeValue(1);

                        sunText.setText("Sun rise: " + rise);
                        setText.setText("Sun set: " + set);
                    }

                    if(tagName.contains("temperature")){
                        String temp = parser.getAttributeValue(null, "value");
                        float tempFloat = Float.parseFloat(temp);
                        float c = tempFloat - 273.15f;
                        tempText.setText("Temperature: " + c + "C");
                    }

                    Log.e("Tag name",  tagName);
                }


                parserEvent = parser.next();
            }
        }catch(Exception e){

        }
    }
}