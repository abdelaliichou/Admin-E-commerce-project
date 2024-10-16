package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin_ecommerce.Controller.Card_items_Adapter;
import com.example.admin_ecommerce.Model.CardItemModel;
import com.example.admin_ecommerce.Model.Delivery_Order_Model;
import com.example.admin_ecommerce.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OrderDetails_Activity extends AppCompatActivity {

    TextView email , date , time , totalPrice , totalNumber , id ;
    RecyclerView OrderRecyclerView ;
    Card_items_Adapter OrderAdapter ;
    ProgressBar progressBar ;
    MaterialCardView ProcessButton ;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Setting_Ui();
        initialisation();
        OnClicks();
        SetInitialInformations();
        SettingRecycler();
    }

    public void SetInitialInformations(){
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Delivery_Order_Model model = snapshot.getValue(Delivery_Order_Model.class);
                    id.setText(model.getOdrderID());
                    email.setText(model.getUserEmail());
                    date.setText(model.getDate());
                    time.setText(model.getTime());
                    totalNumber.setText(model.getProductsNumber());
                    totalPrice.setText(model.getTotalPrice());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderDetails_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void OnClicks(){
    ProcessButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(OrderDetails_Activity.this,Delivery_Activity.class);
            intent.putExtra("id",id.getText().toString().trim());
            intent.putExtra("uri",getIntent().getStringExtra("uri"));
            startActivity(intent);
        }
    });
    }

    public void initialisation(){
        ProcessButton = findViewById(R.id.process);
        progressBar = findViewById(R.id.progress);
        email = findViewById(R.id.email);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        totalPrice = findViewById(R.id.products_total_price);
        totalNumber = findViewById(R.id.total_products);
        id = findViewById(R.id.id);
        OrderRecyclerView = findViewById(R.id.order_list);
    }

    public void SettingRecycler(){
        OrderAdapter = new Card_items_Adapter(getAllOrderProducts(),OrderDetails_Activity.this ,getIntent().getStringExtra("uri"));
        OrderRecyclerView.setAdapter(OrderAdapter);
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(OrderDetails_Activity.this, LinearLayoutManager.VERTICAL, false));
    }

    public ArrayList<CardItemModel> getAllOrderProducts(){
        ArrayList<CardItemModel> liste = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).child("orderElements").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot cardItem : snapshot.getChildren()){
                        CardItemModel model = cardItem.getValue(CardItemModel.class);
                        liste.add(model);
                    }
                    OrderAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderDetails_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        return liste ;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
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
