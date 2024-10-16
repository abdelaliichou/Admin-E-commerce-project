package com.example.admin_ecommerce.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_ecommerce.View.About_customer_Activity;
import com.example.admin_ecommerce.View.Delivery_Activity;
import com.example.admin_ecommerce.View.MainActivity;
import com.example.admin_ecommerce.Model.CustomerModel;
import com.example.admin_ecommerce.R;
import com.example.admin_ecommerce.View.Costumer_Orders_Activity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AllCustomersAdapter extends RecyclerView.Adapter<AllCustomersAdapter.ViewHolder> {

    public ArrayList<CustomerModel> items_list = new ArrayList<>();
    private Context context;



    public AllCustomersAdapter(ArrayList<CustomerModel> items_list, Context context) {
        this.context = context;
        this.items_list = items_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.costumer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Customer_name.setText(items_list.get(position).getName());
        holder.Customer_email.setText(items_list.get(position).getEmail()+"..");
       // holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));
        holder.goToOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Costumer_Orders_Activity.class);
                intent.putExtra("uri", items_list.get(position).getCustomerURI());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.goToOrders.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, Delivery_Activity.class);
                intent.putExtra("uri", items_list.get(position).getCustomerURI());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Costumer_Orders_Activity.class);
                intent.putExtra("uri", items_list.get(position).getCustomerURI());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.Customer_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, About_customer_Activity.class);
                intent.putExtra("uri", items_list.get(position).getCustomerURI());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (items_list.size() ==0){
            MainActivity.CustomersRecycler.setVisibility(View.GONE);
            MainActivity.empty_image.setVisibility(View.VISIBLE);
        } else {
            MainActivity.empty_image.setVisibility(View.GONE);
            MainActivity.CustomersRecycler.setVisibility(View.VISIBLE);
        }
        return items_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Customer_Image ;
        RelativeLayout goToOrders ;
        TextView Customer_name, Customer_email;
        MaterialCardView card ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_item);
           goToOrders =itemView.findViewById(R.id.go);
           Customer_email = itemView.findViewById(R.id.customer_email);
           Customer_Image = itemView.findViewById(R.id.customer_image);
           Customer_name = itemView.findViewById(R.id.customer_name);

        }
    }



}
