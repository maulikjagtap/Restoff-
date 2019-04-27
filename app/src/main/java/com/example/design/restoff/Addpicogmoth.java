package com.example.design.restoff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Addpicogmoth extends AppCompatActivity implements View.OnClickListener {
    String setimage;
    Uri image_uri;
    private CircleImageView circleImageView;
    private EditText shopnmae;
    private EditText shopdescription;
    private Button addpic;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog_image;
    private String myuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpicogmoth);
        progressDialog_image = new ProgressDialog(this);
        progressDialog_image.setMessage("Photo is Uploding...");
        Toast.makeText(this, ""+myuri, Toast.LENGTH_SHORT).show();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        circleImageView = findViewById(R.id.month_image);
        shopdescription = findViewById(R.id.addmoth_shopdeasription);
        shopnmae = findViewById(R.id.addmoth_shopname);
        addpic = findViewById(R.id.addpic_month);
        circleImageView = findViewById(R.id.month_image);
        setimage = getIntent().getStringExtra("url");
        image_uri = Uri.parse(setimage);

        circleImageView.setImageURI(image_uri);
        uploadImage();
        addpic.setOnClickListener(this);
    }

    private void uploadImage() {

        final StorageReference reference=storageReference.child("Image/"+ UUID.randomUUID().toString());
        reference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Addpicogmoth.this, "yes", Toast.LENGTH_SHORT).show();
                String path=reference.getPath();
//                        Toast.makeText(Userpicandotherdata.this, ""+path, Toast.LENGTH_SHORT).show();
                StorageReference storageRef =
                        FirebaseStorage.getInstance().getReference();
                storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        myuri= uri.toString();
                        Toast.makeText(Addpicogmoth.this, ""+myuri, Toast.LENGTH_SHORT).show();


                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Addpicogmoth.this, "no", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addpic_month:
                Addpicofmoth();
                break;
        }

    }

    private void Addpicofmoth() {
        progressDialog_image.show();
        final String shop_name=shopnmae.getText().toString();
        final String shop_description=shopdescription.getText().toString();
        if (TextUtils.isEmpty(shop_name))
        {
            Toast.makeText(this, "Enter Name of shop!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(myuri))
        {
            Toast.makeText(this, "Choose Image", Toast.LENGTH_SHORT).show();
            return;
        }
        Addpicmonth_Model addpicmonth_model= new Addpicmonth_Model(shop_name,shop_description,myuri);
        databaseReference.child("PicOftheMonth")
                .child(firebaseAuth.getCurrentUser().getUid())
                .push()
                .setValue(addpicmonth_model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog_image.dismiss();
                Intent loginactvity=new Intent(Addpicogmoth.this,Tabpicofmonth.class);
                startActivity(loginactvity);
                finish();
            }
        });


    }
}

