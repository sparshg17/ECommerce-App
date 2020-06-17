package com.example.apnabazaar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class settings extends AppCompatActivity {
    private EditText phone,name,mail;
    private Button update;
    private TextView back;

    private Uri uri;
    private String myUrl = "";
    private StorageReference storageReference;
    private String checker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        phone = (EditText) findViewById(R.id.editTextPhone);
        name = (EditText) findViewById(R.id.editTextTextPersonName2);
        mail = (EditText) findViewById(R.id.editTextTextPostalAddress);
        update = (Button)  findViewById(R.id.button3);
        back = (TextView) findViewById(R.id.textView13);


        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(settings.this,MainActivity.class);
                    startActivity(intent);
                }
        });
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.UserPhoneKey);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        if(dataSnapshot.child("Mobile").exists()){
                            String fname = dataSnapshot.child("fName").getValue().toString();
                            String mob = dataSnapshot.child("Mobile").getValue().toString();
                            String email = dataSnapshot.child("Email").getValue().toString();

                            phone.setText(mob);
                            name.setText(fname);
                            mail.setText(email);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                HashMap<String,Object> usermap = new HashMap<>();
                usermap.put("fName",name.getText().toString());
                usermap.put("Mobile",phone.getText().toString());
                usermap.put("Email",mail.getText().toString());

                startActivity(new Intent(settings.this,MainActivity.class));
                Toast.makeText(settings.this,"User Profile updated Successfully!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}