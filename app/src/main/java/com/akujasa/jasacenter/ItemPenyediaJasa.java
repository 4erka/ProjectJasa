package com.akujasa.jasacenter;

/**
 * Created by Antonius on 14/05/2017.
 */

public class ItemPenyediaJasa {
    private String id;



    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    private String nama;
    private String email;
    private String alamat;
    private String nohp;
    private String kategori;
    private int rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public ItemPenyediaJasa(String id, String nama, String email, String alamat, String nohp, String kategori, int rating) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.nohp = nohp;
        this.kategori = kategori;
        this.rating = rating;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }
}
