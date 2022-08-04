package com.example.appsale.data.repository;

import android.content.Context;

import com.example.appsale.data.remote.ApiService;
import com.example.appsale.data.remote.RetrofitClient;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.data.remote.dto.OrderDTO;

import java.util.HashMap;

import retrofit2.Call;

public class OrderRepository {
    private ApiService apiService;

    public OrderRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<OrderDTO>> addToCart(String idFood) {
        HashMap<String,String> body = new HashMap<>();
        body.put("id_product",idFood);
        return apiService.addToCart(body);

    }

    public Call<AppResource<OrderDTO>> cartOrder() {
        return apiService.cartOrder();
    }

    public Call<AppResource<OrderDTO>> updateCart(String idFood, String idCart, String quantity) {
        HashMap<String,String> body = new HashMap<>();
        body.put("id_product",idFood);
        body.put("id_cart",idCart);
        body.put("quantity",quantity);
        return apiService.updateCart(body);

    }

    public Call<AppResource<OrderDTO>> confirmOrdersCart(String idCart){
        HashMap<String,String> body = new HashMap<>();
        body.put("id_cart",idCart);
        body.put("status","true");
        return apiService.confirmItemCart(body);

    }

}