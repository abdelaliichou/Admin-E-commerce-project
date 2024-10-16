package com.example.admin_ecommerce.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_ecommerce.Model.CardItemModel;
import com.example.admin_ecommerce.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Card_items_Adapter extends RecyclerView.Adapter<Card_items_Adapter.ViewHolder> {
    public ArrayList<CardItemModel> list;
    Context context;
    String customer_UID;

    public Card_items_Adapter(ArrayList<CardItemModel> list, Context context, String customer_UID) {
        this.list = list;
        this.context = context;
        this.customer_UID = customer_UID; // this will give us the uid of the customer , we will pass  it when when click on a customer
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_receipt, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameText.setText(list.get(position).getName() + "..");
        holder.priceText.setText(list.get(position).getPrice());
        holder.total_items_Text.setText("Total: " + list.get(position).getQuantity());
        holder.dateText.setText(list.get(position).getDate());
        holder.timeText.setText(list.get(position).getTime());
        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));
        Picasso.get().load(list.get(position).getImageurl()).placeholder(R.drawable.holder).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView dateText, timeText, total_items_Text, priceText, nameText;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_item);
            image = itemView.findViewById(R.id.item_image);
            dateText = itemView.findViewById(R.id.date);
            timeText = itemView.findViewById(R.id.time);
            total_items_Text = itemView.findViewById(R.id.total_items);
            priceText = itemView.findViewById(R.id.item_price);
            nameText = itemView.findViewById(R.id.item_name);
        }
    }
}
