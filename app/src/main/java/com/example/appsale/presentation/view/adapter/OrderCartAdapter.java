package com.example.appsale.presentation.view.adapter;

/**
 * Created by ntudroid on 7/26/2022.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsale.R;
import com.example.appsale.common.AppConstant;
import com.example.appsale.common.StringCommon;
import com.example.appsale.data.model.Food;

import java.util.ArrayList;
import java.util.List;

public class OrderCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Food> listFoods;
    Context context;
    private OnItemClickOrder onItemClickOrder;

    private int ORDER_ITEM = 0;
    private int EMPTY_ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        if(listFoods.get(position) == null){
            return EMPTY_ITEM;
        }
        return ORDER_ITEM;
    }

    public OrderCartAdapter(){
        listFoods = new ArrayList<>();
    }

    public List<Food> getListFoods(){
        return listFoods;
    }

    public void updateListProduct(List<Food> data) {
        if (listFoods != null && listFoods.size() > 0) {
            listFoods.clear();
        }
        listFoods.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OrderViewHolder){
            ((OrderViewHolder) holder).bind(context, listFoods.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listFoods.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice;
        ImageView img;
        TextView txtQuantity;
        Button btnPlusQuantity, btnMinusQuantity;

        public OrderViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.textViewNameOrderHistory);
            tvPrice = view.findViewById(R.id.textViewPrice);
            img = view.findViewById(R.id.imageViewOrder);
            btnPlusQuantity = view.findViewById(R.id.btnPlus);
            btnMinusQuantity = view.findViewById(R.id.btnMinus);
            txtQuantity = view.findViewById(R.id.textviewQuantity);

        }

        public void bind(Context context, Food food) {
            int quantity = food.getQuantity();
            tvName.setText(food.getName());;
            txtQuantity.setText(quantity+" ");
            tvPrice.setText(String.format("%s VND", StringCommon.formatCurrency(food.getPrice())));
            Glide.with(context)
                    .load(AppConstant.BASE_URL  + food.getImg())
                    .placeholder(R.drawable.ic_logo)
                    .into(img);

            btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickOrder != null) {
                        onItemClickOrder.onClick(getAdapterPosition(),String.valueOf(quantity+1));
                    }
                }
            });
            btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickOrder != null) {
                        onItemClickOrder.onClick(getAdapterPosition(),String.valueOf(quantity-1));
                    }
                }
            });
        }
    }

    public void setOnItemClickOrder(OnItemClickOrder onItemClickOrder){
        this.onItemClickOrder = onItemClickOrder;
    }

    public interface OnItemClickOrder {
        void onClick(int position, String quantity);
    }
}
