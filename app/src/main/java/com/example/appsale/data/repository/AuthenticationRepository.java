package com.example.appsale.data.repository;

import android.content.Context;

import com.example.appsale.data.remote.ApiService;
import com.example.appsale.data.remote.RetrofitClient;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.data.remote.dto.UserDTO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;

/**
 * Created by pphat on 7/14/2022.
 */
public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<UserDTO>> signIn(String email, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return apiService.signIn(map);
    }
    public Call<AppResource<UserDTO>> signUp(String name, String location, String email, String phone, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("address", location);
        map.put("email", email);
        map.put("phone", phone);
        map.put("password", password);
        return apiService.signUp(map);
    }
}
