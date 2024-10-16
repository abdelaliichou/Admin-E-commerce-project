package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.admin_ecommerce.Controller.AllCustomersAdapter;
import com.example.admin_ecommerce.Model.CustomerModel;
import com.example.admin_ecommerce.Model.Delivery_Order_Model;
import com.example.admin_ecommerce.Model.UserModel;
import com.example.admin_ecommerce.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    public static DatabaseReference root;
    public static RecyclerView CustomersRecycler;
    public static ImageView empty_image;
    public static AllCustomersAdapter adapter;
    public static SwipeRefreshLayout refresh;
    public static ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Setting_Ui();

        initialisation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        settingRecycler();
        refreshOrders();

    }

    public void refreshOrders() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.items_list = gettingCustomers();
                adapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });
    }

    public void initialisation() {
        empty_image = findViewById(R.id.empty);
        empty_image.setVisibility(View.GONE);
        refresh = findViewById(R.id.refresh);
        CustomersRecycler = findViewById(R.id.all_users_recycler);
        root = FirebaseDatabase.getInstance().getReference();
        shimmerFrameLayout = findViewById(R.id.shimmer);
    }

    public void settingRecycler() {
        adapter = new AllCustomersAdapter(gettingCustomers(), MainActivity.this);
        CustomersRecycler.setAdapter(adapter);
        CustomersRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    public static ArrayList<CustomerModel> gettingCustomers() {

        ArrayList<CustomerModel> list = new ArrayList<>();
        root.child("Delivery").child("OnProcess").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot user : snapshot.getChildren()) {
                    // we get the uid of the customer
                    root.child("Users").child(user.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue(UserModel.class).getName();
                            String email = snapshot.getValue(UserModel.class).getEmail();
                            String userUri = user.getKey();
                            CustomerModel model = new CustomerModel(name, email, userUri);
                            shimmerFrameLayout.setVisibility(View.GONE);
                            shimmerFrameLayout.stopShimmer();
                            list.add(model);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            shimmerFrameLayout.setVisibility(View.GONE);
                            shimmerFrameLayout.stopShimmer();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
            }
        });
        return list;
    }

    public void Setting_Ui() {

        //Hiding action bar
        //getSupportActionBar().hide();
        // setting the keyboard
        // Utils.setUpKeybaord(findViewById(R.id.parent),MainActivity.this);

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.white));

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