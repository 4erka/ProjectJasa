package com.akujasa.jasacenter;

/**
 * Created by Antonius on 14/05/2017.
 */

public class ItemJenis {
    private String nama;

    private String id;

    public ItemJenis(String nama, String id) {
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
