package com.example.design.restoff;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public  class Reward_Chat extends android.support.v4.app.Fragment implements View.OnClickListener {
    private View view;
    private EditText name;
    private EditText email;
    private EditText query;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private Button submitquery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.rewardin_chat_screen,container,false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Sending Query..");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        name=view.findViewById(R.id.customer_name);
        email=view.findViewById(R.id.customer_email);
        query=view.findViewById(R.id.customer_quey);
        submitquery=view.findViewById(R.id.submit_query);
        submitquery.setOnClickListener(this);
        databaseReference.child("User").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userename=dataSnapshot.child("user_name").getValue().toString();
                String usereemail=dataSnapshot.child("user_email").getValue().toString();
                name.setText(userename);
                email.setText(usereemail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit_query:
                progressDialog.show();
                String customername=name.getText().toString();
                String customeremail=email.getText().toString();
                String customequery=query.getText().toString();
                if(TextUtils.isEmpty(customername))
                {
                    Toast.makeText(getContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(customeremail))
                {
                    Toast.makeText(getContext(), "Enter email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(customequery))
                {
                    Toast.makeText(getContext(), "Write you query!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Query_model query_model=new Query_model(customername,customeremail,customequery);
                databaseReference.child("Coustomer_Query").child(firebaseAuth.getCurrentUser().getUid())
                        .push().setValue(query_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "We Solve your Query Soon.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       progressDialog.dismiss();
                        Toast.makeText(getContext(), "Somthing went Wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

    }
}
