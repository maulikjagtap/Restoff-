package com.example.design.restoff;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

class OfferAdepter  extends RecyclerView.Adapter<OfferAdepter.OfferHolder> {
    private Reward_Home context;
    private ArrayList integerArrayList;

    public OfferAdepter(Reward_Home context, ArrayList integerArrayList) {
        this.context=context;
        this.integerArrayList=integerArrayList;
    }

    @NonNull
    @Override
    public OfferHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_row,viewGroup,false);
        return new OfferHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferHolder offerHolder, int i) {
      offerHolder.imageView.setImageResource((Integer) integerArrayList.get(i));

    }

    @Override
    public int getItemCount() {
        return integerArrayList.size();
    }

    public class OfferHolder extends  RecyclerView.ViewHolder {
        private ImageView imageView;
        public OfferHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.offer_image);
        }
    }
}
