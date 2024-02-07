package com.example.fooje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    AlertDialog registerPanelAlertDialog;
    PostApiRequest postApiRequest = new PostApiRequest();
    String[] result;
    List<String> foodTypes = new ArrayList<>();
    public static int clientId, restaurationId;
    public static String apiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiUrl = readFirstLine(this);

        Button loginButton = findViewById(R.id.loginButton);
        EditText loginTextEdit = findViewById(R.id.emailTextEdit);
        EditText passwordTextEdit = findViewById(R.id.passwordTextEdit);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = loginTextEdit.getText().toString();
                String password = passwordTextEdit.getText().toString();

                new LoginTask().execute(username, password);
            }
        });
    }

    public void loginAsClient() {
        Intent intent = new Intent(this, ClientsActivity.class);
        startActivity(intent);

        finish();
    }

    public void loginAsRestauration() {
        Intent intent = new Intent(this, RestaurationActivity.class);
        startActivity(intent);

        finish();
    }

    public void clientRegisterAlertPanel(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alertView = LayoutInflater.from(this).inflate(R.layout.client_register_alert, null);
        builder.setView(alertView);

        registerPanelAlertDialog = builder.create();

        ImageButton closeButton = alertView.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(v -> registerPanelAlertDialog.dismiss());

        LinearLayout registerButton = alertView.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            EditText nameEditText = alertView.findViewById(R.id.nameTextEdit);
            EditText lastnameEditText = alertView.findViewById(R.id.surnameTextEdit);
            EditText emailEditText = alertView.findViewById(R.id.emailTextEdit);
            EditText passwordEditText = alertView.findViewById(R.id.passwordTextEdit);
            EditText phoneNumberEditText = alertView.findViewById(R.id.phoneNumberTextEdit);

            String jsonInputString = "{" +
                    " \"name\": \"" + nameEditText.getText().toString() + "\", " +
                    "\"lastname\": \"" + lastnameEditText.getText().toString() + "\", " +
                    " \"email\": \"" + emailEditText.getText().toString() + "\", " +
                    " \"password\": \"" + passwordEditText.getText().toString() + "\", " +
                    " \"phoneNumber\" : \"" + phoneNumberEditText.getText().toString() + "\" " +
                    " }";

            postApiRequest.setOptions(apiUrl + "clients/add", jsonInputString);
            postApiRequest.execute();

            registerPanelAlertDialog.dismiss();
        });


        registerPanelAlertDialog.show();
    }

    public void restaurationRegisterAlertPanel(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alertView = LayoutInflater.from(this).inflate(R.layout.restauration_register_alert, null);
        builder.setView(alertView);

        try {
            GetApiRequest foodTypeGetApiRequest = new GetApiRequest();
            foodTypeGetApiRequest.setOptions(apiUrl + "food-type/all", "name");
            result = foodTypeGetApiRequest.execute().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (String res : result) {
            foodTypes.add(res);
        }

        registerPanelAlertDialog = builder.create();

        ImageButton closeButton = alertView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v5 -> registerPanelAlertDialog.dismiss());

        LinearLayout registerButton = alertView.findViewById(R.id.registerButton);
        Spinner foodTypeSpinner = alertView.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodTypeSpinner.setAdapter(adapter);


        registerButton.setOnClickListener(v -> {
            EditText cityEditText = alertView.findViewById(R.id.cityTextEdit);
            EditText streetEditText = alertView.findViewById(R.id.streetTextEdit);
            EditText emailEditText = alertView.findViewById(R.id.emailTextEdit);
            EditText nameEditText = alertView.findViewById(R.id.nameTextEdit);
            EditText passwordEditText = alertView.findViewById(R.id.passwordTextEdit);
            EditText phoneNumberEditText = alertView.findViewById(R.id.phoneNumberTextEdit);
            EditText lastnameEditText = alertView.findViewById(R.id.lastnameTextEdit);
            EditText localNameEditText = alertView.findViewById(R.id.localNameTextEdit);
            EditText webPageEditText = alertView.findViewById(R.id.webPageTextEdit);
            EditText hoursMonEditText = alertView.findViewById(R.id.hoursMonTextEdit);
            EditText hoursTueEditText = alertView.findViewById(R.id.hoursTueTextEdit);
            EditText hoursWenEditText = alertView.findViewById(R.id.hoursWenTextEdit);
            EditText hoursThuEditText = alertView.findViewById(R.id.hoursThuTextEdit);
            EditText hoursFriEditText = alertView.findViewById(R.id.hoursFriTextEdit);
            EditText hoursSatEditText = alertView.findViewById(R.id.hoursSatTextEdit);
            EditText hoursSunEditText = alertView.findViewById(R.id.hoursSunTextEdit);
            EditText latitude = alertView.findViewById(R.id.latitude);
            EditText longitude = alertView.findViewById(R.id.longitude);

            Switch animalsSwitch = alertView.findViewById(R.id.animalsSwitch);
            Switch carParkSwitch = alertView.findViewById(R.id.carParkSwitch);
            Switch toiletSwitch = alertView.findViewById(R.id.toiletSwitch);

            String jsonInputString = "{" +
                    " \"restaurantName\": \"" + localNameEditText.getText().toString() + "\", " +
                    " \"name\": \"" + nameEditText.getText().toString() + "\", " +
                    " \"surname\": \"" + lastnameEditText.getText().toString() + "\", " +
                    " \"email\": \"" + emailEditText.getText().toString() + "\", " +
                    " \"password\": \"" + passwordEditText.getText().toString() + "\", " +
                    " \"phoneNumber\" : \"" + phoneNumberEditText.getText().toString() + "\", " +
                    " \"streetAddress\" : \"" + streetEditText.getText().toString() + "\", " +
                    " \"city\" : \"" + cityEditText.getText().toString() + "\", " +
                    " \"webPage\" : \"" + webPageEditText.getText().toString() + "\", " +
                    " \"foodType\" : " + (foodTypeSpinner.getSelectedItemPosition() + 1) + ", " +
                    " \"animalInfo\" : \"" + animalsSwitch.isChecked() + "\", " +
                    " \"toiletInfo\" : \"" + toiletSwitch.isChecked() + "\", " +
                    " \"carInfo\" : \"" + carParkSwitch.isChecked() + "\", " +
                    " \"hoursMonInfo\" : \"" + hoursMonEditText.getText().toString() + "\", " +
                    " \"hoursTueInfo\" : \"" + hoursTueEditText.getText().toString() + "\", " +
                    " \"hoursWenInfo\" : \"" + hoursWenEditText.getText().toString() + "\", " +
                    " \"hoursThuInfo\" : \"" + hoursThuEditText.getText().toString() + "\", " +
                    " \"hoursFriInfo\" : \"" + hoursFriEditText.getText().toString() + "\", " +
                    " \"hoursSatInfo\" : \"" + hoursSatEditText.getText().toString() + "\", " +
                    " \"hoursSunInfo\" : \"" + hoursSunEditText.getText().toString() + "\", " +
                    " \"latitude\" : \"" + latitude.getText().toString() + "\", " +
                    " \"longitude\" : \"" + longitude.getText().toString() + "\" " +
                    " }";

            postApiRequest.setOptions(apiUrl + "restaurations/add", jsonInputString);
            postApiRequest.execute();

            registerPanelAlertDialog.dismiss();
        });

        registerPanelAlertDialog.show();
    }


    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String response;
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    "{\"email\":\"" + params[0] + "\",\"password\":\"" + params[1] + "\"}");

            Request clientRequest = new Request.Builder()
                    .url(apiUrl + "clients/login")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response clientResponse = client.newCall(clientRequest).execute();

                response = clientResponse.body().string();
                String[] parts1 = response.split(";");
                response = parts1[0];

                if (response.equals("Zalogowano jako klient")) {

                    clientId = Integer.parseInt(parts1[1]);

                    return response;
                } else if (response.equals("Błąd logowania")) {
                    OkHttpClient restaurant = new OkHttpClient();

                    Request restaurationRequest = new Request.Builder()
                            .url(apiUrl + "restaurations/login")
                            .post(body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    try {
                        Response restaurationResponse = restaurant.newCall(restaurationRequest).execute();

                        response = restaurationResponse.body().string();

                        String[] parts = response.split(";");
                        response = parts[0];
                        restaurationId = Integer.parseInt(parts[1]);

                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Błąd podczas logowania";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Błąd podczas logowania";
            }
            return "Nie ma takiego konta!";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

            if (result.equals("Zalogowano jako klient")) {
                loginAsClient();
            } else if (result.equals("Zalogowano jako restauracja")) {
                loginAsRestauration();
            }
        }
    }

    private String readFirstLine(Context context) {
        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            File file = new File("/storage/emulated/0/Android/data/com.example.fooje/files/serverApiUrl.txt");

            if (!file.exists()) {
                return null;
            }

            fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));
            String firstLine = reader.readLine();

            return firstLine;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

