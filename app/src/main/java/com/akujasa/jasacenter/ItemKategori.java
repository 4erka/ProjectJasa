package com.akujasa.jasacenter;

/**
 * Created by Antonius on 13/05/2017.
 */

public class ItemKategori {
    private String nama;

    private String id;

    public ItemKategori(String nama, String id) {
        this.nama = nama;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
