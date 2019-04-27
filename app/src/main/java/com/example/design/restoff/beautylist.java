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

public class beautylist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Beautylist_Model> beautylist_modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautylist);
        recyclerView=findViewById(R.id.beauty_list);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        beautylist_modelArrayList=new ArrayList<>();
        databaseReference.child("BeautyParlour").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot :dataSnapshot.getChildren())
                {
                    Beautylist_Model beautylist_model=snapshot.getValue(Beautylist_Model.class);
                    beautylist_modelArrayList.add(beautylist_model);
                }
                Beauty_Adepter beauty_adepter=new Beauty_Adepter(beautylist.this,beautylist_modelArrayList);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(beautylist.this,LinearLayoutManager.VERTICAL,false);
                RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(beautylist.this,DividerItemDecoration.VERTICAL);
                DividerItemDecoration itemDecorator = new DividerItemDecoration(
                        beautylist.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(beautylist.this, R.drawable.divider));
                recyclerView.addItemDecoration(new DividerItemDecoration(beautylist.this,
                        DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(beauty_adepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
