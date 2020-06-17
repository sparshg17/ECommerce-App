package com.example.apnabazaar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.ViewHolder.productViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Paper.init(this);
        TextView text = (TextView) findViewById(R.id.textView9);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
                if (id == R.id.logout) {
                    Intent intent = new Intent(MainActivity.this, loginActivity.class);
                    startActivity(intent);
                }

                if(id == R.id.login){
                    Intent intent = new Intent(MainActivity.this,loginActivity.class);
                    startActivity(intent);
                }

                if(id == R.id.profile){
                    Intent intent = new Intent(MainActivity.this,settings.class);
                    startActivity(intent);
                }
                //This is for maintaining the behavior of the Navigation view
                // NavigationUI.onNavDestinationSelected(menuItem,navController);
                //This is for closing the drawer after acting on it
                //drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // mAppBarConfiguration = new AppBarConfiguration.Builder(
        //       R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
        //     .setDrawerLayout(drawer)
        //   .build();
        //  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(ProductRef,products.class).build();

        FirebaseRecyclerAdapter<products, productViewHolder> adapter = new FirebaseRecyclerAdapter<products, productViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull productViewHolder product_view_holder, int i, @NonNull final products products) {
                    product_view_holder.productName.setText(products.getName());
                    product_view_holder.productPrice.setText("Price=" + products.getPrice() + "$") ;
                    product_view_holder.productDesc.setText(products.getDescription());
                Picasso.get().load(products.getImage()).into(product_view_holder.imageView);
                product_view_holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,productDescription.class);
                        intent.putExtra("Pid",products.getProdID());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                productViewHolder product = new productViewHolder(view);
                return product;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    static class products {
        private String Name,Description,Price,image,ProdID;
        public products() {

        }

        public products(String name, String description, String price,String image,String ProdID) {
            Name = name;
            Description = description;
            Price = price;
            this.image = image;
            this.ProdID = ProdID;
        }

        public String getProdID() {
            return ProdID;
        }

        public void setProdID(String prodID) {
            ProdID = prodID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

}

   // @Override
    /*public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

//    @Override
