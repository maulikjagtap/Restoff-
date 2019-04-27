package com.example.design.restoff;

import android.content.Context;
import android.support.annotation.NonNull;
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

class Bill_Adepter  extends RecyclerView.Adapter<Bill_Adepter.BillHolder> {
    private Context context;
    private ArrayList<Bill_Model> bill_modelArrayList;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private float money;
    private float user_money;

    public Bill_Adepter(Context context, ArrayList<Bill_Model> bill_modelArrayList) {
        this.context=context;
        this.bill_modelArrayList=bill_modelArrayList;
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.billrow,viewGroup,false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BillHolder billHolder, int i) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        final Bill_Model bill_model=bill_modelArrayList.get(i);
        Glide.with(context).load(bill_model.getUrl()).into(billHolder.circleImageView);
        billHolder.name.setText(bill_model.getNameshop());
        billHolder.amount.setText(bill_model.getShopamount());



        int b_status=bill_model.getStatus();
        if(b_status==0)
        {
            billHolder.status.setText(R.string.panding);
        }
        if(b_status==1)
        {
            billHolder.status.setText(R.string.done);
            databaseReference.child("Wallet").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user_money= Float.parseFloat(dataSnapshot.child("money").getValue().toString());
                    Toast.makeText(context, ""+bill_model.getShopamount(), Toast.LENGTH_SHORT).show();
                    float bill_amout= Float.parseFloat(bill_model.getShopamount());
                    float result=((10*bill_amout)/100);
                    Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
                    float total=user_money+result;
                    Toast.makeText(context, ""+total, Toast.LENGTH_SHORT).show();
                    Wallet_model wallet_model =new Wallet_model(total);
                    final Query query= databaseReference.child("Bills").child(firebaseAuth.getCurrentUser().getUid()).orderByChild("status").equalTo(1);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                                String key = snapshot.getKey();
                                Toast.makeText(context, "" + key, Toast.LENGTH_SHORT).show();
                                databaseReference.child("Bills").child(firebaseAuth.getCurrentUser().getUid())
                                        .child(key).child("status").setValue(2);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    databaseReference.child("Wallet").child(firebaseAuth.getCurrentUser().getUid()).setValue(wallet_model);
                    AddMoney_Model addMoney_model=new AddMoney_Model(bill_model.getNameshop(),result);
                    databaseReference.child("AddMoney").child(firebaseAuth.getCurrentUser().getUid()).push().setValue(addMoney_model);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bill_modelArrayList.size();
    }

    public class BillHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView name;
        private TextView amount;
        private TextView rating;
        private TextView status;
        public BillHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.coustomerbill_image);
            name=itemView.findViewById(R.id.coustomershope_name);
            amount=itemView.findViewById(R.id.coustomer_amount);
            rating=itemView.findViewById(R.id.coustomer_rating);
            status=itemView.findViewById(R.id.status_text);

        }
    }
}
