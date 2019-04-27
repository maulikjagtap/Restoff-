package com.example.design.restoff;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.smarteist.autoimageslider.IndicatorView.draw.data.Orientation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.support.v7.widget.GridLayoutManager.*;

public  class Tabpicofmonth extends Fragment implements View.OnClickListener {
     private Uri selectedFileIntent;;
    private  View view;
    private RecyclerView recyclerView;
    private CircleImageView circleImageView;
    private static final int CAMERA_REQUEST = 1888;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private ArrayList<Addpicmonth_Model>  addpicmonth_modelArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.picsofthmonth,container,false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        circleImageView=view.findViewById(R.id.picsofmonth_camera);
        recyclerView=view.findViewById(R.id.picmoth);
        addpicmonth_modelArrayList=new ArrayList<>();
        circleImageView.setOnClickListener(this);
        databaseReference.child("PicOftheMonth").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot :dataSnapshot.getChildren())
                {
                    for(DataSnapshot snapshot1 :snapshot.getChildren()) {
                        Addpicmonth_Model addpicmonth_model = snapshot1.getValue(Addpicmonth_Model.class);

                        addpicmonth_modelArrayList.add(addpicmonth_model);
                    }

                }
                Picofmonth_Adepter picofmonth_adepter=new Picofmonth_Adepter(Tabpicofmonth.this,addpicmonth_modelArrayList);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                RecyclerView.LayoutManager  manager=new GridLayoutManager(getContext(),2);
                recyclerView.setLayoutManager(gaggeredGridLayoutManager);// set LayoutManager to RecyclerView
                recyclerView.setAdapter(picofmonth_adepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.picsofmonth_camera:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                { ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    return;
                }
                Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraintent,CAMERA_REQUEST);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==CAMERA_REQUEST && resultCode==Activity.RESULT_OK )
        {
            //uploading the file
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                if(photo!=null)
                {
                    selectedFileIntent=getImageUri(getContext(),photo);
                    Intent intent=new Intent(getContext(),Addpicogmoth.class);
                    intent.putExtra("url",selectedFileIntent.toString());
                    startActivity(intent);
                }

        }
    }

    private Uri getImageUri(Context context, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }


}
