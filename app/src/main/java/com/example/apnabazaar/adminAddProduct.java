package com.example.apnabazaar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class adminAddProduct extends AppCompatActivity {
    private String CategoryClick,DescriptionClick,prodpriceCLick,prodnameClick,saveCurrDate,saveCurrTime;
    private ImageView image;
    private EditText pname,pdesc,price;
    private Button add;
    private static final int gallerypick = 1;
    private Uri imageUri;
    private String prodRandomKey,downloadImageUrl;
    private StorageReference productImagesRef;
    private DatabaseReference productRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        image = (ImageView) findViewById(R.id.imageView5);
        pname = (EditText) findViewById(R.id.editText);
        pdesc = (EditText) findViewById(R.id.editText2);
        price = (EditText) findViewById(R.id.editText3);
        add = (Button) findViewById(R.id.button2);

        CategoryClick = getIntent().getExtras().get("Category").toString();
        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,gallerypick);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == gallerypick && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            image.setImageURI(imageUri);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prodnameClick = pname.getText().toString();
                    prodpriceCLick = price.getText().toString();
                    DescriptionClick = pdesc.getText().toString();

                    if(imageUri == null){
                        Toast.makeText(adminAddProduct.this,"Product Image is Mandatory!",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(prodnameClick)){
                        Toast.makeText(adminAddProduct.this,"Please add product name!",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(DescriptionClick)){
                        Toast.makeText(adminAddProduct.this,"Please add product Description!",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(prodpriceCLick)){
                        Toast.makeText(adminAddProduct.this,"Please add Product price!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Calendar calendar = Calendar.getInstance();

                        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
                        saveCurrDate = currentDate.format(calendar.getTime());

                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                        saveCurrTime = currentTime.format(calendar.getTime());

                        prodRandomKey = saveCurrDate+saveCurrTime;

                        final StorageReference filePath = productImagesRef.child(imageUri.getLastPathSegment() + prodRandomKey + ".jpg");
                        final UploadTask uploadTask = filePath.putFile(imageUri);

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String message = e.toString();
                                Toast.makeText(adminAddProduct.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(adminAddProduct.this," Product Image Uploaded Successfully!",Toast.LENGTH_SHORT).show();
                                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if(!task.isSuccessful()){
                                            throw task.getException();
                                        }

                                        downloadImageUrl = filePath.getDownloadUrl().toString();
                                        return filePath.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if(task.isSuccessful()){
                                            downloadImageUrl = task.getResult().toString();
                                            Toast.makeText(adminAddProduct.this,"Getting Product image URL Successfully!",Toast.LENGTH_SHORT).show();
                                            saveProductInfo();
                                        }
                                    }
                                });
                            }
                        });

                    }

                }
            });
        }
    }

    private void saveProductInfo() {
        HashMap<String,Object> prodMap = new HashMap<>();
        prodMap.put("ProdID",prodRandomKey);
        prodMap.put("Date",saveCurrDate);
        prodMap.put("Time",saveCurrTime);
        prodMap.put("image",downloadImageUrl);
        prodMap.put("Description",DescriptionClick);
        prodMap.put("Name",prodnameClick);
        prodMap.put("Price",prodpriceCLick);
        prodMap.put("Category",CategoryClick);

        productRef.child(prodRandomKey).updateChildren(prodMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(adminAddProduct.this,"Product is Added Successfully!",Toast.LENGTH_SHORT).show();
                }
                else{
                    String message = task.getException().toString();
                    Toast.makeText(adminAddProduct.this,"Error:"+message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}