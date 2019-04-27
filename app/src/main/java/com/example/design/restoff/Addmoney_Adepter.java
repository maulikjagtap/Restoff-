package com.example.design.restoff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class Addmoney_Adepter extends RecyclerView.Adapter<Addmoney_Adepter.AddmoneyHolder> {
    private Context context;
    private ArrayList<AddMoney_Model> addMoneyModelArrayList;

    public Addmoney_Adepter(Wallet context, ArrayList<AddMoney_Model> addMoney_modelArrayList) {
        this.context=context;
        this.addMoneyModelArrayList=addMoney_modelArrayList;
    }

    @NonNull
    @Override
    public AddmoneyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addmoneyrow,viewGroup,false);
        return new AddmoneyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddmoneyHolder addmoneyHolder, int i) {
        AddMoney_Model addMoney_model=addMoneyModelArrayList.get(i);
        addmoneyHolder.nameshop.setText(addMoney_model.getShopname());
        float skds=addMoney_model.getMoney();
        addmoneyHolder.xyz.setText(""+skds);



    }

    @Override
    public int getItemCount() {
        return addMoneyModelArrayList.size();
    }

    public class AddmoneyHolder extends RecyclerView.ViewHolder {
        private TextView nameshop;
        private TextView xyz;
        public AddmoneyHolder(@NonNull View itemView) {
            super(itemView);
            nameshop=itemView.findViewById(R.id.addmoney_shopname);
            xyz=itemView.findViewById(R.id.moneyrecive);
        }
    }
}
