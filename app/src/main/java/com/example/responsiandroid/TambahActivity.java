package com.example.responsiandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahActivity extends AppCompatActivity {
    private EditText addJudul, addIsiLaporan, addLokasi;
    private Button add_record;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        addJudul = findViewById(R.id.addJudul);
        addIsiLaporan = findViewById(R.id.addIsiLaporan);
        addLokasi = findViewById(R.id.addLokasi);
        add_record = findViewById(R.id.add_record);

        add_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getJudul = addJudul.getText().toString();
                String getIsiLaporan = addIsiLaporan.getText().toString();
                String getLokasi = addLokasi.getText().toString();

                if (getJudul.isEmpty()){
                    addJudul.setError("Masukan Judul");
                } else if (getIsiLaporan.isEmpty()){
                    addIsiLaporan.setError("Masukan Isi Laporan");
                } else if (getLokasi.isEmpty()){
                    addLokasi.setError("Masukan Lokasi");
                } else {
                    database.child("Saran").push().setValue(new ModelSaran(getJudul,getIsiLaporan,getLokasi)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(TambahActivity.this,"Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TambahActivity.this,"Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}