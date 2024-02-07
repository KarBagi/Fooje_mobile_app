package com.example.fooje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ClientEditProfileActivity extends AppCompatActivity {

    String apiUrl = MainActivity.apiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_profie_edit_layout);

        EditText phoneNumber = findViewById(R.id.clientPhoneNumberTextEdit);
        EditText email = findViewById(R.id.clientEmailTextEdit);
        EditText password = findViewById(R.id.ClientPasswordTextEdit);

        TextView profileEditSaveButton = findViewById(R.id.profileEditSaveButton);
        profileEditSaveButton.setOnClickListener(v -> {

            if (TextUtils.isEmpty(phoneNumber.getText().toString())) phoneNumber.setText("emailExample");
            if (TextUtils.isEmpty(email.getText().toString())) phoneNumber.setText("656656656");
            if (TextUtils.isEmpty(password.getText().toString())) phoneNumber.setText("passwordExample");

            PostApiRequest updateClient = new PostApiRequest();
            String jsonUpdateClient = "{" +
                    " \"name\": \"nameExample\", " +
                    "\"lastname\": \"lastNameExample\", " +
                    " \"email\": \"" + email.getText().toString() + "\", " +
                    " \"password\": \"" + password.getText().toString() + "\", " +
                    " \"phoneNumber\" : \"" + phoneNumber.getText().toString() + "\" " +
                    " }";

            updateClient.setOptionUpdateClient(apiUrl + "clients/updateClient/" + MainActivity.clientId, jsonUpdateClient);
            updateClient.execute();
        });
    }

    public void goBack(View view) {
        finish();
    }
}