package com.example.appsale.presentation.view.activity.detail_cart;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsale.R;


public class DetailCartActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    TextView    totalTxt, createCart, emptyCartTxt;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);

        initView();
        initList();
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerViewCart);
        totalTxt = findViewById(R.id.txtTotalPrice);
        createCart = findViewById(R.id.button_create_cart);
        emptyCartTxt = findViewById(R.id.emptyCart);
        scrollView = findViewById(R.id.scrollView2);
    }
    private void initList(){
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

    }
}