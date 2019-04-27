package com.example.design.restoff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class Restaurantiteam_list extends AppCompatActivity implements View.OnClickListener {
    private String key;
    private ImageView shopimage;
    private CircleImageView circleImageView;
    private TextView name;
    private TextView address;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String shop_name;
    private String shop_address;
    private String shop_url;
    private String shop_phoneno;
    private ArrayList<VerticalModel> verticalModelArrayList;
    private ArrayList<TitleModle> titleModleArrayList;
    private String iteamkey;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantiteam_list);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        //TRANSPARENT bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        changeStatusBarColor();
        key=getIntent().getStringExtra("datakey");
        Toast.makeText(this, "this is"+key, Toast.LENGTH_SHORT).show();
        shopimage=findViewById(R.id.shopimage);
        name=findViewById(R.id.shopname);
        address=findViewById(R.id.shopaddress);
        circleImageView=(CircleImageView)findViewById(R.id.shopcall);
        circleImageView.setOnClickListener(this);
// set Profile
        databaseReference.child("Restaurant").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 shop_name=dataSnapshot.child("user_name").getValue().toString();
                 shop_address=dataSnapshot.child("user_address").getValue().toString();
                 shop_url=dataSnapshot.child("uri").getValue().toString();
                shop_phoneno=dataSnapshot.child("user_contact").getValue().toString();
                name.setText(shop_name);
                address.setText(shop_address);
                Glide.with(Restaurantiteam_list.this).load(shop_url).into(shopimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

// Recycle View
        recyclerView=findViewById(R.id.restaurantiteamlist);
        shimmerFrameLayout=findViewById(R.id.facebookshimmer);
        verticalModelArrayList = new ArrayList<>();
        titleModleArrayList = new ArrayList<>();

// data from fire base
        databaseReference.child("RestaurantIteamMenu")
                .child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestaurantMenuModel restaurantMenuModel = snapshot.getValue(RestaurantMenuModel.class);
                    iteamkey=snapshot.getKey();
                    final VerticalModel verticalModel=new VerticalModel();
//                    Toast.makeText(getActivity(), ""+iteamkey, Toast.LENGTH_SHORT).show();
                    verticalModel.setTitle(restaurantMenuModel.getIteamcategory());
                    final ArrayList<Horizontalmodel> horizontalmodelArrayList = new ArrayList<>();
                    databaseReference.child("restaurantIteams").child(key)
                            .child(iteamkey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                                Log.e("mydata", snapshot1.getValue().toString());
                                Horizontalmodel horizontalmodel = snapshot1.getValue(Horizontalmodel.class);
                                horizontalmodelArrayList.add(horizontalmodel);
                            }
                            verticalModel.setHorizontalmodelArrayList(horizontalmodelArrayList);
                            verticalModelArrayList.add(verticalModel);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Restaurantiteam_list.this,LinearLayoutManager.VERTICAL,false);
                            RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(Restaurantiteam_list.this,DividerItemDecoration.VERTICAL);
                            DividerItemDecoration itemDecorator = new DividerItemDecoration(Restaurantiteam_list.this, DividerItemDecoration.VERTICAL);
                            itemDecorator.setDrawable(ContextCompat.getDrawable(Restaurantiteam_list.this, R.drawable.divider));
                            recyclerView.addItemDecoration(new DividerItemDecoration(Restaurantiteam_list.this,
                                    DividerItemDecoration.VERTICAL));
                            recyclerView.setLayoutManager(layoutManager);
                            final MainRestaurantAdepter mainrvAdepter = new MainRestaurantAdepter(Restaurantiteam_list.this, verticalModelArrayList);
                            recyclerView.setAdapter(mainrvAdepter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.shopcall:
                Intent call=new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"+shop_phoneno));
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
                startActivity(call);

                break;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

}
