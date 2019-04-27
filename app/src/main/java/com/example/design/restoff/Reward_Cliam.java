package com.example.design.restoff;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public  class Reward_Cliam extends android.support.v4.app.Fragment {
    private View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
   private ArrayList<Bill_Model> bill_modelArrayList;
   private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.user_claim,container,false);
       firebaseAuth=FirebaseAuth.getInstance();
       firebaseDatabase=FirebaseDatabase.getInstance();
       databaseReference=firebaseDatabase.getReference();
       bill_modelArrayList=new ArrayList<>();
       recyclerView=view.findViewById(R.id.claim_bills);
       if(firebaseAuth.getCurrentUser() != null)
       {
           databaseReference.child("Bills").child(firebaseAuth.getCurrentUser().getUid())
                   .addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           for(DataSnapshot snapshot:dataSnapshot.getChildren())
                           {
                               Bill_Model bill_model=snapshot.getValue(Bill_Model.class);
                               bill_modelArrayList.add(bill_model);
                           }
                           Bill_Adepter bill_adepter=new Bill_Adepter(getContext(),bill_modelArrayList);
                           RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                           RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                           DividerItemDecoration itemDecorator = new DividerItemDecoration(
                                   getContext(), DividerItemDecoration.VERTICAL);
                           itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
                           recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                   DividerItemDecoration.VERTICAL));
                           recyclerView.setLayoutManager(layoutManager);
                           recyclerView.setAdapter(bill_adepter);


                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
       }

       return view;
    }
}
