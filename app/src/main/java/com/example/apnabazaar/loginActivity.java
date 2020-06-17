package com.example.apnabazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnabazaar.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class loginActivity extends AppCompatActivity {
    private EditText mobile,password;
    private Button login,signup,Logout;
    private CheckBox checkBox;
    private TextView adminlink,userlink;
    private Button admlogin,usrlogin;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private String parentDB = "Users";
    boolean isChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobile = (EditText)findViewById(R.id.mobile);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.button);
        signup = (Button)findViewById(R.id.button5);
        checkBox = (CheckBox)findViewById(R.id.checkBox3);
        adminlink = (TextView)findViewById(R.id.textView5);
        userlink = (TextView)findViewById(R.id.textView6);
        admlogin = (Button)findViewById(R.id.button7);
        usrlogin = (Button)findViewById(R.id.button8);
        Logout = (Button) findViewById(R.id.logout);

        userlink.setVisibility(View.INVISIBLE);
        usrlogin.setVisibility(View.INVISIBLE);
        
        admlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Admin Login");
                checkBox.setVisibility(View.INVISIBLE);
                userlink.setVisibility(View.VISIBLE);
                usrlogin.setVisibility(View.VISIBLE);
                admlogin.setVisibility(View.INVISIBLE);
                adminlink.setVisibility(View.INVISIBLE);
                parentDB = "Admins";
            }
        });

        usrlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login");
                checkBox.setVisibility(View.VISIBLE);
                userlink.setVisibility(View.INVISIBLE);
                usrlogin.setVisibility(View.INVISIBLE);
                admlogin.setVisibility(View.VISIBLE);
                adminlink.setVisibility(View.VISIBLE);
                parentDB = "Users";
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(loginActivity.this,signUpActivity.class);
                startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isChecked", isChecked);
                editor.commit();
            }
        });


        SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
        isChecked = settings1.getBoolean("isChecked", false);
        if (isChecked) {
            Intent i = new Intent(loginActivity.this, MainActivity.class);
            startActivity(i);
            return;
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Mobile = mobile.getText().toString().trim();
                final String passw = password.getText().toString().trim();

                if (TextUtils.isEmpty(Mobile)) {
                    Toast.makeText(loginActivity.this, "Please enter registered Mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passw)) {
                    Toast.makeText(loginActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(checkBox.isChecked())
                    {
                        Paper.book().write(Prevalent.UserPhoneKey, Mobile);
                        Paper.book().write(Prevalent.UserPasswordKey, passw);
                    }
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(parentDB).child((Mobile)).exists()) {
                                    Users usersData = dataSnapshot.child(parentDB).child((Mobile)).getValue(Users.class);

                                if (usersData.getMobile().equals(Mobile)){
                                    if(usersData.getPassword().equals(passw)){
                                        if(parentDB == "Admins"){
                                            Toast.makeText(loginActivity.this,"Admin Logged In Successfully!",Toast.LENGTH_SHORT).show();
                                             //checkBox.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(loginActivity.this,adminPanel.class);
                                            startActivity(intent);
                                        }
                                        if(parentDB == "Users"){
                                            Toast.makeText(loginActivity.this,"Logged In Successfully!",Toast.LENGTH_SHORT).show();
                                           // checkBox.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(loginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                                else{
                                    Toast.makeText(loginActivity.this,"Password is Incorrect!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(loginActivity.this,"Please register first!",Toast.LENGTH_SHORT).show();
                                return;
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
}
