package com.akujasa.jasacenter;

import java.io.Serializable;

/**
 * Created by Antonius on 14/05/2017.
 */

public class ItemKatalog implements Serializable {
    private String id;
    private String nama;

    public ItemKatalog(String id, String nama, String deskripsi, String harga) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    private String deskripsi;
    private String harga;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
