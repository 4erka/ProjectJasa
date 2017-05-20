package com.akujasa.jasacenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = this.getSharedPreferences("pencari_info",MODE_PRIVATE);
        String id_pencari = sp.getString("pencari_id","a");
        TextView tvNama = (TextView)findViewById(R.id.profilnama);
        TextView tvAlamat = (TextView)findViewById(R.id.profilalamat);
        TextView tvEmail = (TextView)findViewById(R.id.profilemail);
        TextView tvNohp = (TextView)findViewById(R.id.profilnohp);
        new InfoProcess(this,tvNama,tvEmail,tvAlamat,tvNohp).execute(id_pencari);
        Button tvEdit = (Button)findViewById(R.id.profiledit);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentku = new Intent(ProfilActivity.this, EditActivity.class);

                ProfilActivity.this.startActivity(intentku);
            }
        });
    }



}
