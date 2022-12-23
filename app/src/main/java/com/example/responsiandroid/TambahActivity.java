package com.example.responsiandroid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TambahActivity extends AppCompatActivity {
    private EditText addJudul, addIsiLaporan, addLokasi;
    TextView addTanggal;
    private Button add_record;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        addJudul = findViewById(R.id.addJudul);
        addIsiLaporan = findViewById(R.id.addIsiLaporan);
        addLokasi = findViewById(R.id.addLokasi);
        addTanggal = findViewById(R.id.addTanggal);
        add_record = findViewById(R.id.add_record);

        addTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        add_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getJudul = addJudul.getText().toString();
                String getIsiLaporan = addIsiLaporan.getText().toString();
                String getTanggal = addTanggal.getText().toString();
                String getLokasi = addLokasi.getText().toString();

                if (getJudul.isEmpty()){
                    addJudul.setError("Masukan Judul");
                } else if (getIsiLaporan.isEmpty()){
                    addIsiLaporan.setError("Masukan Isi Laporan");
                } else if(getTanggal.isEmpty()) {
                    addTanggal.setError("Masukan Tanggal");
                } else if (getLokasi.isEmpty()){
                    addLokasi.setError("Masukan Lokasi");
                } else {
                    database.child("Saran").push().setValue(new ModelSaran(getJudul,getIsiLaporan,getTanggal,getLokasi)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builder;

                            Context context = getApplicationContext();
                            Resources res = context.getResources();

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                String CHANNEL_ID = "Arya_chanel";

                                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "YahyaChannel",
                                        NotificationManager.IMPORTANCE_HIGH);
                                channel.setDescription("Arya channel description");
                                manager.createNotificationChannel(channel);

                                builder = new NotificationCompat.Builder(TambahActivity.this, CHANNEL_ID);
                            }
                            else {
                                builder = new NotificationCompat.Builder(context);
                            }

                            PendingIntent action = PendingIntent.getActivity(context, 0, new Intent(context, TambahActivity.class),
                                    PendingIntent.FLAG_CANCEL_CURRENT);

                            builder.setContentIntent(action)
                                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                                    .setTicker("Small text!")
                                    .setAutoCancel(true)
                                    .setContentTitle("Berhasil!")
                                    .setContentText("Berhasil menambah data!");

                            Notification notification = builder.build();

                            int notificationCode = (int) (Math.random() * 1000);
                            manager.notify(notificationCode, notification);
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

    private void showDatePicker() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "date-picker");
    }


    public void processDatePickerResult(int dayOfMonth, int month, int year) {
        String day_string = Integer.toString(dayOfMonth);
        String month_string = Integer.toString(month);
        String year_string = Integer.toString(year);

        String dateMessage = day_string + "/" + month_string + "/" + year_string;

        addTanggal.setText(dateMessage);
    }
}
