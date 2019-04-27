package com.example.design.restoff;

import android.graphics.Color;
import android.media.TimedText;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Wallet extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextView user_money;
    private Button getvoucher;
    private RecyclerView recyclerView;
    private ArrayList<AddMoney_Model> addMoney_modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //TRANSPARENT bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        changeStatusBarColor();
        user_money=findViewById(R.id.user_wallet_value);
        getvoucher=findViewById(R.id.user_valucher);
        getvoucher.setOnClickListener(this);
        //recycle view
        recyclerView=findViewById(R.id.addmoney_recycle);
        addMoney_modelArrayList=new ArrayList<>();

        if (firebaseAuth.getCurrentUser() != null) {
            databaseReference.child("Wallet").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String money = dataSnapshot.child("money").getValue().toString();
                    user_money.setText(money);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            databaseReference.child("AddMoney").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        AddMoney_Model addMoney_model=snapshot.getValue(AddMoney_Model.class);
                        addMoney_modelArrayList.add(addMoney_model);
                    }
                    Addmoney_Adepter addmoney_adepter=new Addmoney_Adepter(Wallet.this,addMoney_modelArrayList);
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Wallet.this,LinearLayoutManager.VERTICAL,false);
                    RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(Wallet.this,DividerItemDecoration.VERTICAL);
                    DividerItemDecoration itemDecorator = new DividerItemDecoration(
                            Wallet.this, DividerItemDecoration.VERTICAL);
                    itemDecorator.setDrawable(ContextCompat.getDrawable(Wallet.this, R.drawable.divider));
                    recyclerView.addItemDecoration(new DividerItemDecoration(Wallet.this,
                            DividerItemDecoration.VERTICAL));
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(addmoney_adepter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
