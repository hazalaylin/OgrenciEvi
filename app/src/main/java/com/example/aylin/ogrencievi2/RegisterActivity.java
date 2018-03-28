package com.example.aylin.ogrencievi2;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText edtMailRegister;
    private EditText edtPasswordRegister;
    private EditText edtNameRegister;
    private EditText edtSoyadRegister;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtMailRegister=(EditText)findViewById(R.id.edtMailRegister);
        edtPasswordRegister=(EditText)findViewById(R.id.edtPasswordRegister);
        edtNameRegister=(EditText)findViewById(R.id.edtNameRegister);
        edtSoyadRegister=(EditText)findViewById(R.id.edtSoyadRegister);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = edtMailRegister.getText().toString();
                String password = edtPasswordRegister.getText().toString();
                String ad = edtNameRegister.getText().toString();
                String soyad = edtSoyadRegister.getText().toString();


                if (!TextUtils.isEmpty(ad) || !TextUtils.isEmpty(mail) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(soyad))
                {
                    RegisterUser(ad, mail, password,soyad);
                } else {
                    // Toast.makeText(this,"")
                }
            }
        });
    }

    private void RegisterUser(final String ad, String mail, String password, final String soyad) {

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uId = currentUser.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uId);

                            HashMap<String,String> userMap=new HashMap<>();
                            userMap.put("ad",ad);
                            userMap.put("soyad",soyad);

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent mainIntent=new Intent(RegisterActivity.this,EV.class);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, "Kaydedilemedi.Lütfen bilgiler kontrol ediniz.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {

                            Toast.makeText(RegisterActivity.this, "Cannot Sign in.Please check the from and try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
