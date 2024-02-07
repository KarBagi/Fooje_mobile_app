package com.example.fooje;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestaurantGetApiRequest extends AsyncTask<Void, Void, String> {

    private String apiUrl;
    private double latitude; // Nazwa kolumny do filtrowania
    private double longitude;
    private int distance;

    public void setOptions(String apiUrl, double latitude, double longitude, int distance) {
        this.apiUrl = apiUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String endpointUrl = apiUrl +
                    "?latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&dystans=" + distance;

            URL url = new URL(endpointUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();

                return response.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            // Reakcja na pomyślne wykonanie żądania
            Log.d("RestaurationApiRequest", "Odpowiedź: " + result);
        } else {
            // Reakcja na błąd wykonania żądania
            Log.e("RestaurationApiRequest", "Błąd wykonania żądania");
        }
    }
}
