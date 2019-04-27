package com.example.design.restoff;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public  class TabReward extends Fragment {
    private View view;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.rewardin_screen,container,false);
      bottomNavigationView=view.findViewById(R.id.reward_bottomnavigation);


      bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

//        recyclerView=view.findViewById(R.id.offerrecycle);
//        OfferAdepter offerAdepter=new OfferAdepter(TabReward.this,image);
//        recyclerView.setAdapter(offerAdepter);
//        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(view.getContext());
//        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
        getFragmentManager().beginTransaction().replace(R.id.main_framlayout,new Reward_Home()).commit();
        return  view;
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment=new Fragment();
            switch (menuItem.getItemId())
            {
               case  R.id.reward_home:
                fragment=new Reward_Home();
                break;
                case  R.id.reward_chat:
                fragment=new Reward_Chat();
                break;
                case R.id.reward_addpic:
                fragment=new Reward_AddBills();
                break;
                case R.id.reward_cliam:
                fragment=new Reward_Cliam();
                break;
                case R.id.reward_person:
                fragment=new Reward_Person();
                break;
            }
            getFragmentManager().beginTransaction().replace(R.id.main_framlayout,fragment).commit();
            return true;
        }
    };


}
