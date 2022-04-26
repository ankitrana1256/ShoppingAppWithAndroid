package com.example.shoppingapp;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button logoutbtn;
    private GridView g;
    private ProgressBar progress;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        logoutbtn = findViewById(R.id.logout);
        g = findViewById(R.id.productview);
        progress = findViewById(R.id.progressBar);
        ArrayList<ProductModel> ProductModelArrayList = new ArrayList<ProductModel>();
//        ProductModelArrayList.add(new ProductModel("Window","https://i.stack.imgur.com/j7tmu.png"));
//        ProductModelArrayList.add(new ProductModel("window","https://i.stack.imgur.com/j7tmu.png"));
//        ProductModelArrayList.add(new ProductModel("window","https://i.stack.imgur.com/j7tmu.png"));

        ProductAdapter adapter = new ProductAdapter(this, ProductModelArrayList);
        g.setAdapter(adapter);
        final Boolean[] empty = {true};
        DatabaseReference refernce = FirebaseDatabase.getInstance().getReference().child("Products");
        refernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductModelArrayList.clear();
                if (empty[0] ==true){
                    progress.setVisibility(View.VISIBLE);
                }
                else{
                    progress.setVisibility(View.GONE);
                }
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        String value = dataSnapshot.getValue().toString();
                        String key = dataSnapshot.getKey();
                        ProductModelArrayList.add(new ProductModel(key,value));
                        progress.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        empty[0] = false;
                    }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
}