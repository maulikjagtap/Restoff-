package com.example.design.restoff;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public  class Reward_Person extends Fragment implements View.OnClickListener {
    private View view;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CircleImageView  circleImageView;
    private TextView user_name;
    private TextView user_email;
    private CardView card_wallet;
    private TextView user_money;
    private Button edit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user,container,false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        user_name=view.findViewById(R.id.user_name);
        user_email=view.findViewById(R.id.user_email);
        circleImageView=view.findViewById(R.id.user_image);
        edit=view.findViewById(R.id.user_edit);
        card_wallet=view.findViewById(R.id.wallet_card);
        user_money=view.findViewById(R.id.wallet_value);
        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {

           
            databaseReference.child("Wallet").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String money=dataSnapshot.child("money").getValue().toString();
                    user_money.setText(money);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            databaseReference.child("User").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("user_name").getValue().toString();
                            String email = dataSnapshot.child("user_email").getValue().toString();
                            String url = dataSnapshot.child("userimage_url").getValue().toString();
                            Glide.with(getContext()).load(url).into(circleImageView);
                            user_email.setText(email);
                            user_name.setText(name);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }
            );
        }
        edit.setOnClickListener(this);
        card_wallet.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.user_edit:
                Intent intent=new Intent(getContext(),EditProfile.class);
                startActivity(intent);
                break;
            case R.id.wallet_card:
                Intent iwallet=new Intent(getContext(),Wallet.class);
                startActivity(iwallet);
                break;
        }

    }
}
