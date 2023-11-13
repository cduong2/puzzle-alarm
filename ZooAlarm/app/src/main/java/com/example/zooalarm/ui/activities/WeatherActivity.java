package com.example.zooalarm.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.zooalarm.Manifest;
import com.example.zooalarm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class WeatherActivity extends AppCompatActivity {
    private Button mBackButton;
    private static final String TAG = "WeatherActivity";

//    EditText etCity, etCountry;
    EditText etCity;
    TextView tvResult;
    private double latitude = 39.9612; //Columbus
    private double longitude = -82.9988; // Columbus
    private String cityIn = "";
    private String countryIn = "";
    private String url = "https://api.open-meteo.com/v1/gfs?latitude=" + latitude + " &longitude=" + longitude + "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";

    //location
    FusedLocationProviderClient fusedLocationProviderClient;
    Button btnGet;
    private final static int REQUEST_CODE=100;

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
//        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult); // !! Use this!!
        tvResult.setText("Please Enter The Information");

        //location
//        btnGet = (Button) findViewById(R.id.btnGet);
        
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
//        String country = etCountry.getText().toString().trim();
        if(city.equals("")){
            tvResult.setText("City field can not be empty!");
        } else {
            tvResult.setText("One second while we get the weather data...");

            //location
//            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//            btnGet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getLastLocation();
//                }
//            });
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> ads = geocoder.getFromLocationName(city,1);
//                List<Address> ads = geocoder.getFromLocationName("Columbus",1);
//                addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                tvResult.setText("\nCity: " + ads.get(0).getAddressLine(0));
//                tvResult.append("\nLongitude: " + ads.get(0).getLongitude());
//                tvResult.append("\nLatitude: " + ads.get(0).getLatitude());
                cityIn = ads.get(0).getAddressLine(0);
                longitude = ads.get(0).getLongitude();
                latitude = ads.get(0).getLatitude();
//                String city1 = ads.get(0).getLocality();
//                String state1 = ads.get(0).getAdminArea();
//                String country1 = ads.get(0).getCountryName();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //Successful Weather API call!!!
            url = "https://api.open-meteo.com/v1/gfs?latitude=39.9612&longitude=-82.9988&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
//            url = "https://jsonplaceholder.typicode.com/todos/1";
//            url = "https://api.open-meteo.com/v1/gfs?latitude=39.9612&longitude=-82.9988&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
//            String lat = Double.toString(39.9612); //Columbus, can substitute for location later.
//            String lon = Double.toString(-82.9988); // Columbus
            String lat = Double.toString(latitude);
            String lon = Double.toString(longitude);
            url = "https://api.open-meteo.com/v1/gfs?latitude=" + lat + "&longitude=" + lon + "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,rain_sum,snowfall_sum,precipitation_hours&wind_speed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    tvResult.setText("The Weather:\n" + response.toString());
                    try {
                        JSONObject daily = response.getJSONObject("daily");
                        JSONObject daily_units = response.getJSONObject("daily_units");
                        tvResult.setText("The Weather:\n");
                        tvResult.append("\nDate: " + daily.get("time").toString());
                        tvResult.append("\nLocation: " + cityIn);//from Location
                        tvResult.append("\nLongitude, Latitude: " + lon + ", " + lat);
//                        tvResult.append("\nTime Zone: " + response.getString("timezone") + " (" + response.getString("timezone_abbreviation") + ")");
                        tvResult.append("\n\nMax Temp: " + daily.get("temperature_2m_max").toString() + daily_units.get("temperature_2m_max").toString());
                        tvResult.append("\nMin Temp: " + daily.get("temperature_2m_min").toString() + daily_units.get("temperature_2m_min").toString());
                        tvResult.append("\n\nSunrise: " + daily.get("sunrise").toString());
                        tvResult.append("\nSunset: " + daily.get("sunset").toString());
                        tvResult.append("\n\nPrecipitation Sum : " + daily.get("precipitation_sum").toString() + " " + daily_units.get("precipitation_sum").toString());
                        tvResult.append("\nRain Sum : " + daily.get("rain_sum").toString() + " " + daily_units.get("rain_sum").toString());
                        tvResult.append("\nSnowfall Sum : " + daily.get("snowfall_sum").toString() + " " + daily_units.get("snowfall_sum").toString());
                }
                catch (JSONException e) {
                    tvResult.setText("Caught... Failed...");
                    e.printStackTrace();
                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tvResult.setText("Error: Could not get Weather Info. \nMAKE SURE YOU ARE CONNECTED TO WIFI.");
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }

//    private void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if(location != null){
//                        Geocoder geocoder = new Geocoder(WeatherActivity.this, Locale.getDefault());
//                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
////                        tvResult.setText("Latitude: " + addresses.get(0).getLatitude());
////                        tvResult.setText("Longitude: " + addresses.get(0).getLongitude());
//                        latitude = addresses.get(0).getLatitude();
//                        longitude = addresses.get(0).getLongitude();
//                        cityIn = addresses.get(0).getLocality();
//                        countryIn = addresses.get(0).getCountryName();
//                    }
//                }
//            });
//        }
//    }
}