package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin_ecommerce.Model.ItemsModel;
import com.example.admin_ecommerce.Model.Messaging_Service;
import com.example.admin_ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Welcom_Activity extends AppCompatActivity {

    MaterialCardView addProduct , allProducts , Custumers , salled ;
    MaterialAlertDialogBuilder progressDialog;
    AlertDialog dialog;
    BottomSheetDialog bottomSheetDialog;
    Uri productimage ;
    StorageReference Storage ;
    DatabaseReference Root ;
    ImageView  first ,second , third , forth ;
    int number ;
    String CHANNEL_ID = "ali";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        Setting_Ui();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initialisation();
        settingIMages();
        onClicks();
        NotifcationListener();

    }

    public void NotifcationListener(){
        FirebaseDatabase.getInstance().getReference().child("Bought_Recently_Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    pushNotification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Welcom_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pushNotification() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            // here we must create our own channel

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID
                    ,"You are in the Settings" // this message will appear when we GO TO THE SETTING of our noti
                    , NotificationManager.IMPORTANCE_HIGH);  // this is the importance of our notification ( the ability of swiping this noti )
            channel.setDescription("My channel description "); // this is another message that will appear when we go to the settings of our message

            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);

        }

        // now we will do the procedure that any phone have the SDK <= 26 also directly enter to it
        // also who have the SDK >= 26 will enter to it but after creating the precedent channel

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE); // this is the intent that when we click an icon in the notification it goes to this intent

        Uri Rington_Sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // sound of the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID); // means that if there is a notification and he needed to a channel so
        // he will connect it with the channel tht we have create in the top

        builder.setSmallIcon(R.drawable.avatar)
                .setContentTitle("New Command") // notification title
                .setContentText("Vous avez une nouvelle commande !") // the text in the notification (description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Rington_Sound)
                .setDefaults(Notification.DEFAULT_ALL)
                .setColor(getResources().getColor(R.color.mainyellow))
                .setContentIntent(pi) // when we click in the notification , it will use this pending intent ( in this case , it will take us to the MainActivity )
                // .setStyle(new NotificationCompat.BigTextStyle().bigText("This is the style")) // this text is also a description text but it hase more styles and features (by writing this line , the first description will not appear , and this description will appear in his place )
                .addAction(R.drawable.ic_flesh,"Voir",pi); // this is the additional button that exists in the button of the noti it hase an icon , a text , and an action ( in this case , when we click in this replay button , it will take use to the MainActivity using the PI pending intent that we have create in the top )

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(10,builder.build());
    }

    public void settingIMages(){
        Picasso.get().load("https://images.unsplash.com/photo-1475275083424-b4ff81625b60?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80").placeholder(R.drawable.holder2).into(first);
        Picasso.get().load("https://images.unsplash.com/photo-1607227063002-677dc5fdf96f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80").placeholder(R.drawable.holder2).into(second);
        Picasso.get().load("https://cdn.pixabay.com/photo/2016/11/22/19/24/archive-1850170_960_720.jpg").placeholder(R.drawable.holder2).into(third);
        Picasso.get().load("https://cdn.pixabay.com/photo/2016/06/01/02/56/ledger-1428230_960_720.jpg").placeholder(R.drawable.holder2).into(forth);
    }

    public void onClicks(){
        salled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcom_Activity.this ,SalledProducts_Activity.class));
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add product , maybe buttomsheet
                ButtomSheetWork();
            }
        });
        Custumers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcom_Activity.this, MainActivity.class));
            }
        });
        allProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // view all products and he can delete some product while long clicking in the item
                startActivity(new Intent(Welcom_Activity.this,ViewAllItemsActivity.class));
            }
        });
    }

    public void ButtomSheetWork() {

        bottomSheetDialog = new BottomSheetDialog(Welcom_Activity.this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        TextInputLayout nametext = bottomSheetDialog.findViewById(R.id.name_layout);
        TextInputLayout pricetext = bottomSheetDialog.findViewById(R.id.price_layout);
        RelativeLayout add = bottomSheetDialog.findViewById(R.id.add_product_layout);
        MaterialCardView image = bottomSheetDialog.findViewById(R.id.add_image);

        // getting the image from the device
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // its let us open a window that makes us able to select a picture from owr device
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent, "pick an image !"), 10);
                image.setClickable(false);
            }
        });

        nametext.getEditText().setText("");
        pricetext.getEditText().setText("");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nametext.getEditText().getText().toString().trim().equals("")) {
                    nametext.getEditText().setError("Enter product name !");
                } else if (pricetext.getEditText().getText().toString().equals("")){
                    pricetext.getEditText().setError("Enter product price !");
                } else {
                    progressDialog = new MaterialAlertDialogBuilder(Welcom_Activity.this);
                    progressDialog.setTitle("Adding the product...");
                    progressDialog.setMessage("Wait a minute please !");
                    progressDialog.setCancelable(false);
                    progressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
                    progressDialog.setIcon(R.drawable.ic__cloud_upload);
                    progressDialog.setCancelable(false);
                    dialog = progressDialog.show();
                    dialog.show();
                    // function to add the product to database
                    if (productimage != null ){
                        ChargeImageInFireStore(nametext.getEditText().getText().toString().trim(),
                                pricetext.getEditText().getText().toString(),
                                productimage);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Welcom_Activity.this, "Please select an image !", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    public void ChargeImageInFireStore(String name, String price, Uri uri){
        Storage = FirebaseStorage.getInstance().getReference().child("Added products").child(name+price);
        // product image variable has the uri if the image that we chose , and we are goons charge it in the fire store
        Log.d("uri",uri.toString());
        Storage.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    // which means that we have uploaded the image to the firebase
                    // and now we are going to get our image uri as a string
                    Storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // this uri makes us able to charge the image in the imageview every time ( its like url )
                            AddProduct(name,price,uri.toString());
                        }
                    });
                } else {
                    Toast.makeText(Welcom_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // gets the image that we select from our phone and puts it in the imageuri variable
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1 && data != null && data.getData() != null) {
            // this is the uri of the image that w have selected from the Phone Gallery
            productimage = data.getData();
            Toast.makeText(Welcom_Activity.this, "Image added !", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddProduct(String name, String price, String imageuri) {
        ItemsModel model = new ItemsModel(name, price, imageuri, false);
        Root.child("Users").child("Products").child("items").child("Addedproducts").push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Welcom_Activity.this, "Product added !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    bottomSheetDialog.dismiss();
                    // refreshing the oll items recyclerview
                    //adapter3.items_list = Utils.AllItemsList();
                    //adapter3.notifyDataSetChanged();
                } else {
                    Toast.makeText(Welcom_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }

    public void initialisation (){
        Root = FirebaseDatabase.getInstance().getReference();
        salled = findViewById(R.id.salled_products);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        forth = findViewById(R.id.forth);
        addProduct = findViewById(R.id.add_product);
        allProducts = findViewById(R.id.all_products);
        Custumers = findViewById(R.id.view_customers);
        first = findViewById(R.id.first);
    }

    public void Setting_Ui() {

        //Hiding action bar
        //getSupportActionBar().hide();
        // setting the keyboard
        //setUpKeybaord(findViewById(R.id.welcom),Welcom_Activity.this);

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
    // hiding the keyboard when we clicks any where ( better user experience )

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}