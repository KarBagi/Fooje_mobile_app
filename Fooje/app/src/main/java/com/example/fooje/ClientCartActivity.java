package com.example.fooje;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ClientCartActivity extends AppCompatActivity {
    String[] cartProducts, products, clientName, restaurantName, orderId;
    List<List<Object>> cartProductList = new ArrayList<>();
    List<List<Object>> productList = new ArrayList<>();
    double pricesSum;
    int isEmpty = 0;
    private final static int clientId = MainActivity.clientId;
    String apiUrl = MainActivity.apiUrl, price, jsonString;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yyyy", Locale.getDefault());
    Date currentDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_cart_layout);

        pricesSum = 0;

        getClientName();

        TextView clientNameTextView = findViewById(R.id.clientNameTextView);
        clientNameTextView.setText("Witaj, " + clientName[0]);

        TextView restaurantNameTextView = findViewById(R.id.restaurationName);
        restaurantNameTextView.setText("Brak zawartości");

        getProductsInCart();

        if (CartController.getInstance().getRestaurantId() != 999999 || isEmpty == 1) {
            showProducts();
        }
    }

    private void showProducts() {
        LinearLayout productsContainer = findViewById(R.id.cartContainer);
        productsContainer.removeAllViews();

        TextView restaurantNameTextView = findViewById(R.id.restaurationName);
        restaurantNameTextView.setText("Restauracja: " + restaurantName[0]);

        for (List<Object> position : productList) {
            showCartContent(productsContainer, position);
        }

        TextView price = findViewById(R.id.priceForCart);
        price.setText("Kwota do zapłaty: " + formatDouble(pricesSum) + " zł");
    }

    private void showCartContent(LinearLayout productsContainer, List<Object> position) {
        View positionView = LayoutInflater.from(this).inflate(R.layout.client_cart_position_layout, productsContainer, false);

        TextView menuTextView = positionView.findViewById(R.id.menuPositionView);
        menuTextView.setText(position.get(2).toString());

        TextView priceTextView = positionView.findViewById(R.id.priceTextView);
        priceTextView.setText(position.get(5).toString());

        LinearLayout deleteFromCartButton = positionView.findViewById(R.id.deleateFromCartButton);
        deleteFromCartButton.setOnClickListener(v -> {
            PostApiRequest addToCartRequest = new PostApiRequest();
            addToCartRequest.setOptionsDeleteFavorites(apiUrl + "carts/remove", Integer.parseInt(position.get(0).toString()), clientId);
            addToCartRequest.execute();

            price = removeLastZl(position.get(5).toString());
            pricesSum = pricesSum - Double.parseDouble(price);

            isEmpty = 0;

            getProductsInCart();

            if(isEmpty == 1)
                showProducts();
            else {
                productsContainer.removeAllViews();

                TextView restaurantNameTextView = findViewById(R.id.restaurationName);
                restaurantNameTextView.setText("Brak zawartości");

                TextView price = findViewById(R.id.priceForCart);
                price.setText("Kwota do zapłaty: 0.00 zł");

            }
        });

        TextView orderButton = findViewById(R.id.orderButton);
        orderButton.setOnClickListener(v -> {
            String dateString = dateFormat.format(currentDate);
            dateString = getCurrentTimeWithSeconds() + " " + dateString;

            PostApiRequest requestOrder = new PostApiRequest();
            jsonString = "{" +
                    " \"clientId\": " + clientId + ", " +
                    "\"restaurantId\": " + Integer.parseInt(position.get(1).toString()) + ", " +
                    " \"state\": \"złożone\", " +
                    " \"orderDate\": \"" + dateString + "\" " +
                    " }";

            requestOrder.setOptions(apiUrl + "orders/add", jsonString);
            requestOrder.execute();

            PostApiRequest deleteCart = new PostApiRequest();
            deleteCart.setOptionsDeleteCart(apiUrl + "carts/removeByClientId", clientId);
            deleteCart.execute();

            try {
                GetApiRequest getOrderId = new GetApiRequest();
                getOrderId.setOptionsGetFavoriteRestaurantsDetails(apiUrl + "orders/maxOrderId/" + clientId + "/" + Integer.parseInt(position.get(1).toString()));
                orderId = getOrderId.execute().get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (List<Object> orderDetails : productList) {
                PostApiRequest addOrderDetails = new PostApiRequest();
                String ordersJson = "{" +
                        " \"ordersId\": " + Integer.parseInt(orderId[0]) + ", " +
                        "\"productId\": " + Integer.parseInt(orderDetails.get(0).toString()) +
                        " }";
                addOrderDetails.setOptions(apiUrl + "orderDetails/add", ordersJson);
                addOrderDetails.execute();
            }

            productList.clear();
            cartProductList.clear();
            productsContainer.removeAllViews();

            getProductsInCart();

            if (isEmpty == 0) {
                TextView restaurantNameTextView = findViewById(R.id.restaurationName);
                restaurantNameTextView.setText("Brak zawartości");

                TextView endPrice = findViewById(R.id.priceForCart);
                endPrice.setText("Kwota do zapłaty: 0,00 zł");
            } else showProducts();
        });

        productsContainer.addView(positionView);

        price = removeLastZl(position.get(5).toString());

        pricesSum += Double.parseDouble(price);
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

    private void getProductsInCart() {
        cartProducts = new String[0];
        cartProductList.clear();

        try {
            GetApiRequest getCartProducts = new GetApiRequest();
            getCartProducts.setOptionsCartProducts(apiUrl + "carts/getByClientId/" + clientId);
            cartProducts = getCartProducts.execute().get();
            if (cartProducts[0].length() < 4) {
                CartController.getInstance().setRestaurantId(999999);
                isEmpty = 0;
            }
            else {
                isEmpty = 1;
                Log.d("asd", "asd");
            }

            if (CartController.getInstance().getRestaurantId() != 999999 || isEmpty == 1) {
                cartProductList = getCartProducts.convertJsonToList(cartProducts[0]);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        products = new String[0];
        productList.clear();

        if (isEmpty == 1) {
            for (List<Object> product : cartProductList) {
                try {
                    GetApiRequest getProduct = new GetApiRequest();
                    getProduct.setOptionsGetProducts(apiUrl + "products/byproductid?productId=" + Integer.parseInt(product.get(2).toString()));
                    products = getProduct.execute().get();
                    productList.add(getProduct.convertJsonToList(products[0]).get(0));
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                GetApiRequest restaurantNameGetTypeRequest = new GetApiRequest();
                restaurantNameGetTypeRequest.setOptions(apiUrl + "restaurations/" + productList.get(0).get(1), "restaurantName");
                restaurantName = restaurantNameGetTypeRequest.execute().get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void goBack(View view) {
        finish();
    }

    public static String removeLastZl(String input) {
        int lastIndex = input.lastIndexOf("zł");

        if (lastIndex != -1) {
            return input.substring(0, lastIndex).trim();
        }

        return input;
    }

    public static String formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(value);
    }

    private String getCurrentTimeWithSeconds() {
        // Pobieramy aktualny czas
        Calendar calendar = Calendar.getInstance();

        // Tworzymy obiekt SimpleDateFormat do formatowania czasu
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        // Formatujemy czas do postaci HH:mm:ss
        return sdf.format(calendar.getTime());
    }
}
