package com.example.apnabazaar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class adminPanel extends AppCompatActivity {
    private ImageView tshirt,purse,glasses,shoes;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        tshirt = (ImageView)findViewById(R.id.imageView2);
        purse = (ImageView)findViewById(R.id.imageView);
        glasses = (ImageView)findViewById(R.id.imageView4);
        shoes = (ImageView)findViewById(R.id.imageView3);
        logout = (Button)findViewById(R.id.button4);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPanel.this,loginActivity.class);
                startActivity(intent);
            }
        });

        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPanel.this,adminAddProduct.class);
                intent.putExtra("Category","TShirts");
                startActivity(intent);
            }
        });

        purse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPanel.this,adminAddProduct.class);
                intent.putExtra("Category","Purse");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPanel.this,adminAddProduct.class);
                intent.putExtra("Category","Sunglasses");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPanel.this,adminAddProduct.class);
                intent.putExtra("Category","Shoes");
                startActivity(intent);
            }
        });
    }
}
