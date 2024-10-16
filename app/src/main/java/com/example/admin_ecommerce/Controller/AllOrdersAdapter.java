package com.example.admin_ecommerce.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_ecommerce.Model.Delivery_Order_Model;
import com.example.admin_ecommerce.R;
import com.example.admin_ecommerce.View.Costumer_Orders_Activity;
import com.example.admin_ecommerce.View.OrderDetails_Activity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.ViewHolder> {
    public static ArrayList<Delivery_Order_Model> OrdersList = new ArrayList<>();
    private Context context;
    private String UID ;

    public AllOrdersAdapter(ArrayList<Delivery_Order_Model> OrdersList, Context context,String UID) {
        this.context = context;
        this.OrdersList = OrdersList;
        this.UID = UID ;
    }

    public AllOrdersAdapter(ArrayList<Delivery_Order_Model> OrdersList, Context context) {
        this.context = context;
        this.OrdersList = OrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item2, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery_Order_Model model = OrdersList.get(position);
        holder.ID.setText(model.getOdrderID());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.totalProducts.setText(model.getProductsNumber());
        holder.totalPrice.setText(model.getTotalPrice());
        holder.email.setText(model.getUserEmail());
        holder.cardParent.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));
    }

    @Override
    public int getItemCount() {
        if (Costumer_Orders_Activity.empty_image != null){
            if (OrdersList.size() == 0){
                Costumer_Orders_Activity.empty_image.setVisibility(View.VISIBLE);
            }else {
                Costumer_Orders_Activity.empty_image.setVisibility(View.GONE);
            }
        }
        return OrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView email , totalPrice , totalProducts , date , time ,ID ;
        MaterialCardView MoreInfoButton, cardParent ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardParent = itemView.findViewById(R.id.card_item);
            email = itemView.findViewById(R.id.orderEmail);
            totalPrice = itemView.findViewById(R.id.orderPrice);
            totalProducts = itemView.findViewById(R.id.orderTotalProducts);
            date = itemView.findViewById(R.id.orderDate);
            time = itemView.findViewById(R.id.orderTime);
            ID = itemView.findViewById(R.id.orderId);
            MoreInfoButton = itemView.findViewById(R.id.more);
            if (UID != null){ // means that we are using the second constructor with the no UID parameter , which is used by the SalledProductsActivity
                MoreInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, OrderDetails_Activity.class);
                        intent.putExtra("id", OrdersList.get(getAdapterPosition()).getOdrderID());
                        intent.putExtra("uri", UID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                cardParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, OrderDetails_Activity.class);
                        intent.putExtra("id", OrdersList.get(getAdapterPosition()).getOdrderID());
                        intent.putExtra("uri", UID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }
}
