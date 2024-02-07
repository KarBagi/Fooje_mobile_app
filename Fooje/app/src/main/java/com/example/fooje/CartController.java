package com.example.fooje;

public class CartController {
    private static CartController instance;
    public int restaurantId = 999999;

    public static CartController getInstance(){
        if(instance == null){
            instance = new CartController();
        }
        return  instance;
    }

    public void setRestaurantId(int id){
        restaurantId = id;
    }

    public int getRestaurantId(){
        return restaurantId;
    }

}
