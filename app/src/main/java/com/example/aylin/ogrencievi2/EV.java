package com.example.aylin.ogrencievi2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class EV extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText edtUserEmail;
    private EditText edtUserPassword;
    private Button BtnEvOlustur;
    private Button BtnEveBaglan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ev);

        BtnEvOlustur=(Button)findViewById(R.id.BtnEvOlustur);
        BtnEveBaglan=(Button)findViewById(R.id.BtnEveBaglan);

        BtnEvOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(EV.this,EvOlusturnew.class);
                startActivity(mainIntent);
                finish();
            }
        });

        BtnEveBaglan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(EV.this,EveBaglan.class);
                startActivity(mainIntent);
                finish();
            }
        });

    }
}
