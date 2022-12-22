package com.example.responsiandroid;

import java.security.PrivateKey;

public class ModelSaran {

    private String judul;
    private String isiLaporan;
    private String lokasi;
    private String tanggal;
    private String key;

    public ModelSaran(){

    }

    public ModelSaran(String judul, String isiLaporan, String tanggal, String lokasi){
        this.judul = judul;
        this.isiLaporan = isiLaporan;
        this.tanggal = tanggal;
        this.lokasi = lokasi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsiLaporan() {
        return isiLaporan;
    }

    public void setIsiLaporan(String isiLaporan) {
        this.isiLaporan = isiLaporan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setKey(String key) {this.key = key;}

    public String getKey() {return key;}
}
