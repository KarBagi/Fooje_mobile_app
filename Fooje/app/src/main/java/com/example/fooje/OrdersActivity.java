package com.example.fooje;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrdersActivity extends AppCompatActivity {

    private static final long DELAY_MILLIS = 3 * 60 * 1000;
    private final Handler handler = new Handler();
    String[] orders, orderDetails, products;
    List<List<Object>> ordersList;
    List<List<Object>> orderDetailsList = new ArrayList<>();
    List<List<Object>> productsList = new ArrayList<>();
    String apiUrl = MainActivity.apiUrl;
    int isEmpty = 0;

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            getOrders();
            handler.postDelayed(this, DELAY_MILLIS);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_orders_layout);

        updateRunnable.run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable);
    }

    private void showDetailedOrders() {

        if (isEmpty == 1) {

            getOrderDetails();
            getProductsFromOrders();

            LinearLayout ordersContainer = findViewById(R.id.ordersContainer);
            ordersContainer.removeAllViews();

            ordersContainer.removeAllViews();

            for (List<Object> order : ordersList) {
                View ordersInflatedView = null;

                if (order.get(3).toString().equals("złożone")) {
                    ordersInflatedView = LayoutInflater.from(this).inflate(R.layout.final_order_history_layout, null);

                    TextView acceptButton = ordersInflatedView.findViewById(R.id.acceptButton);
                    acceptButton.setOnClickListener(v -> {
                        PostApiRequest setStatusAccepted = new PostApiRequest();
                        setStatusAccepted.setOptionOrderStatus(apiUrl + "orders/" + Integer.parseInt(order.get(0).toString()) + "/updateState/Zatwierdzony");
                        setStatusAccepted.execute();

                        getOrders();
                    });

                    TextView cancelButton = ordersInflatedView.findViewById(R.id.cancelButton);
                    cancelButton.setOnClickListener(v -> {
                        PostApiRequest setStatusCanceled = new PostApiRequest();
                        setStatusCanceled.setOptionOrderStatus(apiUrl + "orders/" + Integer.parseInt(order.get(0).toString()) + "/updateState/Anulowany");
                        setStatusCanceled.execute();

                        getOrders();
                    });
                } else if (order.get(3).toString().equals("Zatwierdzony") || order.get(3).toString().equals("Anulowany")) {
                    ordersInflatedView = LayoutInflater.from(this).inflate(R.layout.orders_history_layout, null);
                }

                TextView clientName = ordersInflatedView.findViewById(R.id.clientNameTextView);
                clientName.setText(getClientName(Integer.parseInt(order.get(2).toString())));

                TextView orderDate = ordersInflatedView.findViewById(R.id.dateTextView);
                orderDate.setText(order.get(4).toString());

                TextView status = ordersInflatedView.findViewById(R.id.statusTextView);
                status.setText(order.get(3).toString());

                LinearLayout positionsContainer = ordersInflatedView.findViewById(R.id.positionContainer);

                for (List<Object> orderDetails : orderDetailsList) {

                    if (order.get(0).toString().equals(orderDetails.get(1).toString())) {

                        for (List<Object> position : productsList) {

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
        }
    }

    private void getProductsFromOrders() {
        products = new String[0];
        productsList.clear();
        for (List<Object> product : orderDetailsList) {
            try {
                GetApiRequest getProducts = new GetApiRequest();
                getProducts.setOptionsGetProducts(apiUrl + "products/byproductid?productId=" + Integer.parseInt(product.get(2).toString()));
                products = getProducts.execute().get();
                if (products[0].length() > 4)
                    productsList.add(getProducts.convertJsonToList(products[0]).get(0));
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
                Log.d("kkkk", getOdrerDetails.convertJsonToList(orderDetails[0]).get(0).toString());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getOrders() {
        orders = new String[0];

        try {
            GetApiRequest getOrders = new GetApiRequest();
            getOrders.setOptionsOrders(apiUrl + "orders/by-restaurant/" + MainActivity.restaurationId);
            orders = getOrders.execute().get();

            if (orders[0].length() < 4) isEmpty = 0;
            else {
                isEmpty = 1;
                ordersList = getOrders.convertJsonToList(orders[0]);
                Collections.reverse(ordersList);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        showDetailedOrders();
    }

    private String getClientName(int clientId) {
        try {
            GetApiRequest clientNameGettypeRequest = new GetApiRequest();
            clientNameGettypeRequest.setOptions(apiUrl + "clients/" + clientId, "name");
            String[] clientName = clientNameGettypeRequest.execute().get();
            return clientName[0];
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBack(View view) {
        finish();
    }
}
