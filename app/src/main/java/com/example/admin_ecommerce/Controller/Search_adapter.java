package com.example.admin_ecommerce.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_ecommerce.Model.ItemsModel;
import com.example.admin_ecommerce.R;
import com.example.admin_ecommerce.View.Edite_Product_Activity;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Search_adapter extends RecyclerView.Adapter<Search_adapter.ViewHolder> implements Filterable {

    public ArrayList<ItemsModel> items_list = new ArrayList<>();
    private Context context;
    int exists = 0;


    public Search_adapter(ArrayList<ItemsModel> items_list, Context context) {
        this.context = context;
        this.items_list = items_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(items_list.get(position).getProductName());
        holder.productPrice.setText(items_list.get(position).getPrice());
        Picasso.get().load(items_list.get(position).getImageUrl()).placeholder(R.drawable.holder).into(holder.productImage);
        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edite_Product_Activity.class);
                intent.putExtra("name", items_list.get(position).getProductName());
                intent.putExtra("price", items_list.get(position).getPrice());
                intent.putExtra("imageurl", items_list.get(position).getImageUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edite_Product_Activity.class);
                intent.putExtra("name", items_list.get(position).getProductName());
                intent.putExtra("price", items_list.get(position).getPrice());
                intent.putExtra("imageurl", items_list.get(position).getImageUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        MaterialCardView card;
        RelativeLayout go ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.photo);
            productName = itemView.findViewById(R.id.nom);
            productPrice = itemView.findViewById(R.id.prix);
            card = itemView.findViewById(R.id.card_item);
            go = itemView.findViewById(R.id.go);

        }
    }

    // for the space between items in recyclerview
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int halfSpace;

        public SpacesItemDecoration(int space) {
            this.halfSpace = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getPaddingLeft() != halfSpace) {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
                parent.setClipToPadding(false);
            }

            outRect.top = halfSpace;
            outRect.bottom = halfSpace;
            outRect.left = halfSpace;
            outRect.right = halfSpace;
        }
    }

}
