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

class Cloth_Adepter extends RecyclerView.Adapter<Cloth_Adepter.ClothHolder> {

    private Context context;
    private ArrayList<Clothlist_Model>  clothlist_modelArrayList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String key;
    public Cloth_Adepter(Clothlist context, ArrayList<Clothlist_Model> clothlist_modelArrayList) {
        this.context=context;
        this.clothlist_modelArrayList=clothlist_modelArrayList;
    }

    @NonNull
    @Override
    public ClothHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listrow,viewGroup,false);
        return new ClothHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothHolder clothHolder, int i) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        Clothlist_Model clothlist_model=clothlist_modelArrayList.get(i);
        Glide.with(context).load(clothlist_model.getUri()).into(clothHolder.circleImageView);
        clothHolder.address.setText(clothlist_model.getUser_address());
        clothHolder.contacat.setText(clothlist_model.getUser_contact());
        clothHolder.desc.setText(clothlist_model.getUser_description());
        clothHolder.name.setText(clothlist_model.getUser_name());
        final Query query= databaseReference.child("ClothShop").orderByChild("user_name").equalTo(clothlist_model.getUser_name());
        clothHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            key = snapshot.getKey();
                            Intent updatedata = new Intent(context,Clothiteam_list.class);
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
        return clothlist_modelArrayList.size();
    }

    public class ClothHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private CircleImageView circleImageView;
        private TextView name;
        private TextView address;
        private TextView desc;
        private TextView contacat;
        public ClothHolder(@NonNull View itemView) {
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
