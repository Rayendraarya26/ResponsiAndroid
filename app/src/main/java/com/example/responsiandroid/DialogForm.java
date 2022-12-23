package com.example.responsiandroid;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {
    String judul, isiLaporan, tanggal, lokasi, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String judul, String isiLaporan, String tanggal, String lokasi, String key, String pilih) {
        this.judul = judul;
        this.isiLaporan = isiLaporan;
        this.tanggal = tanggal;
        this.lokasi = lokasi;
        this.key = key;
        this.pilih = pilih;
    }
    TextView tJudul, tIsiLaporan, tTanggal,tLokasi;
    Button savebtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tambah, container, false );
        tJudul = view.findViewById(R.id.addJudul);
        tIsiLaporan = view.findViewById(R.id.addIsiLaporan);
        tTanggal = view.findViewById(R.id.addTanggal);
        tLokasi = view.findViewById(R.id.addLokasi);
        savebtn = view.findViewById(R.id.add_record);

        tTanggal.setText(tanggal);

        tTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        tJudul.setText(judul);
        tIsiLaporan.setText(isiLaporan);
        tLokasi.setText(lokasi);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = tJudul.getText().toString();
                String isiLaporan = tIsiLaporan.getText().toString();
                String lokasi = tLokasi.getText().toString();
                String tanggal = tTanggal.getText().toString();

                if (pilih.equals("Ubah")){
                    database.child("Saran").child(key).setValue(new ModelSaran(judul, isiLaporan, tanggal, lokasi)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Data berhasil dirubah", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Data gagal dirubah", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    private void showDatePicker() {
        DialogFragment dateFragment = new EditDatePickerFragment();
        dateFragment.show(getChildFragmentManager(), "date-picker");
    }


    public void processDatePickerResult(int dayOfMonth, int month, int year) {
        String day_string = Integer.toString(dayOfMonth);
        String month_string = Integer.toString(month);
        String year_string = Integer.toString(year);
        String dateMessage = day_string + "/" + month_string + "/" + year_string;

        tTanggal.setText(dateMessage);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!=null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
