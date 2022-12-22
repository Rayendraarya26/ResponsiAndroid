package com.example.responsiandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private Button logoutButton;
    private FloatingActionButton addSaran;
    AdapterSaran adapterSaran;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelSaran> listSaran;
    RecyclerView Showlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton = findViewById(R.id.logoutButton);
        addSaran = findViewById(R.id.addSaran);

        addSaran.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),TambahActivity.class));
        });
        Showlist = findViewById(R.id.Showlist);
        RecyclerView.LayoutManager sLayout = new LinearLayoutManager(this);
        Showlist.setLayoutManager(sLayout);
        Showlist.setItemAnimator(new DefaultItemAnimator());

        tampilData();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
        });
    }

    private void tampilData() {
        database.child("Saran").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSaran = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    ModelSaran mdlsrn = item.getValue(ModelSaran.class);
                    mdlsrn.setKey(item.getKey());
                    listSaran.add(mdlsrn);
                }
                adapterSaran = new AdapterSaran(listSaran, MainActivity.this);
                Showlist.setAdapter(adapterSaran);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}