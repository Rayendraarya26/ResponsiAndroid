package com.example.responsiandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterSaran extends RecyclerView.Adapter<AdapterSaran.MyViewHolder> {
    private List<ModelSaran> sList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    CardView cardView;

    public AdapterSaran(List<ModelSaran>sList, Activity activity){
        this.sList = sList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelSaran data = sList.get(position);
        holder.tvJudul.setText(data.getJudul());
        holder.tvIsiLaporan.setText(data.getIsiLaporan());
        holder.tvLokasi.setText(data.getLokasi());

        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialog = new DialogForm(
                        data.getJudul(),
                        data.getIsiLaporan(),
                        data.getLokasi(),
                        data.getKey(),
                        "Ubah"
                );
                dialog.show(manager, "form");
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.child("Saran").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setMessage("Apakah anda ingin menghapus data" + " " + data.getJudul());
                builder.show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvIsiLaporan, tvLokasi;
        ImageView deletebtn, updatebtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudulSaran);
            tvIsiLaporan = itemView.findViewById(R.id.tvIsiLaporan);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            deletebtn = itemView.findViewById(R.id.hapus);
            updatebtn = itemView.findViewById(R.id.update);


        }
    }


}
