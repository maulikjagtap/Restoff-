package com.example.design.restoff;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Restaurantlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Restaurantlist_model> restaurantlist_modelArrayList;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        shimmerFrameLayout=findViewById(R.id.facebookshimmer);
        recyclerView=findViewById(R.id.restaurant_list);
        restaurantlist_modelArrayList=new ArrayList<>();
        databaseReference.child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot :dataSnapshot.getChildren())
                {
                    Restaurantlist_model restaurantlist_model=snapshot.getValue(Restaurantlist_model.class);
//                    Toast.makeText(Restaurantlist.this, ""+restaurantlist_model.getUser_cont(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Restaurantlist.this, ""+restaurantlist_model.getUser_des(), Toast.LENGTH_SHORT).show();
                    restaurantlist_modelArrayList.add(restaurantlist_model);
                }
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                Restaurantlist_Adepter restaurantlist_adepter=new Restaurantlist_Adepter(Restaurantlist.this,restaurantlist_modelArrayList);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Restaurantlist.this,LinearLayoutManager.VERTICAL,false);
                RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(Restaurantlist.this,DividerItemDecoration.VERTICAL);
                DividerItemDecoration itemDecorator = new DividerItemDecoration(Restaurantlist.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(Restaurantlist.this, R.drawable.divider));
                recyclerView.addItemDecoration(new DividerItemDecoration(Restaurantlist.this,
                        DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(restaurantlist_adepter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



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
