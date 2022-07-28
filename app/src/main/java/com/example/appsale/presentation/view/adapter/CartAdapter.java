package com.example.appsale.presentation.view.adapter;


/**
 * Created by ntudroid on 7/26/2022.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsale.R;
import com.example.appsale.common.AppConstant;
import com.example.appsale.common.StringCommon;
import com.example.appsale.data.model.Food;
import com.example.appsale.data.model.Order;


import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<Order> listFoodsOrder;
    Context context;
    private OnItemClickFoodOrder onItemClickFoodOrder;

    public CartAdapter() {
        listFoodsOrder = new ArrayList<>();
    }

    public void updateListProduct(List<Order> data) {
        if (listFoodsOrder != null && listFoodsOrder.size() > 0) {
            listFoodsOrder.clear();
        }
        listFoodsOrder.addAll(data);
        notifyDataSetChanged();
    }

    public List<Order> getListFoodsOrder(){
        return listFoodsOrder;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_item_food, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(context, listFoodsOrder.get(position));
    }

    @Override
    public int getItemCount() {
        return listFoodsOrder.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvPrice;
        ImageView img;
        LinearLayout btnAddCart;

        public CartViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.textViewName);
            tvAddress = view.findViewById(R.id.textViewAddress);
            tvPrice = view.findViewById(R.id.textViewPrice);
            img = view.findViewById(R.id.imageView);
            btnAddCart = view.findViewById(R.id.button_add);

            btnAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickFoodOrder != null) {
                        onItemClickFoodOrder.onClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Context context, Order food) {
            tvName.setText(food.getName());
            tvAddress.setText(food.getAddress());
            tvPrice.setText(String.format("%s VND", StringCommon.formatCurrency(food.getPrice())));
            Glide.with(context)
                    .load(AppConstant.BASE_URL  + food.getImg())
                    .placeholder(R.drawable.ic_logo)
                    .into(img);
        }
    }

    public void setOnItemClickFoodOrder (OnItemClickFoodOrder onItemClickFoodOrder){
        this.onItemClickFoodOrder = onItemClickFoodOrder;
    }

    public interface OnItemClickFoodOrder {
        void onClick(int position);
    }
}
