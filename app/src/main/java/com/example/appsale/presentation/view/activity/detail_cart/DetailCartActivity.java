package com.example.appsale.presentation.view.activity.detail_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsale.R;
import com.example.appsale.common.StringCommon;
import com.example.appsale.data.model.Food;
import com.example.appsale.data.model.Order;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.presentation.view.activity.detail_cart.DetailCartViewModel;
import com.example.appsale.presentation.view.activity.home.HomeActivity;
import com.example.appsale.presentation.view.adapter.OrderCartAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailCartActivity extends AppCompatActivity {

    DetailCartViewModel detailCartViewModel;
    private OrderCartAdapter orderCartAdapter;
    private RecyclerView rcvCart;
    TextView    totalTxt, createCart, emptyCartTxt;
    private ScrollView scrollView;
    LinearLayout layoutLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);

        addControls();
        observerData();
        events();
    }

    private void observerData() {
        detailCartViewModel.getOrder().observe(this, (Observer<AppResource<Order>>) orderAppResource -> {
            switch (orderAppResource.status) {
                case LOADING:
                    layoutLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    layoutLoading.setVisibility(View.GONE);
                    orderCartAdapter.updateListProduct(orderAppResource.data.getFoods());
                    totalTxt.setText("Sum Price: "+String.format("%s VND", StringCommon.formatCurrency(orderAppResource.data.getPrice())));
                    setUpdateCart(orderAppResource.data.getId());
                    setConfirmCart(orderAppResource.data.getId());
                    emptyCartTxt.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    Toast.makeText(DetailCartActivity.this, orderAppResource.message, Toast.LENGTH_SHORT).show();
                    layoutLoading.setVisibility(View.GONE);
                    emptyCartTxt.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    List<Food> foodList = new ArrayList<>();
                    foodList.add(null);
                    orderCartAdapter.updateListProduct(foodList);
                    createCart.setEnabled(false);
                    break;
            }
        });
    }

    private void setUpdateCart(String idCart) {
        orderCartAdapter.setOnItemClickOrder((position, quantity) -> detailCartViewModel.UpdateCart(orderCartAdapter.getListFoods().get(position).getId(),idCart,quantity));

    }

    private void setConfirmCart(String id) {
        createCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailCartViewModel.confirmOrdersCart(id);
                Intent intent = new Intent(DetailCartActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(DetailCartActivity.this,"Order Success",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void events() {
        detailCartViewModel.CartOrder();

    }

    private void addControls() {
        layoutLoading = findViewById(R.id.layout_loading);
        createCart = findViewById(R.id.button_create_cart);
        totalTxt = findViewById(R.id.txtTotalPrice);
        emptyCartTxt = findViewById(R.id.emptyCart);
        scrollView = findViewById(R.id.scrollView2);
        detailCartViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new DetailCartViewModel(DetailCartActivity.this);
            }
        }).get(DetailCartViewModel.class);
        orderCartAdapter = new OrderCartAdapter();

        // Setup RecyclerView
        rcvCart = findViewById(R.id.recyclerViewCart);
        rcvCart.setAdapter(orderCartAdapter);
        rcvCart.setHasFixedSize(true);

    }
}