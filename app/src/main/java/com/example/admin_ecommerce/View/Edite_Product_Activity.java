package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin_ecommerce.Model.ItemsModel;
import com.example.admin_ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Edite_Product_Activity extends AppCompatActivity {

    ImageView product_image;
    TextInputLayout Product_name, Product_price;
    RelativeLayout update_button;
    MaterialAlertDialogBuilder progressDialog;
    AlertDialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_product);
        Setting_Action_Bar_Status_Bar();

        initialisation();
        Setting_initial_elements();
        Handeling_On_Clicks();

    }

    public void Handeling_On_Clicks() {
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("name").equals(Product_name.getEditText().getText().toString()) &&
                        getIntent().getStringExtra("price").equals(Product_price.getEditText().getText().toString())) {
                    Toast.makeText(Edite_Product_Activity.this, "Nothing changed !", Toast.LENGTH_SHORT).show();
                } else if (Product_name.getEditText().getText().toString().isEmpty()) {
                    Product_name.getEditText().setError("Enter product name !");
                } else if ( Product_price.getEditText().getText().toString().isEmpty()){
                    Product_price.getEditText().setError("Enter product price !");
                }else {
                    // update
                    progressDialog.setTitle("Updating the product!");
                    progressDialog.setMessage("Wait a minute please ...");
                    progressDialog.setCancelable(false);
                    progressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
                    progressDialog.setIcon(R.drawable.update_profile_image);
                    progressDialog.setCancelable(false);
                    dialog = progressDialog.show();
                    dialog.show();
                    update_Product(Product_name.getEditText().getText().toString(), Product_price.getEditText().getText().toString());
                }
            }
        });
    }

    public void update_Product(String new_Name, String new_Price) {
        FirebaseDatabase.getInstance().getReference().child("Users").child("Products").child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot category : snapshot.getChildren()) {
                        for (DataSnapshot item : category.getChildren()) {
                            ItemsModel model = item.getValue(ItemsModel.class);

                            if (model.getProductName().equals(getIntent().getStringExtra("name")) &&
                                    model.getPrice().equals(getIntent().getStringExtra("price")) &&
                                    model.getImageUrl().equals(getIntent().getStringExtra("imageurl"))) {

                                FirebaseDatabase.getInstance().getReference().child("Users").child("Products").child("items").child(category.getKey()).child(item.getKey()).child("Price").setValue(new_Price).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference().child("Users").child("Products").child("items").child(category.getKey()).child(item.getKey()).child("productName").setValue(new_Name).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // all good
                                                        dialog.dismiss();
                                                        Toast.makeText(Edite_Product_Activity.this, "Product updated successfully !", Toast.LENGTH_SHORT).show();
                                                        // refresh
                                                        ViewAllItemsActivity.adapter.items_list = ViewAllItemsActivity.ItemsList();
                                                        ViewAllItemsActivity.adapter.notifyDataSetChanged();
                                                        finish();
                                                    } else {
                                                        dialog.dismiss();
                                                        Toast.makeText(Edite_Product_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(Edite_Product_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Edite_Product_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void initialisation() {
        progressDialog = new MaterialAlertDialogBuilder(this);
        update_button = findViewById(R.id.update_button);
        product_image = findViewById(R.id.image_product);
        Product_name = findViewById(R.id.name);
        Product_price = findViewById(R.id.price);
    }

    public void Setting_initial_elements() {
        Picasso.get().load(getIntent().getStringExtra("imageurl")).placeholder(R.drawable.holder2).into(product_image);
        Product_price.getEditText().setText(getIntent().getStringExtra("price"));
        Product_name.getEditText().setText(getIntent().getStringExtra("name"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        //getSupportActionBar().hide();

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        // setting the keyboard
        setUpKeybaord(findViewById(R.id.update), Edite_Product_Activity.this);

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

    public static void SettingKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public static void setUpKeybaord(View view, Activity activity) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    SettingKeyboard(activity);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setUpKeybaord(innerView, activity);
            }
        }
    }
}