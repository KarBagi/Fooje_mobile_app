package com.example.fooje;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetApiRequest extends AsyncTask<String, Void, String[]> {

    private String apiUrl;
    private String columnName;
    private double latitude;
    private double longitude;
    private double distance;
    int clientId;
    int restaurantId;
    int a;

    public void setOptions(String apiUrl, String columnName) {
        this.apiUrl = apiUrl;
        this.columnName = columnName;
    }

    public void setOptionsToGetRestaurants(String apiUrl, double latitude, double longitude, double distance){
        this.apiUrl = apiUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.a = 1;
    }

    public void setOptionsProducts(String apiUrl, int restaurantId){
        this.apiUrl = apiUrl;
        this.restaurantId = restaurantId;
        this.a = 2;
    }

    public void setOptionsFavoritesRestaurants(String apiUrl, int clientId){
        this.apiUrl = apiUrl;
        this.clientId = clientId;
        this.a = 3;
    }

    public void setOptionsGetFavoriteRestaurantsDetails(String apiUrl){
        this.apiUrl = apiUrl;
        this.a = 4;
    }

    public void setOptionsCartProducts(String apiUrl){
        this.apiUrl = apiUrl;
        this.a = 5;
    }

    public void setOptionsGetProducts(String apiUrl){
        this.apiUrl = apiUrl;
        this.a = 6;
    }

    public void setOptionsOrders(String apiUrl){
        this.apiUrl = apiUrl;
        this.a = 7;
    }

    public void setOptionsOrdersDetails(String apiUrl){
        this.apiUrl = apiUrl;
        this.a = 8;
    }

    @Override
    protected String[] doInBackground(String... params) {
        if (a == 1) {
            apiUrl += "?latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&distanceInMeters=" + distance;
        }
        else if(a == 2){
            apiUrl += "?restaurantId=" + restaurantId;
        }
        else if(a == 3){
            apiUrl += "?clientId=" + clientId;
        }

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader br;
                if(a == 1 || a == 2 || a == 6){
                    br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                }
                else {
                    br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                }
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    if(a == 1 || a == 2 || a == 6 || a == 7 || a == 8){
                        response.append(responseLine);
                    }
                    else response.append(responseLine.trim());
                }

                if(a == 1 || a == 2 || a == 3 || a == 4 || a == 5 || a == 6 || a == 7 || a == 8)
                {
                    String[] responseStringTable = {response.toString()};
                    return responseStringTable;
                }else{
                    return extractColumnValues(response.toString(), columnName);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null) {
            for (String value : result) {
                Log.d("ApiGetRequest", columnName + ": " + value);
            }
        } else {
            Log.e("ApiGetRequest", "Error during HTTP request");
        }
    }

    private String[] extractColumnValues(String jsonResponse, String columnName) {

        String[] values = new String[0];

        if(!jsonResponse.startsWith("[")){
            jsonResponse = "[" + jsonResponse + "]";
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            values = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                values[i] = jsonObject.optString(columnName, "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return values;
    }

    public List<List<Object>> convertJsonToList(String jsonString) {
        List<List<Object>> resultList = new ArrayList<>();

        if(!jsonString.startsWith("[") || a == 4){
            jsonString = "[" + jsonString + "]";
        }

        try {
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                List<Object> restauration = new ArrayList<>();

                if(a == 1 || a == 4){
                    restauration.add(jsonObject.get("restaurantId").getAsLong());
                    restauration.add(jsonObject.get("restaurantName").getAsString());
                    restauration.add(jsonObject.get("name").getAsString());
                    restauration.add(jsonObject.get("surname").getAsString());
                    restauration.add(jsonObject.get("email").getAsString());
                    restauration.add(jsonObject.get("phoneNumber").getAsString());
                    restauration.add(jsonObject.get("streetAddress").getAsString());
                    restauration.add(jsonObject.get("city").getAsString());
                    restauration.add(jsonObject.get("webPage").getAsString());
                    restauration.add(jsonObject.get("foodType").getAsString());
                    restauration.add(jsonObject.get("animalInfo").getAsString());
                    restauration.add(jsonObject.get("toiletInfo").getAsString());
                    restauration.add(jsonObject.get("carInfo").getAsString());
                    restauration.add(jsonObject.get("hoursMonInfo").getAsString());
                    restauration.add(jsonObject.get("hoursTueInfo").getAsString());
                    restauration.add(jsonObject.get("hoursWenInfo").getAsString());
                    restauration.add(jsonObject.get("hoursThuInfo").getAsString());
                    restauration.add(jsonObject.get("hoursFriInfo").getAsString());
                    restauration.add(jsonObject.get("hoursSatInfo").getAsString());
                    restauration.add(jsonObject.get("hoursSunInfo").getAsString());
                }
                else if(a == 2 || a == 6){
                    restauration.add(jsonObject.get("productId").getAsLong());
                    restauration.add(jsonObject.get("restaurantId").getAsLong());
                    restauration.add(jsonObject.get("name").getAsString());
                    restauration.add(jsonObject.get("description").getAsString());
                    restauration.add(jsonObject.get("weight").getAsString());
                    restauration.add(jsonObject.get("price").getAsString());
                    restauration.add(jsonObject.get("ingredients").getAsString());
                    restauration.add(jsonObject.get("fat").getAsString());
                    restauration.add(jsonObject.get("carbon").getAsString());
                    restauration.add(jsonObject.get("sugar").getAsString());
                    restauration.add(jsonObject.get("protein").getAsString());
                    restauration.add(jsonObject.get("salt").getAsString());
                    restauration.add(jsonObject.get("fiber").getAsString());
                }
                else if(a == 3){
                    restauration.add(jsonObject.get("restaurantId").getAsLong());
                }
                else if(a == 5) {
                    restauration.add(jsonObject.get("cartId"));
                    restauration.add(jsonObject.get("clientId"));
                    restauration.add(jsonObject.get("productId"));
                }
                else if(a == 7){
                    restauration.add(jsonObject.get("ordersId").getAsLong());
                    restauration.add(jsonObject.get("restaurantId").getAsLong());
                    restauration.add(jsonObject.get("clientId").getAsString());
                    restauration.add(jsonObject.get("state").getAsString());
                    restauration.add(jsonObject.get("orderDate").getAsString());
                }
                else if(a == 8){
                    restauration.add(jsonObject.get("ordersDetailsId").getAsLong());
                    restauration.add(jsonObject.get("ordersId").getAsLong());
                    restauration.add(jsonObject.get("productId").getAsString());
                }

                resultList.add(restauration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}