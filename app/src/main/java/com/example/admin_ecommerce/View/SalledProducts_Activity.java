package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin_ecommerce.Controller.AllOrdersAdapter;
import com.example.admin_ecommerce.Controller.Card_items_Adapter;
import com.example.admin_ecommerce.Model.CardItemModel;
import com.example.admin_ecommerce.Model.Delivery_Order_Model;
import com.example.admin_ecommerce.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SalledProducts_Activity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    ShimmerFrameLayout shimmerFrameLayout ;
    TextView totalPrice;
    TextView totalItems;
    DatabaseReference root ;
    MaterialAlertDialogBuilder ProgressDialog2  , ProgressDialog;
    AlertDialog dialog2 , dialog ;
    RecyclerView oroderRecycler;
    AllOrdersAdapter adapter;
    RelativeLayout delete ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salled_products);
        Setting_Ui();
        initialisation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        SettingRecycler();
        initializePricesTexts();
        HndelingOnClicks();

    }

    public void SettingRecycler() {
        adapter = new AllOrdersAdapter(getallorders(), SalledProducts_Activity.this);
        oroderRecycler.setAdapter(adapter);
        oroderRecycler.setLayoutManager(new LinearLayoutManager(SalledProducts_Activity.this, LinearLayoutManager.VERTICAL, false));
    }

    public void initialisation() {
        ProgressDialog2 = new MaterialAlertDialogBuilder(this);
        ProgressDialog = new MaterialAlertDialogBuilder(this);
        delete = findViewById(R.id.delete_all);
        totalItems = findViewById(R.id.product_number);
        totalPrice = findViewById(R.id.products_total_price);
        root = FirebaseDatabase.getInstance().getReference();
        oroderRecycler = findViewById(R.id.user_recycler);
        refresh = findViewById(R.id.refresh);
        shimmerFrameLayout = findViewById(R.id.shimmer);
    }

    public void Setting_Ui() {

        //Hiding action bar
        //getSupportActionBar().hide();
        // setting the keyboard
        //setUpKeybaord(findViewById(R.id.welcom), Welcom_Activity.this);

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorgray));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        // to change the color of the icons in the navigation bar to dark
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.white)); //setting bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
        }
    }

    public ArrayList<Delivery_Order_Model> getallorders() {
        ArrayList<Delivery_Order_Model> liss = new ArrayList<>();
        DatabaseReference head = FirebaseDatabase.getInstance().getReference();
        head.child("Delivery").child("completed").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()){
                    for(DataSnapshot order : user.getChildren()){
                        Delivery_Order_Model model = order.getValue(Delivery_Order_Model.class);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        liss.add(0, model);
                        adapter.notifyItemInserted(0);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SalledProducts_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return liss;
    }

    public void initializePricesTexts() {
        root.child("Delivery").child("completed").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float productsnumer = 0;
                float allprice = 0f;
                for (DataSnapshot user : snapshot.getChildren()) {
                    for (DataSnapshot order : user.getChildren()){
                        productsnumer = productsnumer + Float.parseFloat(order.getValue(Delivery_Order_Model.class).getProductsNumber());
                        allprice = allprice + Float.parseFloat(order.getValue(Delivery_Order_Model.class).getTotalPrice());
                    }
                }
                totalItems.setText(String.valueOf(productsnumer));
                totalPrice.setText(String.valueOf(allprice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SalledProducts_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void HndelingOnClicks(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog2.setTitle("Delete all the orders !");
                ProgressDialog2.setMessage("Do you want to delete all the archive ?");
                ProgressDialog2.setCancelable(true);
                ProgressDialog2.setIcon(getResources().getDrawable(R.drawable.delete));
                ProgressDialog2.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
                ProgressDialog2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialog.setTitle("Deleting all the contracts  !");
                        ProgressDialog.setMessage("Wait for the deleted to complete ! ");
                        ProgressDialog.setCancelable(false);
                        ProgressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
                        dialog = ProgressDialog.show();
                        dialog.show();
                        FirebaseDatabase.getInstance().getReference().child("Delivery").child("completed").removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                dialog.dismiss();
                                Toast.makeText(SalledProducts_Activity.this, "All orders are removed from archive !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SalledProducts_Activity.this,Welcom_Activity.class));
                                finish();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog2.dismiss();
                    }
                });
                dialog2 = ProgressDialog2.show();
                dialog2.show();
            }
        });
    }

}