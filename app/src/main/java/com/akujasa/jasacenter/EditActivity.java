package com.akujasa.jasacenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private EditText etNama;
    private EditText etEmail;
    private EditText etAlamat;
    private EditText etNohp;
    private Button btEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        SharedPreferences sp = this.getSharedPreferences("pencari_info",MODE_PRIVATE);
        final String id_pencari = sp.getString("pencari_id","a");
        etNama = (EditText)findViewById(R.id.editnama);
        etEmail = (EditText)findViewById(R.id.editemail);
        etAlamat = (EditText)findViewById(R.id.editalamat);
        etNohp = (EditText)findViewById(R.id.editnohp);
        btEdit = (Button)findViewById(R.id.btEdit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditProcess(EditActivity.this).execute(etNama.getText().toString(),etEmail.getText().toString(),etAlamat.getText().toString(),etNohp.getText().toString(),id_pencari);
            }
        });
       new InfoEditViewProcess(this,etNama,etEmail,etAlamat,etNohp).execute(id_pencari);
    }


}
