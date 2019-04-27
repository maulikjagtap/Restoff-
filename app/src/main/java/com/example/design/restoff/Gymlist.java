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

public class Gymlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Gymlist_Model> gymlist_modelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymlist);
        recyclerView=findViewById(R.id.gym_list);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        gymlist_modelArrayList=new ArrayList<>();
        databaseReference.child("Gym").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Gymlist_Model gymlist_model=snapshot.getValue(Gymlist_Model.class);
                    gymlist_modelArrayList.add(gymlist_model);
                }
                Gym_Adepter gym_adepter=new Gym_Adepter(Gymlist.this,gymlist_modelArrayList);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Gymlist.this,LinearLayoutManager.VERTICAL,false);
                RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(Gymlist.this,DividerItemDecoration.VERTICAL);
                DividerItemDecoration itemDecorator = new DividerItemDecoration(
                        Gymlist.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(Gymlist.this, R.drawable.divider));
                recyclerView.addItemDecoration(new DividerItemDecoration(Gymlist.this,
                        DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(gym_adepter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
