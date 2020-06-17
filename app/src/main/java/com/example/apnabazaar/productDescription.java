package com.example.apnabazaar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productDescription extends AppCompatActivity {
    private ImageView pimage;
    private TextView prodname,proddesc,prodprice;
    private EditText quantity;
    private Button order;
    private String productID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        pimage = (ImageView) findViewById(R.id.imageView9);
        prodname = (TextView)findViewById(R.id.textView16);
        proddesc = (TextView) findViewById(R.id.textView17);
        prodprice = (TextView) findViewById(R.id.textView18);
        quantity = (EditText) findViewById(R.id.editTextTextPersonName);
        order = (Button) findViewById(R.id.button10);

        productID = getIntent().getStringExtra("ProdID");

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String savCurrTime,savCurrDate;
                Calendar date = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
                savCurrDate = currentDate.format(date.getTime());
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                savCurrTime = currentTime.format(date.getTime());

                DatabaseReference calForRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
                final HashMap<String,Object> cartMap = new HashMap<>();
                cartMap.put("PID",productID);
                cartMap.put("PName",prodname.getText().toString());
                cartMap.put("Price",prodprice.getText().toString());
                cartMap.put("Date",savCurrDate);
                cartMap.put("Time",savCurrTime);
                cartMap.put("Quantity",quantity.getText().toString());
                
            }
        });
        DatabaseReference prodRef = FirebaseDatabase.getInstance().getReference().child("Products");
        prodRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    products productss = dataSnapshot.getValue(products.class);
                    prodname.setText(productss.getName());
                    proddesc.setText(productss.getDescription());
                    prodprice.setText(productss.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}