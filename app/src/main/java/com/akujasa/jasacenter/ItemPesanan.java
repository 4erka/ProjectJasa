package com.akujasa.jasacenter;

import java.io.Serializable;

/**
 * Created by Antonius on 18/05/2017.
 */

public class ItemPesanan implements Serializable {
    private String id_pesanan;
    private String id_penyedia;
    private String id_status;
    private String nama_penyedia;
    private String tanggal_jasa;
    private String harga_jasa;
    private String jumlah_jasa;
    private String alamat_jasa;
    private String ket_jasa;
    private String nama_jasa;

    public ItemPesanan(String id_pesanan, String id_penyedia, String id_status, String nama_penyedia, String tanggal_jasa, String harga_jasa, String jumlah_jasa, String alamat_jasa, String ket_jasa, String nama_jasa) {
        this.id_pesanan = id_pesanan;
        this.id_penyedia = id_penyedia;
        this.id_status = id_status;
        this.nama_penyedia = nama_penyedia;
        this.tanggal_jasa = tanggal_jasa;
        this.harga_jasa = harga_jasa;
        this.jumlah_jasa = jumlah_jasa;
        this.alamat_jasa = alamat_jasa;
        this.ket_jasa = ket_jasa;
        this.nama_jasa = nama_jasa;
    }

    public String getKet_jasa() {
        return ket_jasa;
    }

    public void setKet_jasa(String ket_jasa) {
        this.ket_jasa = ket_jasa;
    }

    public String getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(String id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public String getId_penyedia() {
        return id_penyedia;
    }

    public void setId_penyedia(String id_penyedia) {
        this.id_penyedia = id_penyedia;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
    }

    public String getNama_penyedia() {
        return nama_penyedia;
    }

    public void setNama_penyedia(String nama_penyedia) {
        this.nama_penyedia = nama_penyedia;
    }

    public String getTanggal_jasa() {
        return tanggal_jasa;
    }

    public void setTanggal_jasa(String tanggal_jasa) {
        this.tanggal_jasa = tanggal_jasa;
    }

    public String getHarga_jasa() {
        return harga_jasa;
    }

    public void setHarga_jasa(String harga_jasa) {
        this.harga_jasa = harga_jasa;
    }

    public String getJumlah_jasa() {
        return jumlah_jasa;
    }

    public void setJumlah_jasa(String jumlah_jasa) {
        this.jumlah_jasa = jumlah_jasa;
    }

    public String getAlamat_jasa() {
        return alamat_jasa;
    }

    public void setAlamat_jasa(String alamat_jasa) {
        this.alamat_jasa = alamat_jasa;
    }

    public String getNama_jasa() {
        return nama_jasa;
    }

    public void setNama_jasa(String nama_jasa) {
        this.nama_jasa = nama_jasa;
    }


}
