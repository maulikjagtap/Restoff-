package com.example.design.restoff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public  class Reward_Home extends Fragment implements View.OnClickListener {
    private  View view;
    private LinearLayout restaurant;
    private LinearLayout stationary;
    private LinearLayout beauty;
    private LinearLayout cloth;
    private LinearLayout gym;
    private RecyclerView recyclerView;
        private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int[] data={R.drawable.food,R.drawable.stationary,R.drawable.beautyproduct,R.drawable.clothshop,R.drawable.gym};
    ArrayList image = new ArrayList<>(Arrays.asList(R.drawable.food, R.drawable.stationary, R.drawable.beautyproduct, R.drawable.clothshop, R.drawable.gym));
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.rewardin_home_screen,container,false);
//        recycle view
        recyclerView=view.findViewById(R.id.offerrecycle);
        OfferAdepter offerAdepter=new OfferAdepter(Reward_Home.this,image);
        recyclerView.setAdapter(offerAdepter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
//        image layout
        restaurant=view.findViewById(R.id.restaurant);
        stationary=view.findViewById(R.id.stationary);
        beauty=view.findViewById(R.id.beautypalour);
        cloth=view.findViewById(R.id.clothshop);
        gym=view.findViewById(R.id.gym);
        restaurant.setOnClickListener(this);
        stationary.setOnClickListener(this);
        beauty.setOnClickListener(this);
        cloth.setOnClickListener(this);
        gym.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.restaurant:
                Intent intent=new Intent(getContext(),Restaurantlist.class);
                startActivity(intent);
                break;

            case R.id.stationary:
                Intent intent1=new Intent(getContext(),Starionarylist.class);
                startActivity(intent1);
                break;

            case R.id.beautypalour:
                Intent intent2=new Intent(getContext(),beautylist.class);
                startActivity(intent2);
                break;

            case R.id.clothshop:
                Intent intent3=new Intent(getContext(),Clothlist.class);
                startActivity(intent3);
                break;

            case R.id.gym:
                Intent intent4=new Intent(getContext(),Gymlist.class);
                startActivity(intent4);
                break;

        }

    }
}
