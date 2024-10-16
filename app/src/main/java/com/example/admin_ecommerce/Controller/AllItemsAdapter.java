package com.example.admin_ecommerce.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_ecommerce.Model.ItemsModel;
import com.example.admin_ecommerce.R;
import com.example.admin_ecommerce.View.Edite_Product_Activity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ViewHolder> {

    public ArrayList<ItemsModel> items_list = new ArrayList<>();
    Context context;
    MaterialAlertDialogBuilder progressDialog ;
    MaterialAlertDialogBuilder ProgressDialog2 ;
    AlertDialog dialog2 ;
    AlertDialog dialog;


    public AllItemsAdapter(ArrayList<ItemsModel> items_list, Context context) {
        this.context = context;
        this.items_list = items_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.populair_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(items_list.get(position).getProductName());
        holder.productPrice.setText("$" + items_list.get(position).getPrice());
        Picasso.get().load(items_list.get(position).getImageUrl()).placeholder(R.drawable.holder).into(holder.productImage);
        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));
        holder.view_more.setOnClickListener(new View.OnClickListener() {
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
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                progressDialog = new MaterialAlertDialogBuilder(view.getContext());
                progressDialog.setTitle("Confirmation text !");
                progressDialog.setMessage("Do you want to delete this product from your card ?");
                progressDialog.setCancelable(false);
                progressDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialog2 = new MaterialAlertDialogBuilder(view.getContext());
                        ProgressDialog2.setTitle("Deleting the product !");
                        ProgressDialog2.setMessage("Wait for the operation to complete ! ");
                        ProgressDialog2.setCancelable(false);
                        ProgressDialog2.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog_back));
                        ProgressDialog2.setCancelable(false);
                        dialog2 = ProgressDialog2.show();
                        dialog2.show();
                        DeleteItemFromDataBase(position);
                        dialog.dismiss();
                    }
                });
                progressDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
                progressDialog.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog_back));
                progressDialog.setIcon(R.drawable.delete);
                progressDialog.setCancelable(true);
                dialog = progressDialog.show();
                dialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage ;
        TextView productName, productPrice;
        MaterialCardView card;
        RelativeLayout view_more ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            card = itemView.findViewById(R.id.card);
            view_more = itemView.findViewById(R.id.more_informations);

        }
    }

    public void DeleteItemFromDataBase(int position) {
        DatabaseReference Root = FirebaseDatabase.getInstance().getReference();
        Root.child("Users").child("Products").child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // searching for the items that have the same image url which means its the same object , and we delete them
                for (DataSnapshot category : snapshot.getChildren()) {
                    for (DataSnapshot item : category.getChildren()){
                        if (item.getValue(ItemsModel.class).getImageUrl().equals(items_list.get(position).getImageUrl())) {
                            // means that the position adapter image is the same as the one in the firebase
                            Root.child("Users").child("Products").child("items").child(category.getKey()).child(item.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(context, items_list.get(position).getProductName() + " Deleted !", Toast.LENGTH_SHORT).show();
                                    items_list.remove(items_list.get(position));
                                    notifyDataSetChanged();
                                    dialog2.dismiss();
                                }
                            });
                        }
                    }
                }
                dialog2.dismiss();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog2.dismiss();
            }
        });
    }

}
