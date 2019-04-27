package com.example.design.restoff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class Picofmonth_Adepter extends RecyclerView.Adapter<Picofmonth_Adepter.PicHolder> {
    private Tabpicofmonth context;
    private ArrayList<Addpicmonth_Model>  addpicmonth_modelArrayList;
    public Picofmonth_Adepter(Tabpicofmonth context, ArrayList<Addpicmonth_Model> addpicmonth_modelArrayList) {
        this.context=context;
        this.addpicmonth_modelArrayList=addpicmonth_modelArrayList;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.picmonth_row,viewGroup,false);
       return new PicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicHolder picHolder, int i) {
        Addpicmonth_Model addpicmonth_model=addpicmonth_modelArrayList.get(i);
        Glide.with(context).load(addpicmonth_model.getUrl()).into(picHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return addpicmonth_modelArrayList.size();
    }

    public class PicHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;


        public PicHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.pic_image);

        }
    }
}
