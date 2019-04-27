package com.example.design.restoff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class ClothHorizontalrvAdepter extends RecyclerView.Adapter<ClothHorizontalrvAdepter.SubHolder> {
    private Context context;
    private ArrayList<Horizontalmodel> horizontalmodelArrayList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public ClothHorizontalrvAdepter(Context context, ArrayList<Horizontalmodel> horizontalmodelArrayList) {
        this.context=context;
        this.horizontalmodelArrayList=horizontalmodelArrayList;
    }

    @NonNull
    @Override
    public ClothHorizontalrvAdepter.SubHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iteamlistrow_sub,viewGroup,false);
        return new ClothHorizontalrvAdepter.SubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothHorizontalrvAdepter.SubHolder subHolder, int i) {
        Horizontalmodel horizontalmodel =horizontalmodelArrayList.get(i);
        Glide.with(context).load(horizontalmodel.getMyuri()).into(subHolder.circleImageView);
        subHolder.name.setText(horizontalmodel.getName());
        subHolder.des.setText(horizontalmodel.getDescription());
        subHolder.price.setText(horizontalmodel.getPrice());

    }

    @Override
    public int getItemCount() {
        return horizontalmodelArrayList.size();
    }

    public class SubHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView name;
        private TextView des;
        private TextView price;
        public SubHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.iteamlistrowsub_image);
            name=itemView.findViewById(R.id.iteamlistrowsub_shopename);
            price=itemView.findViewById(R.id.iteamlistrowsub_shopeprice);
            des=itemView.findViewById(R.id.iteamlistrowsub_des);
        }
    }
}
