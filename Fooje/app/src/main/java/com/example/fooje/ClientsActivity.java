package com.example.fooje;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ClientsActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private AlertDialog alertDialog;
    private AlertDialog alertDialog1;
    private AlertDialog menuButtonAlertDialog;
    private AlertDialog filterButtonAlertDialog;
    private AlertDialog historyButtonAlertDialog;
    private AlertDialog favoritesButtonAlertDialog;
    double latitude, longitude;
    double chooseDistance = 500;
    int foodTypeFilter, inEmpty = 0;
    int clientId = MainActivity.clientId;
    boolean animalInfo, toiletInfo, carInfo;
    List<String> distance = new ArrayList<>();
    String[] clientName, orders;
    String[] foodTypesStringTable;
    List<String> foodTypes = new ArrayList<>();
    String apiUrl = MainActivity.apiUrl;
    String[] restaurants, products, favorites, favoriteDetails, orderedProducts, orderDetails;
    List<List<Object>> restaurationList, productsList, favoritesList, favoriteDetailsList, ordersList;
    List<List<Object>> orderDetailsList = new ArrayList<>();
    List<List<Object>> orderedProductsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clients_activity_layout);

        if (checkLocationPermission()) {
            Toast.makeText(this, "Aplikacja ma dostęp do lokalizacji", Toast.LENGTH_SHORT).show();
        }
        initializeLocation();
    }

    private void showContentMenu() {
        getRestaurantsNearby();
        distance.add("+ 500 m");
        distance.add("+ 1 km");
        distance.add("+ 1,5 km");
        distance.add("+ 2 km");
        distance.add("+ 2,5 km");
        distance.add("+ 3 km");

        getClientName();

        getFoodTypes();

        TextView clientNameTextView = findViewById(R.id.clientNameTextView);
        clientNameTextView.setText("Cześć, " + clientName[0]);

        restaurantsScrollView();

        Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(v -> {
            showFilterAlert();
        });

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v4 -> {
            showMenuAlert();
        });
    }

    private void restaurantsScrollView() {
        LinearLayout restaurantContainer = findViewById(R.id.restaurantsContainer);
        restaurantContainer.removeAllViews();
        if (restaurantContainer != null) {
            for (List<Object> restaurant : restaurationList) {

                if (foodTypeFilter > 0 && Integer.parseInt(restaurant.get(9).toString()) != foodTypeFilter)
                    continue;
                if (animalInfo && !restaurant.get(10).toString().equals(Boolean.toString(animalInfo)))
                    continue;
                if (toiletInfo && !restaurant.get(11).toString().equals(Boolean.toString(toiletInfo)))
                    continue;
                if (carInfo && !restaurant.get(12).toString().equals(Boolean.toString(carInfo)))
                    continue;

                showRestaurants(restaurantContainer, restaurant);
            }
        }
    }

    private void showMenuAlert() {
        AlertDialog.Builder menuButtonBuilder = new AlertDialog.Builder(this);
        View menuButtonView = LayoutInflater.from(this).inflate(R.layout.client_options_alert, null);
        menuButtonBuilder.setView(menuButtonView);

        TextView nameTextView = menuButtonView.findViewById(R.id.nameTextView);
        nameTextView.setText(clientName[0]);

        LinearLayout favoriteButton = menuButtonView.findViewById(R.id.favoritesButton);
        favoriteButton.setOnClickListener(v1 -> {
            AlertDialog.Builder favoritesButtonBuilder = new AlertDialog.Builder(this);
            View favoritesButtonView = LayoutInflater.from(this).inflate(R.layout.client_favorites_alert, null);
            favoritesButtonBuilder.setView(favoritesButtonView);

            favoriteDetailsList = new ArrayList<>();

            favoritesButtonAlertDialog = favoritesButtonBuilder.create();

            try {
                GetApiRequest getFavoritesRestaurants = new GetApiRequest();
                getFavoritesRestaurants.setOptionsFavoritesRestaurants(apiUrl + "favorites/byClientId", clientId);
                favorites = getFavoritesRestaurants.execute().get();
                favoritesList = getFavoritesRestaurants.convertJsonToList(favorites[0]);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for(List<Object> fav : favoritesList){
                try {
                    GetApiRequest getFavoriteRestaurants = new GetApiRequest();
                    getFavoriteRestaurants.setOptionsGetFavoriteRestaurantsDetails(apiUrl + "restaurations/" + Integer.parseInt(fav.get(0).toString()));
                    favoriteDetails = getFavoriteRestaurants.execute().get();
                    favoriteDetailsList.add(getFavoriteRestaurants.convertJsonToList(favoriteDetails[0]).get(0));
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            LinearLayout restaurantsContainer = favoritesButtonView.findViewById(R.id.restaurantsContainer);
            restaurantsContainer.removeAllViews();
            if (restaurantsContainer != null) {
                for (List<Object> restaurant : favoriteDetailsList) {
                    showRestaurants(restaurantsContainer, restaurant);
                }
            }

            ImageButton closeButton = favoritesButtonView.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(v -> favoritesButtonAlertDialog.dismiss());

            favoritesButtonAlertDialog.show();
        });

        LinearLayout editProfileButton = menuButtonView.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(this, ClientEditProfileActivity.class);
            startActivity(intent);
        });

        LinearLayout ordersHistory = menuButtonView.findViewById(R.id.historyButton);
        ordersHistory.setOnClickListener(v -> {
            AlertDialog.Builder historyButtonBuilder = new AlertDialog.Builder(this);
            View historyButtonView = LayoutInflater.from(this).inflate(R.layout.client_orders_history_alert, null);
            historyButtonBuilder.setView(historyButtonView);

            historyButtonAlertDialog = historyButtonBuilder.create();

            getClientOrderHistory();

            if(inEmpty == 1){
                getOrderDetails();
                getProductsFromOrders();
            }

            LinearLayout ordersContainer = historyButtonView.findViewById(R.id.ordersContainer);
            ordersContainer.removeAllViews();

            for (List<Object> order : ordersList) {
                View ordersInflatedView = LayoutInflater.from(this).inflate(R.layout.orders_history_layout, null);

                TextView RestaurantName = ordersInflatedView.findViewById(R.id.clientNameTextView);
                RestaurantName.setText(getRestaurantName(Integer.parseInt(order.get(1).toString())));

                TextView orderDate = ordersInflatedView.findViewById(R.id.dateTextView);
                orderDate.setText(order.get(4).toString());

                TextView status = ordersInflatedView.findViewById(R.id.statusTextView);
                status.setText(order.get(3).toString());

                LinearLayout positionsContainer = ordersInflatedView.findViewById(R.id.positionContainer);

                for (List<Object> orderDetails : orderDetailsList) {

                    if (order.get(0).toString().equals(orderDetails.get(1).toString())) {

                        for (List<Object> position : orderedProductsList) {

                            if (orderDetails.get(2).toString().equals(position.get(0).toString())) {

                                View positionInflatedView = LayoutInflater.from(ordersInflatedView.getContext()).inflate(R.layout.order_positions_layout, null);

                                TextView positionName = positionInflatedView.findViewById(R.id.positionNameTextView);
                                positionName.setText(position.get(2).toString());

                                TextView price = positionInflatedView.findViewById(R.id.priceTextView);
                                price.setText(position.get(5).toString());


                                positionsContainer.addView(positionInflatedView);
                            }
                        }
                    }
                }

                ordersContainer.addView(ordersInflatedView);
            }

            ImageButton closeButton = historyButtonView.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(v1 -> historyButtonAlertDialog.dismiss());


            historyButtonAlertDialog.show();
        });

        LinearLayout cartButton = menuButtonView.findViewById(R.id.bucketButton);
        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClientCartActivity.class);
            startActivity(intent);
        });

        LinearLayout logout = menuButtonView.findViewById(R.id.logoutButton);
        logout.setOnClickListener(v -> {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            finish();
        });

        menuButtonAlertDialog = menuButtonBuilder.create();
        ImageButton closeButton2 = menuButtonView.findViewById(R.id.clientMenuCloseButton);
        closeButton2.setOnClickListener(v5 -> menuButtonAlertDialog.dismiss());
        menuButtonAlertDialog.show();
    }

    private void getClientOrderHistory() {
        orders = new String[0];

        try {
            GetApiRequest getClientOrders = new GetApiRequest();
            getClientOrders.setOptionsOrders(apiUrl + "orders/by-client/" + MainActivity.clientId);
            orders = getClientOrders.execute().get();

            if (orders[0].length() < 4){
                inEmpty = 0;
                Toast.makeText(this, "Nie ma historii zamówień", Toast.LENGTH_SHORT).show();
                historyButtonAlertDialog.dismiss();
            }
            else {
                inEmpty = 1;
                ordersList = getClientOrders.convertJsonToList(orders[0]);
                Collections.reverse(ordersList);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getProductsFromOrders() {
        orderedProducts = new String[0];
        orderedProductsList.clear();
        for (List<Object> product : orderDetailsList) {
            try {
                GetApiRequest getProducts = new GetApiRequest();
                getProducts.setOptionsGetProducts(apiUrl + "products/byproductid?productId=" + Integer.parseInt(product.get(2).toString()));
                orderedProducts = getProducts.execute().get();
                if (orderedProducts[0].length() > 4)
                    orderedProductsList.add(getProducts.convertJsonToList(orderedProducts[0]).get(0));
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getOrderDetails() {
        orderDetails = new String[0];
        orderDetailsList.clear();

        for (List<Object> order : ordersList) {
            try {
                GetApiRequest getOdrerDetails = new GetApiRequest();
                getOdrerDetails.setOptionsOrdersDetails(apiUrl + "orderDetails/by-orders/" + order.get(0));
                orderDetails = getOdrerDetails.execute().get();
                orderDetailsList.addAll(getOdrerDetails.convertJsonToList(orderDetails[0]));
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showFilterAlert() {
        AlertDialog.Builder filterButtonBuilder = new AlertDialog.Builder(this);
        View filterButtonView = LayoutInflater.from(this).inflate(R.layout.filters_alert, null);
        filterButtonBuilder.setView(filterButtonView);

        filterButtonAlertDialog = filterButtonBuilder.create();

        Spinner foodTypeSpinner = filterButtonView.findViewById(R.id.foodTypeSpinner);
        ArrayAdapter<String> foodTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodTypes);
        foodTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodTypeSpinner.setAdapter(foodTypeAdapter);

        Spinner distanceSpinner = filterButtonView.findViewById(R.id.distanceSpinner);
        ArrayAdapter<String> distanceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, distance);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(distanceAdapter);

        Switch animalInfoSwitch = filterButtonView.findViewById(R.id.animalsSwitch);

        Switch toiletInfoSwitch = filterButtonView.findViewById(R.id.toiletSwitch);

        Switch carParkInfoSwitch = filterButtonView.findViewById(R.id.carParkSwitch);

        LinearLayout saveFilter = filterButtonView.findViewById(R.id.saveFilterButton);
        saveFilter.setOnClickListener(v1 -> {
            if (distanceSpinner.getSelectedItemPosition() == 0)
                chooseDistance = 500;
            else if (distanceSpinner.getSelectedItemPosition() == 1)
                chooseDistance = 1000;
            else if (distanceSpinner.getSelectedItemPosition() == 2)
                chooseDistance = 1500;
            else if (distanceSpinner.getSelectedItemPosition() == 3)
                chooseDistance = 2000;
            else if (distanceSpinner.getSelectedItemPosition() == 4)
                chooseDistance = 2500;
            else if (distanceSpinner.getSelectedItemPosition() == 5)
                chooseDistance = 3000;

            foodTypeFilter = foodTypeSpinner.getSelectedItemPosition();
            restaurationList.clear();
            restaurants = new String[0];
            animalInfo = animalInfoSwitch.isChecked();
            toiletInfo = toiletInfoSwitch.isChecked();
            carInfo = carParkInfoSwitch.isChecked();
            getRestaurantsNearby();
            restaurantsScrollView();
            filterButtonAlertDialog.dismiss();
        });

        ImageButton closeButton2 = filterButtonView.findViewById(R.id.closeButton);
        closeButton2.setOnClickListener(v1 -> filterButtonAlertDialog.dismiss());
        filterButtonAlertDialog.show();
    }

    private void showRestaurants(LinearLayout restaurantContainer, List<Object> restaurant) {
        View inflatedView = LayoutInflater.from(this).inflate(R.layout.relative_layout, restaurantContainer, false);
        TextView textView = inflatedView.findViewById(R.id.dataTextView);
        TextView restaurantFoodTypeTextView = inflatedView.findViewById(R.id.foodTypeTextView);
        textView.setText(restaurant.get(1).toString());
        restaurantFoodTypeTextView.setText("Kategoria: " + foodTypes.get(Integer.parseInt(restaurant.get(9).toString())));

        inflatedView.setOnClickListener(v -> {

            showRestaurantDetails(restaurant);
        });

        restaurantContainer.addView(inflatedView);
    }

    private void showRestaurantDetails(List<Object> restaurant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alertView = LayoutInflater.from(this).inflate(R.layout.restaurant_details_alert, null);
        builder.setView(alertView);

        Switch favoritesSwitch = alertView.findViewById(R.id.favoriteSwitch);

        getRestaurantProducts(Integer.parseInt(restaurant.get(0).toString()));

        alertDialog = builder.create();
        ImageButton closeButton = alertView.findViewById(R.id.closeButton);
        alertDialog.show();

        try {
            GetApiRequest getFavoritesRestaurants = new GetApiRequest();
            getFavoritesRestaurants.setOptionsFavoritesRestaurants(apiUrl + "favorites/byClientId", clientId);
            favorites = getFavoritesRestaurants.execute().get();
            favoritesList = getFavoritesRestaurants.convertJsonToList(favorites[0]);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (List<Object> fav : favoritesList) {
            if (Integer.parseInt(restaurant.get(0).toString()) == Integer.parseInt(fav.get(0).toString()))
                favoritesSwitch.setChecked(true);
        }

        boolean favoritesStatus = favoritesSwitch.isChecked();

        TextView restaurantName = alertView.findViewById(R.id.restaurantName);
        TextView restaurantAddress = alertView.findViewById(R.id.address);
        TextView restaurantPhoneNumber = alertView.findViewById(R.id.phoneNumber);
        TextView restaurantWebPage = alertView.findViewById(R.id.webPage);

        restaurantName.setText(restaurant.get(1).toString());
        restaurantAddress.setText(restaurant.get(7).toString() + "\n" + restaurant.get(6).toString());
        restaurantPhoneNumber.setText(restaurant.get(5).toString());
        restaurantWebPage.setText(restaurant.get(8).toString());

        restaurantWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webPageUrl = "https://www." + restaurant.get(8).toString();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(webPageUrl));
                startActivity(intent);
            }
        });

        closeButton.setOnClickListener(v1 -> {
            if (favoritesSwitch.isChecked() != favoritesStatus) {
                if (!favoritesStatus) {
                    addToFavorites(restaurant);
                } else deleteFromFavorites(restaurant);

            }

            alertDialog.dismiss();
            productsList.clear();
            products = new String[0];
        });

        LinearLayout menuContainer = alertView.findViewById(R.id.menuContainer);

        for (List<Object> product : productsList) {
            showRestaurantMenu(menuContainer, product);
        }

        ImageButton informationButton = alertView.findViewById(R.id.informationButton);

        informationButton.setOnClickListener(v2 -> {
            showRestaurationInfo(restaurant);
        });

        LinearLayout navigateButton = alertView.findViewById(R.id.showOnMapButton);
        navigateButton.setOnClickListener(v6 -> {

            navigateToRestaurant(restaurant);
        });
    }

    private void navigateToRestaurant(List<Object> restaurant) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(restaurant.get(6).toString() + ", " + restaurant.get(7).toString()));

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void showRestaurationInfo(List<Object> restaurant) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        View restaurationInfoView = LayoutInflater.from(this).inflate(R.layout.restaurant_information_layout, null);
        builder1.setView(restaurationInfoView);

        alertDialog1 = builder1.create();

        TextView infoAddrestTextView = restaurationInfoView.findViewById(R.id.address);
        TextView infoPhoneNumberTextView = restaurationInfoView.findViewById(R.id.phoneNumber);

        infoAddrestTextView.setText(restaurant.get(7).toString() + "\n" + restaurant.get(6).toString());
        infoPhoneNumberTextView.setText(restaurant.get(5).toString());

        ImageButton closeButton1 = restaurationInfoView.findViewById(R.id.closeButton1);

        closeButton1.setOnClickListener(v3 -> alertDialog1.dismiss());

        TextView textMon = restaurationInfoView.findViewById(R.id.hoursMon);
        TextView textTue = restaurationInfoView.findViewById(R.id.hoursTue);
        TextView textWen = restaurationInfoView.findViewById(R.id.hoursWen);
        TextView textThu = restaurationInfoView.findViewById(R.id.hoursThu);
        TextView textFri = restaurationInfoView.findViewById(R.id.hoursFri);
        TextView textSat = restaurationInfoView.findViewById(R.id.hoursSat);
        TextView textSun = restaurationInfoView.findViewById(R.id.hoursSun);

        textMon.setText(restaurant.get(13).toString());
        textTue.setText(restaurant.get(14).toString());
        textWen.setText(restaurant.get(15).toString());
        textThu.setText(restaurant.get(16).toString());
        textFri.setText(restaurant.get(17).toString());
        textSat.setText(restaurant.get(18).toString());
        textSun.setText(restaurant.get(19).toString());

        ImageView animalsImageView = restaurationInfoView.findViewById(R.id.restaurationInfoCheckBoxAnimals);
        ImageView toiletImageView = restaurationInfoView.findViewById(R.id.restaurationInfoCheckBoxToliet);
        ImageView parkImageView = restaurationInfoView.findViewById(R.id.restaurationInfoCheckBoxPark);

        if (restaurant.get(10).toString().equals("true"))
            animalsImageView.setImageResource(R.drawable.yes_photo);
        else
            animalsImageView.setImageResource(R.drawable.no_photo);
        if (restaurant.get(11).toString().equals("true"))
            toiletImageView.setImageResource(R.drawable.yes_photo);
        else
            toiletImageView.setImageResource(R.drawable.no_photo);
        if (restaurant.get(12).toString().equals("true"))
            parkImageView.setImageResource(R.drawable.yes_photo);
        else
            parkImageView.setImageResource(R.drawable.no_photo);

        alertDialog1.show();
    }

    private void showRestaurantMenu(LinearLayout menuContainer, List<Object> product) {
        View menuView = LayoutInflater.from(this).inflate(R.layout.restaurant_menu_layout, menuContainer, false);

        TextView menuTextView = menuView.findViewById(R.id.menuPositionView);
        menuTextView.setText(product.get(2).toString());

        TextView descriptionTextView = menuView.findViewById(R.id.descriptionView);
        descriptionTextView.setText(product.get(3).toString());

        TextView priceTextView = menuView.findViewById(R.id.priceTextView);
        priceTextView.setText(product.get(5).toString());

        LinearLayout addToCartButton = menuView.findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(v -> {

            if((CartController.getInstance().restaurantId == 999999) || (CartController.getInstance().getRestaurantId() == Integer.parseInt(product.get(1).toString()))){
                CartController.getInstance().setRestaurantId(Integer.parseInt(product.get(1).toString()));

                PostApiRequest addToCartRequest = new PostApiRequest();
                String cartJsonString = "{" +
                        " \"clientId\": " + clientId + ", " +
                        "\"productId\": " + Integer.parseInt(product.get(0).toString()) +
                        " }";

                addToCartRequest.setOptions(apiUrl + "carts/add", cartJsonString);
                addToCartRequest.execute();

                Toast.makeText(this, "Dodano produkt do koszyka", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "Do koszyka można dodać produkty tylko z jednej restauracji", Toast.LENGTH_LONG).show();
        });

        menuContainer.addView(menuView);
    }

    private void addToFavorites(List<Object> restaurant) {
        PostApiRequest addToFavorites = new PostApiRequest();
        String jsonInputString = "{\"clientId\": " + clientId +
                ", \"restaurantId\": " + restaurant.get(0) + "}";
        addToFavorites.setOptions(apiUrl + "favorites/add", jsonInputString);
        addToFavorites.execute();
    }

    private void deleteFromFavorites(List<Object> restaurant) {
        PostApiRequest deleteFavorites = new PostApiRequest();
        deleteFavorites.setOptionsDeleteFavorites(apiUrl + "favorites/delete", Integer.parseInt(restaurant.get(0).toString()), clientId);
        deleteFavorites.execute();
    }

    private void getFoodTypes() {
        try {
            GetApiRequest filterFoodTypeGetApiRequest = new GetApiRequest();
            filterFoodTypeGetApiRequest.setOptions(apiUrl + "food-type/all", "name");
            foodTypesStringTable = filterFoodTypeGetApiRequest.execute().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        foodTypes.add("Wszystko");

        for (String res : foodTypesStringTable) {
            foodTypes.add(res);
        }
    }

    private void getClientName() {
        try {
            GetApiRequest clientNameGettypeRequest = new GetApiRequest();
            clientNameGettypeRequest.setOptions(apiUrl + "clients/" + clientId, "name");
            clientName = clientNameGettypeRequest.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRestaurantName(int restaurantId) {
        try {
            GetApiRequest clientNameGetTypeRequest = new GetApiRequest();
            clientNameGetTypeRequest.setOptions(apiUrl + "restaurations/" + restaurantId, "restaurantName");
            String[] restaurantName = clientNameGetTypeRequest.execute().get();
            return restaurantName[0];
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getRestaurantsNearby() {
        try {
            GetApiRequest restaurationsNearby = new GetApiRequest();
            restaurationsNearby.setOptionsToGetRestaurants(apiUrl + "restaurations/nearby", latitude, longitude, chooseDistance);
            restaurants = restaurationsNearby.execute().get();
            restaurationList = restaurationsNearby.convertJsonToList(restaurants[0]);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getRestaurantProducts(int restaurantId) {
        try {
            GetApiRequest restaurantProducts = new GetApiRequest();
            restaurantProducts.setOptionsProducts(apiUrl + "products/byrestaurantid", restaurantId);
            products = restaurantProducts.execute().get();
            productsList = restaurantProducts.convertJsonToList(products[0]);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(getLocationRequest(), locationCallback, null);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(120000); // Interwał w milisekundach, w jakim chcesz otrzymywać aktualizacje lokalizacji
        locationRequest.setFastestInterval(100000); // Najkrótszy możliwy interwał
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showExplanationDialog();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult != null) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    updateLocationData(location);
                }
            }
        }
    };

    public void initializeLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void updateLocationData(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        showContentMenu();
    }

    private void showExplanationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Potrzebujemy dostępu do lokalizacji")
                .setMessage("Aplikacja potrzebuje dostępu do lokalizacji w celu działania.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ClientsActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("Aplikacja wymaga dostępu do lokalizacji. Zamykanie aplikacji.");
                        finish();
                    }
                })
                .show();
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Uprawnienia do lokalizacji udzielone");
            } else {
                showToast("Uprawnienia do lokalizacji nieudzielone. Zamykanie aplikacji.");
                finish();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}