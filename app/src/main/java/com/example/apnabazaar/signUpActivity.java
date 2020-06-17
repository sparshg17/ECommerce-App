package com.example.apnabazaar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class signUpActivity extends AppCompatActivity {

        private Button signup;
        private EditText email,fname,lname,password,phone;
        private CheckBox checkBox;
        private ProgressBar progressBar;
        private int a=0,progress=0;
        private FirebaseAuth mAuth;
        private FirebaseDatabase database;
        private DatabaseReference myRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            signup = (Button)findViewById(R.id.Signup);
            email = (EditText)findViewById(R.id.email);
            fname = (EditText)findViewById(R.id.fname);
            lname = (EditText)findViewById(R.id.lname);
            phone = (EditText)findViewById(R.id.phone);
            password = (EditText)findViewById(R.id.password);
            checkBox = (CheckBox)findViewById(R.id.checkBox);
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            mAuth = FirebaseAuth.getInstance();

            database = FirebaseDatabase.getInstance();

            myRef = database.getReference();

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    final String fName = fname.getText().toString().trim();
                    final String lName = lname.getText().toString().trim();
                    final String mobile = phone.getText().toString().trim();
                    final String Email = email.getText().toString().trim();
                    final String passw = password.getText().toString().trim();

                    if (TextUtils.isEmpty(Email)) {
                        Toast.makeText(signUpActivity.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(passw)) {
                        Toast.makeText(signUpActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(passw.length() < 6) {
                        Toast.makeText(signUpActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(fName)) {
                        Toast.makeText(signUpActivity.this, "Please enter your first name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(lName)) {
                        Toast.makeText(signUpActivity.this, "Please enter your last name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(mobile)) {
                        Toast.makeText(signUpActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        setProgressValue(progress);
                        progressBar.setVisibility(View.VISIBLE);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if(!dataSnapshot.child("Users").child(mobile).exists()){
                                HashMap<String,Object> userData = new HashMap<>();
                                userData.put("fName",fName);
                                userData.put("lName",lName);
                                userData.put("Email",Email);
                                userData.put("Mobile",mobile);
                                userData.put("Password",passw);

                                myRef.child("Users").child(mobile).updateChildren(userData)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(signUpActivity.this,"You have been Successfully Registered!",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(),loginActivity.class));
                                                }
                                                else{
                                                    Toast.makeText(signUpActivity.this,"Registeration failed! Please try again",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(signUpActivity.this,"User already exists!",Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    }
                }
            });

        }
    private void setProgressValue(final int progress){
            progressBar.setProgress(progress);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    setProgressValue(progress+10);
                }
            });
            thread.start();
    }
}
