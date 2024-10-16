package com.example.admin_ecommerce.View;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.admin_ecommerce.Model.CardItemModel;
import com.example.admin_ecommerce.Model.Delivery_Order_Model;
import com.example.admin_ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delivery_Activity extends AppCompatActivity {

    MaterialCardView cardone, cardsecond, cardthird, cardfour, cardfive, doneButton;
    ImageView imgone, imgsecond, imgthird, imgfour, imgfive;
    LinearLayout linearone, linearsecond, linearthird, linearfour;
    TextView textone, textsecond, textthird, textfour, textfive, doneText;
    MaterialAlertDialogBuilder progressDialog;
    AlertDialog dialog;
    ProgressBar progressBar;
    BottomSheetDialog bottomSheetDialog;
    int buttom = 0 ;

    int state = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Setting_Action_Bar_Status_Bar();
        initialisation();
        initialCardsDeliveryState();
        OnClicks();

    }

    public void initialCardsDeliveryState() {
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("Started").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String DeliverState = snapshot.getValue(String.class);
                    switch (DeliverState) {
                        case "first":
                            state = 2;
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.VISIBLE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.GONE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textthird.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfour.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            progressBar.setVisibility(View.GONE);
                            doneText.setText("Next step");
                            break;
                        case "second":
                            state = 3;
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.VISIBLE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.GONE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfour.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            progressBar.setVisibility(View.GONE);
                            doneText.setText("Next step");
                            break;
                        case "third":
                            state = 4;
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.VISIBLE);
                            linearfour.setVisibility(View.GONE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfour.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            progressBar.setVisibility(View.GONE);
                            doneText.setText("Next step");
                            break;
                        case "forth":
                            state = 5;
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.VISIBLE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfour.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            progressBar.setVisibility(View.GONE);
                            doneText.setText("Next step");
                            break;
                        case "fifth":
                            Log.d("opppppppppppen fifth","msg");
                            state = 6;
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.GONE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfour.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfive.setTextColor(getResources().getColor(R.color.deliveryText));
                            progressBar.setVisibility(View.GONE);
                            doneText.setText("Save order");
                            try {
                                BottomsheetDialog();
                                Log.d("Bottom No problem","Nooo probleeeem");
                            }
                            catch (WindowManager.BadTokenException e) {
                                // fuck you
                                Log.d("Bottom problem","here is the probleeeeem");
                            }
                            break;

                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Delivery_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void TransferOrderFromOnProcessToCompleted() {
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // we got the completed order , know we will add it to the completed orders
                    Delivery_Order_Model model = snapshot.getValue(Delivery_Order_Model.class);
                    FirebaseDatabase.getInstance().getReference().child("Delivery").child("completed").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // we added the order to the completed orders , and know we will add the products of this order
                                Log.d("compleeeeeeeeeted", "transfer completed !");
                                AddCompletedOrderProducts();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(Delivery_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(Delivery_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddCompletedOrderProducts() {
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).child("orderElements").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot product : snapshot.getChildren()) {

                        CardItemModel item = product.getValue(CardItemModel.class);
                        FirebaseDatabase.getInstance().getReference().child("Delivery").child("completed").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).child("orderElements").push().setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // this product has been added to the completed order
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(Delivery_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    // finish charging the order element  in the completed child
                    Toast.makeText(Delivery_Activity.this, "Order saved successfully !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(new Intent(Delivery_Activity.this,Welcom_Activity.class));
                    finishAffinity();
                    // know we will delete the order from the on process after it has been added al to the completed
                    // Delete_OnProcess_Started_Orders();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Delivery_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void Delete_OnProcess_Started_Orders() {
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                // all the order has been deleted from the OnProcess child ,
                // we will delete it also from theStarted child
                FirebaseDatabase.getInstance().getReference().child("Delivery").child("Started").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        // here we completed every thing !!!!
                        Toast.makeText(Delivery_Activity.this, "Order saved successfully !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(Delivery_Activity.this,Welcom_Activity.class));
                        finishAffinity();
                    }
                });
            }
        });
    }

    public void BottomsheetDialog() {
        if (buttom == 0) {
            bottomSheetDialog = new BottomSheetDialog(Delivery_Activity.this, R.style.BottomSheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.delivery_done_buttom_sheet);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.show();
            RelativeLayout CheckButton = bottomSheetDialog.findViewById(R.id.check_procces);

            CheckButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                }
            });
            // this variable is for the bottom sheet for not show more then once !!
            buttom ++ ;
        } else {
            // nothing
        }
    }

    public void OnClicks() {
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new MaterialAlertDialogBuilder(Delivery_Activity.this);
                progressDialog.setTitle("Confirmation");
                progressDialog.setCancelable(true);
                progressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
                progressDialog.setIcon(R.drawable.true_deliver);

                switch (state) {
                    // case state = 1 is never gonna be true because in the previous function , it will see that "first" is true so the state will start with 2 not 1
                    case 2:
                        progressDialog.setMessage("Are you sur you want to confirm the order ?");
                        progressDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        ChangeStateDelivery("second");
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog = progressDialog.show();
                        dialog.show();
                        break;
                    case 3:
                        progressDialog.setMessage("Are you sur you prepared the order ?");
                        progressDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        ChangeStateDelivery("third");
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog = progressDialog.show();
                        dialog.show();
                        break;
                    case 4:
                        progressDialog.setMessage("Are you sur that the order is in the way ?");
                        progressDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        ChangeStateDelivery("forth");
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog = progressDialog.show();
                        dialog.show();
                        break;
                    case 5:
                        progressDialog.setMessage("Are you sur that the order has been delivered successfully ?");
                        progressDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        ChangeStateDelivery("fifth");
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog = progressDialog.show();
                        dialog.show();
                        break;
                    case 6:
                        // order finished
                        progressDialog.setMessage("Your order is being saved, please wait a minute !");
                        dialog = progressDialog.show();
                        dialog.show();
                        TransferOrderFromOnProcessToCompleted();
                        break;
                }
            }
        });
    }

    public void initialisation() {
        doneText = findViewById(R.id.text_button);
        progressBar = findViewById(R.id.progress);
        textfive = findViewById(R.id.textfive);
        textfour = findViewById(R.id.textfour);
        textthird = findViewById(R.id.textthird);
        textsecond = findViewById(R.id.textsecond);
        textone = findViewById(R.id.textone);
        cardone = findViewById(R.id.one);
        cardsecond = findViewById(R.id.second);
        cardthird = findViewById(R.id.third);
        cardfour = findViewById(R.id.four);
        cardfive = findViewById(R.id.five);
        imgone = findViewById(R.id.imgone);
        imgsecond = findViewById(R.id.imgsecond);
        imgthird = findViewById(R.id.imgthird);
        imgfour = findViewById(R.id.imgfour);
        imgfive = findViewById(R.id.imgfive);
        linearone = findViewById(R.id.linearone);
        linearsecond = findViewById(R.id.linearsecond);
        linearthird = findViewById(R.id.linearthird);
        linearfour = findViewById(R.id.linearfour);
        doneButton = findViewById(R.id.done);
    }

    public void ChangeStateDelivery(String status) {
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("Started").child(getIntent().getStringExtra("uri")).child(getIntent().getStringExtra("id")).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Delivery_Activity.this, status + " step done !", Toast.LENGTH_SHORT).show();
                    // for the refresh of the cards and the colors
                    initialCardsDeliveryState();
                } else {
                    Toast.makeText(Delivery_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        //getSupportActionBar().hide();
        // setting the keyboard
        // Utils.setUpKeybaord(findViewById(R.id.succes), Delivery_Activity.this);

        this.getWindow().setStatusBarColor(Color.WHITE);

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        // to change the color of the icons in the navigation bar to dark
        this.getWindow().setNavigationBarColor(Color.WHITE); //setting bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Delivery_Activity.this,Welcom_Activity.class));
        finishAffinity();
    }
}