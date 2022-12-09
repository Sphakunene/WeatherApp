package com.buildappwithcoleen.WeatherApp.Controller;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.IOException;

@Service
public class WeatherService {
    private OkHttpClient client;
    private Response response;

    private String cityName;

    private  String unit;


    public JSONObject getWeather() throws IOException {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+getCityName()+"&units="+getUnit()+"&appid=ad32e01c3300a131bc7e1bd30ad7612d")
                .build();
        response = client.newCall(request).execute();
        try {

            return new JSONObject(response.body().string());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    public JSONArray returnWeatherArray(){
        try {
            JSONArray jsonArray = this.getWeather().getJSONArray("weather");
            return jsonArray;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public JSONObject getMainObject(){

        try {
            JSONObject jsonObject = this.getWeather().getJSONObject("main");
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public JSONObject getWindObject(){

        try {
            JSONObject jsonObject = this.getWeather().getJSONObject("wind");
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public JSONObject getSunSetObject(){

        try {
            JSONObject jsonObject = this.getWeather().getJSONObject("sys");
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
