package com.example.design.restoff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class MainStationaryAdepter extends  RecyclerView.Adapter<MainStationaryAdepter.MainHolder>{
    private Context context;
    private ArrayList<VerticalModel> verticalModelArrayList;
    public MainStationaryAdepter(Stationaryiteam_list context, ArrayList<VerticalModel> verticalModelArrayList) {
        this.context = context;
        this.verticalModelArrayList = verticalModelArrayList;
    }

    @NonNull
    @Override
    public MainStationaryAdepter.MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iteamlistrow_main,viewGroup,false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainStationaryAdepter.MainHolder mainHolder, int i) {
        VerticalModel verticalModel=verticalModelArrayList.get(i);
        String title=verticalModel.getTitle();
        ArrayList<Horizontalmodel> horizontalmodelArrayList=verticalModel.getHorizontalmodelArrayList();
        mainHolder.textView.setText(title);
        StationaryHorizontalrvAdepter  stationaryHorizontalrvAdepter=new StationaryHorizontalrvAdepter(context,horizontalmodelArrayList);
        mainHolder.recyclerView.setHasFixedSize(true);
        mainHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        mainHolder.recyclerView.setAdapter(stationaryHorizontalrvAdepter);

    }

    @Override
    public int getItemCount() {
        return verticalModelArrayList.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView textView;
        public MainHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.iteamlistrowmain_rv);
            textView=itemView.findViewById(R.id.iteamlistrowmain_text);
        }
    }
}
