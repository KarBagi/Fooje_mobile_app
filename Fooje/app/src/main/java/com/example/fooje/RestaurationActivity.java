package com.example.fooje;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RestaurationActivity extends AppCompatActivity {

    private AlertDialog editAlert;
    private AlertDialog addNewPositionAlertDialog;
    String[] restaurantDetails, product;
    List<List<Object>> restaurantDetailsList, productList;
    String apiUrl = MainActivity.apiUrl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restauration_activity_layout);

        getRestaurantDetails();

        getRestaurantProducts();

        TextView restaurantNametextView = findViewById(R.id.retaurationNameTextView);
        restaurantNametextView.setText(restaurantDetailsList.get(0).get(1).toString());

        showRestaurantProducts();
    }

    private void showRestaurantProducts() {
        LinearLayout menuPositionContainer = findViewById(R.id.restaurantMenuContainer);

        if (menuPositionContainer != null) {
            for (List<Object> entry : productList) {
                View menuInflatedView = LayoutInflater.from(this).inflate(R.layout.restauration_menu_view_layout, menuPositionContainer, false);

                TextView positionName = menuInflatedView.findViewById(R.id.menuPositionView);
                TextView descriptionName = menuInflatedView.findViewById(R.id.descriptionView);

                positionName.setText(entry.get(2).toString());
                descriptionName.setText(entry.get(3).toString());

                menuPositionContainer.addView(menuInflatedView);
                menuInflatedView.setOnClickListener(v -> {
                    AlertDialog.Builder editPositionAlertBuilder = new AlertDialog.Builder(this);
                    View alertView = LayoutInflater.from(this).inflate(R.layout.restaurant_edit_position_alert, null);
                    editPositionAlertBuilder.setView(alertView);

                    TextView positionView = alertView.findViewById(R.id.menuPositionNameTextEdit);
                    TextView descriptionView = alertView.findViewById(R.id.menuPositionDescriptionTextEdit);
                    TextView priceView = alertView.findViewById(R.id.menuPositionPriceTextEdit);
                    TextView weightView = alertView.findViewById(R.id.menuPositionWeightTextEdit);
                    TextView ingredientsView = alertView.findViewById(R.id.menuPositionIngredientsTextEdit);
                    TextView fatView = alertView.findViewById(R.id.fatTextEdit);
                    TextView proteinView = alertView.findViewById(R.id.proteinTextEdit);
                    TextView saltView = alertView.findViewById(R.id.saltTextEdit);
                    TextView sugarView = alertView.findViewById(R.id.sugarTextEdit);
                    TextView fiberView = alertView.findViewById(R.id.fiberTextEdit);
                    TextView carbonView = alertView.findViewById(R.id.carbonTextEdit);

                    positionView.setText(entry.get(2).toString());
                    descriptionView.setText(entry.get(3).toString());
                    priceView.setText(entry.get(5).toString());
                    weightView.setText(entry.get(4).toString());
                    ingredientsView.setText(entry.get(6).toString());
                    fatView.setText(entry.get(7).toString());
                    carbonView.setText(entry.get(8).toString());
                    sugarView.setText(entry.get(9).toString());
                    proteinView.setText(entry.get(10).toString());
                    saltView.setText(entry.get(11).toString());
                    fiberView.setText(entry.get(12).toString());

                    LinearLayout savePositionEditButton = alertView.findViewById(R.id.savePositionEditButton);

                    editAlert = editPositionAlertBuilder.create();

                    ImageButton editPositionAlertCloseButton = alertView.findViewById(R.id.closeButton);

                    editPositionAlertCloseButton.setOnClickListener(v1 -> editAlert.dismiss());

                    editAlert.show();
                });
            }

            TextView addNewPosition = findViewById(R.id.addNewPositionButton);
            addNewPosition.setOnClickListener(v -> {
                AlertDialog.Builder addNewPositionBuilder = new AlertDialog.Builder(this);
                View addNewPositionAlertView = LayoutInflater.from(this).inflate(R.layout.restauration_add_position_to_menu_alert, null);
                addNewPositionBuilder.setView(addNewPositionAlertView);

                addNewPositionAlertDialog = addNewPositionBuilder.create();

                LinearLayout addButton = addNewPositionAlertView.findViewById(R.id.addPositionToMenuButton);
                addButton.setOnClickListener(v1 -> {
                    addNewPositionToMenu(menuPositionContainer, addNewPositionAlertView);

                });

                ImageButton closeButton = addNewPositionAlertView.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(v1 -> addNewPositionAlertDialog.dismiss());

                addNewPositionAlertDialog.show();
            });

            ImageButton optionsButton = findViewById(R.id.menuButton);
            optionsButton.setOnClickListener(v -> {
                AlertDialog.Builder editPositionAlertBuilder = new AlertDialog.Builder(this);
                View alertView = LayoutInflater.from(this).inflate(R.layout.restauration_options_alert, null);
                editPositionAlertBuilder.setView(alertView);

                TextView restaurantName = alertView.findViewById(R.id.restaurationNameTextView);
                restaurantName.setText(restaurantDetailsList.get(0).get(1).toString());

                LinearLayout ordersButton = alertView.findViewById(R.id.ordersButton);
                ordersButton.setOnClickListener(v1 -> {
                    Intent intent = new Intent(this, OrdersActivity.class);
                    startActivity(intent);
                });

                LinearLayout editProfileButton = alertView.findViewById(R.id.editProfileButton);
                editProfileButton.setOnClickListener(v1 -> {
                    Intent intent = new Intent(this, RestaurationEditProfileActivity.class);
                    startActivity(intent);
                });

                LinearLayout logoutButton = alertView.findViewById(R.id.logoutButton);
                logoutButton.setOnClickListener(v1 -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                    finish();
                });

                editAlert = editPositionAlertBuilder.create();

                ImageButton editPositionAlertCloseButton = alertView.findViewById(R.id.clientMenuCloseButton);

                editPositionAlertCloseButton.setOnClickListener(v1 -> editAlert.dismiss());

                editAlert.show();
            });
        }
    }

    private void addNewPositionToMenu(LinearLayout menuPositionContainer, View addNewPositionAlertView) {
        EditText name = addNewPositionAlertView.findViewById(R.id.menuPositionNameTextEdit);
        EditText price = addNewPositionAlertView.findViewById(R.id.menuPositionPriceTextEdit);
        EditText weight = addNewPositionAlertView.findViewById(R.id.menuPositionWeightTextEdit);
        EditText description = addNewPositionAlertView.findViewById(R.id.menuPositionDescriptionTextEdit);
        EditText ingredients = addNewPositionAlertView.findViewById(R.id.menuPositionIngredientsTextEdit);
        EditText fat = addNewPositionAlertView.findViewById(R.id.fatTextEdit);
        EditText carbon = addNewPositionAlertView.findViewById(R.id.carbonTextEdit);
        EditText sugar = addNewPositionAlertView.findViewById(R.id.sugarTextEdit);
        EditText protein = addNewPositionAlertView.findViewById(R.id.proteinTextEdit);
        EditText salt = addNewPositionAlertView.findViewById(R.id.saltTextEdit);
        EditText fiber = addNewPositionAlertView.findViewById(R.id.fiberTextEdit);

        if (name.getText() != null ||
                price.getText() != null ||
                weight.getText() != null ||
                description.getText() != null ||
                ingredients.getText() != null ||
                fat.getText() != null ||
                carbon.getText() != null ||
                sugar.getText() != null ||
                protein.getText() != null ||
                salt.getText() != null ||
                fiber.getText() != null) {

            String addProductJson = "{" +
                    " \"restaurantId\": \"" +MainActivity.restaurationId + "\", " +
                    " \"name\": \"" + name.getText().toString() + "\", " +
                    "\"description\": \"" + description.getText().toString() + "\", " +
                    " \"weight\": \"" + weight.getText().toString() + " kg\", " +
                    " \"price\": \"" + price.getText().toString() + "\", " +
                    " \"ingredients\" : \"" + ingredients.getText().toString() + "\", " +
                    " \"fat\" : \"" + fat.getText().toString() + "\", " +
                    " \"carbon\" : \"" + carbon.getText().toString() + "\", " +
                    " \"sugar\" : \"" + sugar.getText().toString() + "\", " +
                    " \"protein\" : \"" + protein.getText().toString() + "\", " +
                    " \"salt\" : \"" + salt.getText().toString() + "\", " +
                    " \"fiber\" : \"" + fiber.getText().toString() + "\" " +
                    " }";

            PostApiRequest addProductPostApiRequest = new PostApiRequest();
            addProductPostApiRequest.setOptions(apiUrl + "products/add", addProductJson);
            addProductPostApiRequest.execute();

            product = new String[0];
            productList.clear();
            getRestaurantProducts();
            menuPositionContainer.removeAllViews();
            showRestaurantProducts();

        } else {
            Toast.makeText(this, "Uzupełnij wszystkie dane", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRestaurantProducts() {
        try {
            GetApiRequest getProducts = new GetApiRequest();
            getProducts.setOptionsProducts(apiUrl + "products/byrestaurantid", Integer.parseInt(restaurantDetailsList.get(0).get(0).toString()));
            product = getProducts.execute().get();
            productList = getProducts.convertJsonToList(product[0]);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getRestaurantDetails() {
        try {
            GetApiRequest getRestaurantDetails = new GetApiRequest();
            getRestaurantDetails.setOptionsGetFavoriteRestaurantsDetails(apiUrl + "restaurations/" + MainActivity.restaurationId);
            restaurantDetails = getRestaurantDetails.execute().get();
            restaurantDetailsList = getRestaurantDetails.convertJsonToList(restaurantDetails[0]);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void openEditAlert(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alertView = LayoutInflater.from(this).inflate(R.layout.restaurant_edit_position_alert, null);
        builder.setView(alertView);

        editAlert = builder.create();

        ImageButton closeButton = alertView.findViewById(R.id.closeButton);

        editAlert.show();

        closeButton.setOnClickListener(v1 -> editAlert.dismiss());
    }
}