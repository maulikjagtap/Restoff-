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

class Beauty_Adepter  extends RecyclerView.Adapter<Beauty_Adepter.BeautyHolder> {
    private Context context;
    private ArrayList<Beautylist_Model> beautylist_modelArrayList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String key;

    public Beauty_Adepter(beautylist context, ArrayList<Beautylist_Model> beautylist_modelArrayList) {
        this.context=context;
        this.beautylist_modelArrayList=beautylist_modelArrayList;
    }

    @NonNull
    @Override
    public BeautyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listrow,viewGroup,false);
       return new BeautyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeautyHolder beautyHolder, int i) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        Beautylist_Model beautylist_model=beautylist_modelArrayList.get(i);

        Glide.with(context).load(beautylist_model.getUri()).into(beautyHolder.circleImageView);
        beautyHolder.address.setText(beautylist_model.getUser_address());
        beautyHolder.contacat.setText(beautylist_model.getUser_contact());
        beautyHolder.desc.setText(beautylist_model.getUser_description());
        beautyHolder.name.setText(beautylist_model.getUser_name());
        final Query query= databaseReference.child("BeautyParlour").orderByChild("user_name").equalTo(beautylist_model.getUser_name());
        beautyHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            key = snapshot.getKey();
                            Intent updatedata = new Intent(context, Beautyiteam_list.class);
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
        return beautylist_modelArrayList.size();
    }

    public class BeautyHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private CircleImageView circleImageView;
        private TextView name;
        private TextView address;
        private TextView desc;
        private TextView contacat;
        public BeautyHolder(@NonNull View itemView) {
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
