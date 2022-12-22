package com.example.responsiandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
    EditText judul,isi,lokasi, judulEdit, isiLaporanEdit, tanggalEdit, lokasiEdit;
    String Key;
    Button btnSave;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final EditText detJudul = (EditText) findViewById(R.id.editJudul);
        final EditText detIsi = (EditText) findViewById(R.id.editIsiLaporan);
        final EditText detTanggal = (EditText) findViewById(R.id.editTanggal);
        final EditText detLokasi = (EditText) findViewById(R.id.editLokasi);
        Button btnSave = (Button) findViewById(R.id.UpdateRecord);

        Intent intent = getIntent();
        String judulEdit = intent.getStringExtra("editJudul");
        String isiLaporanEdit = intent.getStringExtra("editIsiLaporan");
        String tanggalEdit = intent.getStringExtra("editTanggal");
        String lokasiEdit = intent.getStringExtra("editLokasi");
        String key = intent.getStringExtra("editKey");

        detJudul.setText(judulEdit);
        detIsi.setText(isiLaporanEdit);
        detTanggal.setText(tanggalEdit);
        detLokasi.setText(lokasiEdit);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editJudul = detJudul.getText().toString();
                String editIsiLaporan = detIsi.getText().toString();
                String editTanggal = detTanggal.getText().toString();
                String editLokasi = detLokasi.getText().toString();

                database.child("Saran").child(key).setValue(new ModelSaran(judulEdit, isiLaporanEdit, tanggalEdit, lokasiEdit)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditActivity.this, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditActivity.this, "Data gagal disimpan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}