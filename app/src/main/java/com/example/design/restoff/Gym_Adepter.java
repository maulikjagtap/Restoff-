package com.example.design.restoff;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class Gym_Adepter extends RecyclerView.Adapter<Gym_Adepter.GymHolde> {
    private Context context;
    private ArrayList<Gymlist_Model> gymlist_modelArrayList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String key;
    public Gym_Adepter(Gymlist context, ArrayList<Gymlist_Model> gymlist_modelArrayList) {
        this.context=context;
        this.gymlist_modelArrayList=gymlist_modelArrayList;
    }

    @NonNull
    @Override
    public GymHolde onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listrow,viewGroup,false);
        return new GymHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GymHolde gymHolde, int i) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        Gymlist_Model gymlist_model=gymlist_modelArrayList.get(i);
        Glide.with(context).load(gymlist_model.getUri()).into(gymHolde.circleImageView);
        gymHolde.contacat.setText(gymlist_model.getUser_contact());
        gymHolde.name.setText(gymlist_model.getUser_name());
        gymHolde.desc.setText(gymlist_model.getUser_description());
        gymHolde.address.setText(gymlist_model.getUser_address());
        final Query query= databaseReference.child("Gym").orderByChild("user_name").equalTo(gymlist_model.getUser_name());
        gymHolde.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            key = snapshot.getKey();
                            Intent updatedata = new Intent(context,Gymiteam_list.class);
                            updatedata.putExtra("datakey", key);
                            context.startActivity(updatedata);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return gymlist_modelArrayList.size();
    }

    public class GymHolde extends RecyclerView.ViewHolder{
        private CardView cardView;
        private CircleImageView circleImageView;
        private TextView name;
        private TextView address;
        private TextView desc;
        private TextView contacat;
        public GymHolde(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            circleImageView=itemView.findViewById(R.id.list_image);
            name=itemView.findViewById(R.id.list_name);
            address=itemView.findViewById(R.id.list_address);
            desc=itemView.findViewById(R.id.list_description);
            contacat=itemView.findViewById(R.id.list_contact);
        }
    }
}
