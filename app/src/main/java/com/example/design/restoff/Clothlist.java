package com.example.design.restoff;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Clothlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Clothlist_Model>  clothlist_modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothlist);
        recyclerView=findViewById(R.id.cloth_list);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        clothlist_modelArrayList=new ArrayList<>();
        databaseReference.child("ClothShop").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                       Clothlist_Model clothlist_model=snapshot.getValue(Clothlist_Model.class);
                       clothlist_modelArrayList.add(clothlist_model);
                }
                Cloth_Adepter cloth_adepter=new Cloth_Adepter(Clothlist.this,clothlist_modelArrayList);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Clothlist.this,LinearLayoutManager.VERTICAL,false);
                RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(Clothlist.this,DividerItemDecoration.VERTICAL);
                DividerItemDecoration itemDecorator = new DividerItemDecoration(
                        Clothlist.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(Clothlist.this, R.drawable.divider));
                recyclerView.addItemDecoration(new DividerItemDecoration(Clothlist.this,
                        DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(cloth_adepter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
