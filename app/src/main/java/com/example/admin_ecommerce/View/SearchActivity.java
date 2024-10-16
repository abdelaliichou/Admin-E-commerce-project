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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_ecommerce.Controller.Search_adapter;
import com.example.admin_ecommerce.Model.CategoryModel;
import com.example.admin_ecommerce.Model.ItemsModel;
import com.example.admin_ecommerce.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static ProgressBar progressBar ;
    TextView categoriesText , itemsText ;
    AutoCompleteTextView searchField;
    RecyclerView recyclerView;
    DatabaseReference root;
    public static Search_adapter adapter;
    RelativeLayout items_search , categories_search ;
    ShimmerFrameLayout shimmerFrameLayout ;
    ArrayList<ItemsModel> list3 ;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Setting_Action_Bar_Status_Bar();
        initialisation();
        initialClickButton();
        SearchType();
    }

    public void initialClickButton(){
        AutoCompleteByName();
        items_search.setBackground(getResources().getDrawable(R.drawable.click_back));
        itemsText.setTextColor(getResources().getColor(R.color.colorthird));
        categories_search.setBackground(getResources().getDrawable(R.drawable.view_more_item_back));
        categoriesText.setTextColor(getResources().getColor(R.color.white));
        if (adapter != null) {
            adapter.items_list.clear();
            searchField.setText("");
            adapter.notifyDataSetChanged();
        }
    }

    public void SearchType(){
        items_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteByName();
                items_search.setBackground(getResources().getDrawable(R.drawable.click_back));
                itemsText.setTextColor(getResources().getColor(R.color.colorthird));
                categories_search.setBackground(getResources().getDrawable(R.drawable.view_more_item_back));
                categoriesText.setTextColor(getResources().getColor(R.color.white));
                if (adapter != null) {
                    adapter.items_list.clear();
                    searchField.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        categories_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 AutoCompleteByCategory();
                items_search.setBackground(getResources().getDrawable(R.drawable.view_more_item_back));
                itemsText.setTextColor(getResources().getColor(R.color.white));
                categories_search.setBackground(getResources().getDrawable(R.drawable.click_back));
                categoriesText.setTextColor(getResources().getColor(R.color.colorthird));
                if (adapter != null) {
                    adapter.items_list.clear();
                    searchField.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void initialisation() {
        shimmerFrameLayout = findViewById(R.id.shimmer);
        progressBar = findViewById(R.id.search_progress);
        categoriesText = findViewById(R.id.categories);
        itemsText = findViewById(R.id.items);
        items_search = findViewById(R.id.search_items);
        categories_search = findViewById(R.id.search_categories);
        searchField = findViewById(R.id.complete_text);
        recyclerView = findViewById(R.id.search_recycler);
        root = FirebaseDatabase.getInstance().getReference();
    }

    public void AutoCompleteByName() {
        root.child("Users").child("Products").child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> name = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot category : snapshot.getChildren()) {
                        for (DataSnapshot product : category.getChildren()) {
                            String item = product.getValue(ItemsModel.class).getProductName();
                            name.add(item);
                        }
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, name);
                    searchField.setAdapter(arrayAdapter);
                    searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //progressBar.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.startShimmer();
                            String product = searchField.getText().toString();
                            searchProductByName(product);
                        }
                    });
                } else {
                    Toast.makeText(SearchActivity.this, "No products found !", Toast.LENGTH_SHORT).show();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
            }
        });
    }

    public void AutoCompleteByCategory() {
        root.child("Users").child("Products").child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> name = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot category : snapshot.getChildren()) {
                        String item = category.getValue(CategoryModel.class).getCategory();
                        name.add(item);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, name);
                    searchField.setAdapter(arrayAdapter);
                    searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //progressBar.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.startShimmer();
                            String category = searchField.getText().toString();
                            searchProductByCategory(category);
                        }
                    });
                } else {
                    Toast.makeText(SearchActivity.this, "No products found !", Toast.LENGTH_SHORT).show();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
           }
        });
    }

    public void searchProductByName(String productName) {
        root.child("Users").child("Products").child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    ArrayList<ItemsModel> liste = new ArrayList<>();
                    for (DataSnapshot category : snapshot.getChildren()) {
                        for (DataSnapshot item : category.getChildren()) {
                            if (item.getValue(ItemsModel.class).getProductName().equals(productName)) {
                                liste.add(item.getValue(ItemsModel.class));
                            }
                        }
                    }
                    setRecycler(liste);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
            }
        });
    }

    public void searchProductByCategory(String productName) {
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        setRecycler(getPopularItemsListFromDataBase(productName));
    }

    public void setRecycler(ArrayList<ItemsModel> list) {
        adapter = new Search_adapter(list, SearchActivity.this);
        SearchActivity.progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        //getSupportActionBar().hide();
        // setting the keyboard
        setUpKeybaord(findViewById(R.id.serach_page), SearchActivity.this);

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

    public  ArrayList<ItemsModel> getPopularItemsListFromDataBase(String Category) {
        DatabaseReference Rot = FirebaseDatabase.getInstance().getReference();
        if (list3 == null) {
            list3 = new ArrayList<>();
        }
        list3.clear();
        Rot.child("Users").child("Products").child("items").child(Category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot category : snapshot.getChildren()) {
                    if (SearchActivity.progressBar != null){
                        SearchActivity.progressBar.setVisibility(View.GONE);
                    }
                    String Productname = category.getValue(ItemsModel.class).getProductName();
                    String ProductPris = category.getValue(ItemsModel.class).getPrice();
                    String ImageUrl = category.getValue(ItemsModel.class).getImageUrl();
                    ItemsModel model = new ItemsModel(Productname, ProductPris, ImageUrl);
                    list3.add(model);
                    if (SearchActivity.adapter != null){
                        SearchActivity.adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                SearchActivity.progressBar.setVisibility(View.GONE);
            }
        });
        return list3;
    }

}