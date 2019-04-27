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
import android.widget.Toast;

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

class Restaurantlist_Adepter extends RecyclerView.Adapter<Restaurantlist_Adepter.RestaurantHolder> {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Restaurantlist_model> restaurantlist_modelArrayList;
    private String key;

    public Restaurantlist_Adepter(Restaurantlist context, ArrayList<Restaurantlist_model> restaurantlist_modelArrayList) {
        this.context=context;
        this.restaurantlist_modelArrayList=restaurantlist_modelArrayList;
    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listrow,viewGroup,false);
       return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder restaurantHolder, int i) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        final int position=i;
        Restaurantlist_model restaurantlist_model=restaurantlist_modelArrayList.get(i);
        Glide.with(context).load(restaurantlist_model.getUri()).into(restaurantHolder.circleImageView);
        restaurantHolder.contacat.setText(restaurantlist_model.getUser_contact());
        restaurantHolder.name.setText(restaurantlist_model.getUser_name());
        restaurantHolder.desc.setText(restaurantlist_model.getUser_description());
        restaurantHolder.address.setText(restaurantlist_model.getUser_address());
        final Query query= databaseReference.child("Restaurant").orderByChild("user_name").equalTo(restaurantlist_model.getUser_name());
        restaurantHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            key = snapshot.getKey();
                            Intent updatedata = new Intent(context, Restaurantiteam_list.class);
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
        return restaurantlist_modelArrayList.size();
    }

    public class RestaurantHolder  extends RecyclerView.ViewHolder{
        private CardView cardView;
        private CircleImageView circleImageView;
        private TextView name;
        private TextView address;
        private TextView desc;
        private TextView contacat;
        public RestaurantHolder(@NonNull View itemView) {
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
