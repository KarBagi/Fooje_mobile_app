package com.example.fooje;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostApiRequest extends AsyncTask<Void, Void, String> {

    private String apiUrl;
    private String jsonInputString;
    private int restaurantId, clientId;
    int a;

    public void setOptions(String apiUrl, String jsonInputString) {
        this.apiUrl = apiUrl;
        this.jsonInputString = jsonInputString;
    }

    public void setOptionsDeleteFavorites(String apiUrl, int restaurantId, int clientId){
        this.apiUrl = apiUrl;
        this.restaurantId = restaurantId;
        this.clientId = clientId;
        this.a = 1;
    }

    public void setOptionsDeleteCart(String apiUrl, int clientId){
        this.apiUrl = apiUrl;
        this.clientId = clientId;
        this.a = 2;
    }

    public void setOptionOrderStatus(String apiUrl){
        this.apiUrl = apiUrl;
        this.a = 3;
    }

    public void setOptionUpdateClient(String apiUrl, String jsonInputString){
        this.apiUrl = apiUrl;
        this.jsonInputString = jsonInputString;
        this.a = 4;
    }


    @Override
    protected String doInBackground(Void... voids) {

        if (a == 1 || a == 2) {
            try {
                if(a == 1)
                    apiUrl += "/" + clientId + "/" + restaurantId;
                else if(a == 2)
                    apiUrl += "/" + clientId;
                URL url = new URL(apiUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("DELETE");

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        return response.toString();
                    }
                } else {
                    return "Błąd podczas usuwania z ulubionych. Kod odpowiedzi: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else if(a == 3 || a == 4) {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                if(a == 4){
                    httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                    httpURLConnection.setDoOutput(true);
                }

                httpURLConnection.setRequestMethod("PUT");

                try {
                    BufferedReader br = null;
                    if(a == 3)
                        br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                    else if(a == 4)
                        br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                httpURLConnection.setDoOutput(true);

                try (OutputStream os = httpURLConnection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try (BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());

                    }
                    return response.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            System.out.println("Response from server: " + result);
        } else {
            System.out.println("Error during HTTP request");
        }
    }
}
