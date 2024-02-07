package com.example.fooje;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public String parseValueFromJson(String jsonString, String data) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            String value = jsonObject.getString(data);

            return value;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
