package com.example.design.restoff;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public  class Reward_AddBills extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private View view;
    private static final int PICK_IMG_CODE = 100;
    private CircleImageView circleImageView;
    private EditText shopnmae;
    private EditText bill_amout;
    private EditText reviwofshop;
    private RatingBar ratingBar;
    private Button bill_upload;
    private ImageButton bill_choose;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static final int CAMERA_REQUEST = 1888;
    private ProgressDialog progressDialog_image;
    private Uri selectimge;
    private String myuri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.activity_rewarbills,container,false);
       firebaseAuth=FirebaseAuth.getInstance();
       progressDialog_image=new ProgressDialog(getContext());
        progressDialog_image.setMessage("Bill is Uploding...");
       firebaseDatabase=FirebaseDatabase.getInstance();
       databaseReference=firebaseDatabase.getReference();
       firebaseStorage=FirebaseStorage.getInstance();
       storageReference=firebaseStorage.getReference();
       circleImageView=view.findViewById(R.id.bill_image);
       shopnmae=view.findViewById(R.id.bill_shopname);
       bill_amout=view.findViewById(R.id.billamout);
       reviwofshop=view.findViewById(R.id.aboutshop);
       ratingBar=view.findViewById(R.id.shop_rating);
       ratingBar.setRating(5);
       ratingBar.setNumStars(5);
       String rating= String.valueOf(ratingBar.getRating());
        Toast.makeText(getContext(), ""+rating, Toast.LENGTH_SHORT).show();
       bill_upload=view.findViewById(R.id.uploadbill);
       bill_choose=view.findViewById(R.id.choosebill);

       bill_upload.setOnClickListener(this);
       bill_choose.setOnClickListener(this);

       return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.choosebill:
                PopupMenu popup = new PopupMenu(getContext(),v);
                popup.setOnMenuItemClickListener(this);// to implement on click event on items of menu
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.button_menu, popup.getMenu());
                popup.show();
                break;
            case R.id.uploadbill:
                UploadBill();
                break;
        }

    }

    private void UploadBill() {
        progressDialog_image.show();
        String shop_name=shopnmae.getText().toString();
        String shop_amout=bill_amout.getText().toString();
        String about_shop=reviwofshop.getText().toString();
        float shop_rating= ratingBar.getRating();
        if(TextUtils.isEmpty(shop_name))
        {
            Toast.makeText(getContext(), "Enter name of shop!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(shop_amout))
        {
            Toast.makeText(getContext(), "Enter amount of bill!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(about_shop))
        {
            Toast.makeText(getContext(), "Enter somthing about shop!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(myuri))
        {
            Toast.makeText(getContext(), "Upload Pic of Bill!", Toast.LENGTH_SHORT).show();
            return;
        }
        Bill_Model bill_model=new Bill_Model(shop_name,shop_amout,about_shop,shop_rating,myuri,0);
        databaseReference.child("Bills").child(firebaseAuth.getCurrentUser().getUid())
                .push().setValue(bill_model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                circleImageView.setImageURI(null);
                shopnmae.setText("");
                reviwofshop.setText("");
                ratingBar.setRating(0);
                progressDialog_image.dismiss();
                Toast.makeText(getContext(), "Upload more Bills to get More Rewards", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "data is not uploaded", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.camera:

                Camera();
                break;
            case R.id.gallery:
                Image();
                break;
        }
        return true;
    }

    private void Image() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return;
        }
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select Picture"),PICK_IMG_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==PICK_IMG_CODE && resultCode==RESULT_OK && data != null && data.getData()!=null )
        {
            selectimge=data.getData();
            circleImageView.setImageURI(selectimge);
            uploadImage();
        }
        if(requestCode ==CAMERA_REQUEST && resultCode==Activity.RESULT_OK )
        {
            //uploading the file
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            if(photo!=null)
            {
                selectimge=getImageUri(getContext(),photo);
                circleImageView.setImageURI(selectimge);
                uploadImage();
            }

        }
    }

    private Uri getImageUri(Context context, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }


    private void uploadImage() {

        final  StorageReference reference=storageReference.child("Image/"+ UUID.randomUUID().toString());
        reference.putFile(selectimge).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
                String path=reference.getPath();
//                        Toast.makeText(Userpicandotherdata.this, ""+path, Toast.LENGTH_SHORT).show();
                StorageReference storageRef =
                        FirebaseStorage.getInstance().getReference();
                storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        myuri= uri.toString();
                        Toast.makeText(getContext(), ""+myuri, Toast.LENGTH_SHORT).show();


                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void Camera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        { ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraintent,CAMERA_REQUEST);
    }

}
