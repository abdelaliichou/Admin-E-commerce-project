package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin_ecommerce.Model.UserModel;
import com.example.admin_ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class About_customer_Activity extends AppCompatActivity {

    TextView name, email, phone;
    RelativeLayout call, sendEmail;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_customer);
        Setting_Action_Bar_Status_Bar();

        initalisation();
        intialiUserData();
        handelingOnClicks();

    }

    public void handelingOnClicks() {
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString().substring(8,email.getText().length())});
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Thank you for the trust !");
                startActivity(Intent.createChooser(intent,"Send Email"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0"+phone.getText().toString().trim()));
                startActivity(intent);
            }
        });
    }

    public void intialiUserData() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("uri")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    name.setText(user.getName());
                    email.setText( user.getEmail());
                    phone.setText("0" + String.valueOf(user.getPhonenumber()));
                } else {
                    Toast.makeText(About_customer_Activity.this, "No customer found !", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(About_customer_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initalisation() {
        call = findViewById(R.id.phone_contact);
        sendEmail = findViewById(R.id.email_contact);
        name = findViewById(R.id.name_text);
        email = findViewById(R.id.email_text);
        phone = findViewById(R.id.phone_text);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        //getSupportActionBar().hide();

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorlast));

        // to change the color of the icons in status bar to dark
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
//        }
        // to change the color of the icons in the navigation bar to dark
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorlast)); //setting bar color
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
//        }
    }
}