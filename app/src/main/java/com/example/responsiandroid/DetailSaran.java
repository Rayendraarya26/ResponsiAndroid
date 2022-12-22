package com.example.responsiandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailSaran extends AppCompatActivity {
    public Button updatebutton;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_saran);

        final TextView detJudul = (TextView) findViewById(R.id.detailJudul);
        final TextView detisiLaporan = (TextView) findViewById(R.id.detailIsiLaporan);
        final TextView detLokasi = (TextView) findViewById(R.id.detailLokasi);
        updatebutton = findViewById(R.id.updateRecord);

        Intent intent = getIntent();
        String judul = intent.getStringExtra("Judul");
        String isiLaporan = intent.getStringExtra("IsiLaporan");
        String lokasi = intent.getStringExtra("Lokasi");
        String key = intent.getStringExtra("Key");

        detJudul.setText(judul);
        detisiLaporan.setText(isiLaporan);
        detLokasi.setText(lokasi);


        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nInt = new Intent(DetailSaran.this, EditActivity.class);
                nInt.putExtra("editJudul", judul);
                nInt.putExtra("editLokasi", lokasi);
                nInt.putExtra("editIsiLaporan", isiLaporan);
                nInt.putExtra("editKey", key);
                startActivity(nInt);
            }
        });


    }
}