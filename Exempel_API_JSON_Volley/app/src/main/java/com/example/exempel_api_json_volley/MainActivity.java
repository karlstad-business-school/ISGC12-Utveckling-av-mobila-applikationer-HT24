package com.example.exempel_api_json_volley;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements VolleyCallback {

    private TextView cityTV, skyTV, tempTV;

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

        cityTV = findViewById(R.id.city_tv);
        skyTV = findViewById(R.id.sky_tv);
        tempTV = findViewById(R.id.temp_tv);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "https://api.openweathermap.org/data/2.5/weather?q=Karlstad&appid=YOU_API_KEY&mode=json";

        APICall api = new APICall();

        api.get(requestQueue, this, url);
        //api.get(requestQueue, this, null, null, null, url);


    }

    @Override
    public void onSuccess(JSONObject object) {
        try {
            String name = object.get("name").toString();
            String temp = object.getJSONObject("main").get("temp").toString();

            JSONArray weather = object.getJSONArray("weather");
            String sky = weather.getJSONObject(0).get("main").toString();

            cityTV.setText(name);
            tempTV.setText(temp);

            skyTV.setText(sky);

        } catch (JSONException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFailure(Exception e) {

    }
}