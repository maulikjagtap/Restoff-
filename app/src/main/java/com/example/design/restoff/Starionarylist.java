package com.example.design.restoff;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Starionarylist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Stationarylist_model> stationarylist_modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starionarylist);
        recyclerView=findViewById(R.id.statioanry_list);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        stationarylist_modelArrayList=new ArrayList<>();
        databaseReference.child("Stationary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Stationarylist_model stationarylist_model=snapshot.getValue(Stationarylist_model.class);
                    stationarylist_modelArrayList.add(stationarylist_model);

                }
                Stationary_Adepter stationary_adepter=new Stationary_Adepter(Starionarylist.this,stationarylist_modelArrayList);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Starionarylist.this,LinearLayoutManager.VERTICAL,false);
                RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(Starionarylist.this,DividerItemDecoration.VERTICAL);
                DividerItemDecoration itemDecorator = new DividerItemDecoration(Starionarylist.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(Starionarylist.this, R.drawable.divider));
                recyclerView.addItemDecoration(new DividerItemDecoration(Starionarylist.this,DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(stationary_adepter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
