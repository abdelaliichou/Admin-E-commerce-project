package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.admin_ecommerce.Controller.AllOrdersAdapter;
import com.example.admin_ecommerce.Model.Delivery_Order_Model;
import com.example.admin_ecommerce.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class Costumer_Orders_Activity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    DatabaseReference root;
    RecyclerView oroderRecycler;
    AllOrdersAdapter adapter;
    public static ShimmerFrameLayout shimmerFrameLayout;
    TextView totalPrice;
    TextView totalItems;
    RelativeLayout contactButton;
    public static ImageView empty_image ;
    float productsnumer = 0f;
    float allprice = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer_orders);
        Setting_Ui();

        initialisation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        SettingRecycler();
        initializePricesTexts();
        refreshOrders();
        onClicks();
    }

    public void onClicks() {
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Costumer_Orders_Activity.this, About_customer_Activity.class);
                intent.putExtra("uri", getIntent().getStringExtra("uri"));
                startActivity(intent);
            }
        });
    }


    public void refreshOrders() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AllOrdersAdapter.OrdersList = getallorders();
                adapter.notifyDataSetChanged();
                productsnumer = 0 ;
                allprice = 0f ;
                initializePricesTexts();
                refresh.setRefreshing(false);
            }
        });
    }

    public void initialisation() {
        empty_image = findViewById(R.id.empty_image);
        refresh = findViewById(R.id.refresh);
        contactButton = findViewById(R.id.contact_costumer);
        totalItems = findViewById(R.id.product_number);
        totalPrice = findViewById(R.id.products_total_price);
        root = FirebaseDatabase.getInstance().getReference();
        oroderRecycler = findViewById(R.id.user_recycler);
        shimmerFrameLayout = findViewById(R.id.shimmer);
    }

    public void SettingRecycler() {
        adapter = new AllOrdersAdapter(getallorders(), Costumer_Orders_Activity.this, getIntent().getStringExtra("uri"));
        oroderRecycler.setAdapter(adapter);
        oroderRecycler.setLayoutManager(new LinearLayoutManager(Costumer_Orders_Activity.this, LinearLayoutManager.VERTICAL, false));
    }

    public ArrayList<Delivery_Order_Model> getallorders() {
        ArrayList<Delivery_Order_Model> liss = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(getIntent().getStringExtra("uri")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    liss.clear();
                    for (DataSnapshot order : snapshot.getChildren()) {
                        Delivery_Order_Model model = order.getValue(Delivery_Order_Model.class);
                        // here we will get the completed orders to compare between the completed and the nonProcess ( just to not show the completed orders here )
                        FirebaseDatabase.getInstance().getReference().child("Delivery").child("completed").child(getIntent().getStringExtra("uri")).child(model.getOdrderID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Delivery_Order_Model CompletedOrder = snapshot.getValue(Delivery_Order_Model.class);
                                if (CompletedOrder == null) { // means that this order id doesn't exist in the completed child , so we add it to the list
                                    shimmerFrameLayout.setVisibility(View.GONE);
                                    shimmerFrameLayout.stopShimmer();
                                    liss.add( 0,model);
                                    adapter.notifyDataSetChanged();
                                    //adapter.notifyItemInserted(0);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Costumer_Orders_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                shimmerFrameLayout.stopShimmer();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Costumer_Orders_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
            }
        });
        return liss;
    }

    public void initializePricesTexts() {
        root.child("Delivery").child("OnProcess").child(getIntent().getStringExtra("uri")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()) {
                    Delivery_Order_Model model = i.getValue(Delivery_Order_Model.class);
                    productsnumer = productsnumer + Float.parseFloat(model.getProductsNumber());
                    allprice = allprice + Float.parseFloat(model.getTotalPrice());
                }
                totalItems.setText(String.valueOf(productsnumer));
                totalPrice.setText(String.valueOf(allprice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Costumer_Orders_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Setting_Ui() {

        //Hiding action bar
        //getSupportActionBar().hide();
        // setting the keyboard
        // Utils.setUpKeybaord(findViewById(R.id.parent),MainActivity.this);

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
}