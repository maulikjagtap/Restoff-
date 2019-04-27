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

class Stationary_Adepter extends RecyclerView.Adapter<Stationary_Adepter.StationaryHolder> {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Stationarylist_model> stationarylist_modelArrayList;
    private String key;

    public Stationary_Adepter(Starionarylist context, ArrayList<Stationarylist_model> stationarylist_modelArrayList) {
        this.context=context;
        this.stationarylist_modelArrayList=stationarylist_modelArrayList;
    }

    @NonNull
    @Override
    public StationaryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listrow,viewGroup,false);
       return new StationaryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationaryHolder stationaryHolder, int i) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        Stationarylist_model stationarylist_model=stationarylist_modelArrayList.get(i);
        Glide.with(context).load(stationarylist_model.getUri()).into(stationaryHolder.circleImageView);
        stationaryHolder.address.setText(stationarylist_model.getUser_address());
        stationaryHolder.contacat.setText(stationarylist_model.getUser_contact());
        stationaryHolder.desc.setText(stationarylist_model.getUser_description());
        stationaryHolder.name.setText(stationarylist_model.getUser_name());
        final Query query= databaseReference.child("Stationary").orderByChild("user_name").equalTo(stationarylist_model.getUser_name());
        stationaryHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            key = snapshot.getKey();
                            Intent updatedata = new Intent(context, Stationaryiteam_list.class);
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
        return stationarylist_modelArrayList.size();
    }

    public class StationaryHolder extends  RecyclerView.ViewHolder{
        private CardView cardView;
        private CircleImageView circleImageView;
        private TextView name;
        private TextView address;
        private TextView desc;
        private TextView contacat;
        public StationaryHolder(@NonNull View itemView) {
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
