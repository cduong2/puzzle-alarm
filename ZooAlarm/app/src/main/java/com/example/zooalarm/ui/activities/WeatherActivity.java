package com.example.zooalarm.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zooalarm.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class WeatherActivity extends AppCompatActivity {
    private Button mBackButton;
    private static final String TAG = "WeatherActivity";

    EditText etCity, etCountry;
    TextView tvResult;
    private final String cbusURL = "https://api.open-meteo.com/v1/gfs?latitude=39.9612&longitude=-82.9988&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
    private double latitude = 39.9612; //Columbus
    private double longitude = -82.9988; // Columbus
    private String url = "https://api.open-meteo.com/v1/gfs?latitude=" + latitude + " &longitude=" + longitude + "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
//    private final String appid = ""; // not sure if I need this..
//    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_weather);

        //Non Weather API info functionality, buttons
        mBackButton = (Button) findViewById(R.id.home_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    startActivity(new Intent(WeatherActivity.this, MainActivity.class));
                }
            });

        // API Weather Info
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult); // !! Use this!!
        tvResult.setText("Please Enter Information - First Call");

        // API Weather Info Attempt #2
//        OkHttpClient client new OkHttpClient();

        // API Weather Info Attempt #3 - Volley
        url = "https://api.open-meteo.com/v1/gfs?latitude=39.9612&longitude=-82.9988&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tvResult.setText("The Weather:\n" + response.toString());
//                tvResult.setText(response.toString());
//                tvResult.setText("the weather");
//                int i = response.getInt("userId");
//                try {
////                    tvResult.setText(response.toString());
//                    String temperature_2m_max = response.getString("temperature_2m_max") + " *C";
//                    String temperature_2m_min = response.getString("temperature_2m_min");
//
//                    tvResult.setText("Max Temp: " + temperature_2m_max + " *C\nMin Temp: " + temperature_2m_min + " *C");
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvResult.setText("Error: Could not get Weather Info");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


        // Access the RequestQueue through your singleton class.
//        WeatherActivity.getInstance(this).addToRequestQueue(jsonObjectRequest);

//        tvResult.setText("Please Enter Information - Final Call");

//        // attempt #4
//
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        tvResult.setText("Response is: " + response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tvResult.setText("That didn't work! #1");
//            }
//        });
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
        
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        if(city.equals("")){
            tvResult.setText("City field can not be empty!");
        } else {
            if(!country.equals("")){
                double columbus_latitude = 39.9612;
                double columbus_longitude = -82.9988;
                this.latitude = columbus_latitude; //Custom, will replace later
                this.longitude = columbus_longitude; //Custom, will replace later
                this.url = "https://api.open-meteo.com/v1/gfs?latitude=" + latitude + " &longitude=" + longitude + "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
                tempUrl = this.url;
            } else {
                //default city is Columbus
                this.latitude = 39.9612; //Columbus
                this.longitude = -82.9988; // Columbus
                this.url = "https://api.open-meteo.com/v1/gfs?latitude=" + latitude + " &longitude=" + longitude + "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
                tempUrl = this.url;
            }
            tvResult.setText("The Weather...");
        }
    }
}
