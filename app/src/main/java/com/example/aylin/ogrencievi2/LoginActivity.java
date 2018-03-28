package com.example.aylin.ogrencievi2;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private EditText edtUserEmail;
    private EditText edtUserPassword;
    private Button btnLogin;
    private Button btnStartRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        // findViewById --> ID ile bul getir
        edtUserEmail=(EditText)findViewById(R.id.edtUserEmail);
        edtUserPassword=(EditText)findViewById(R.id.edtUserPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnStartRegister=(Button)findViewById(R.id.btnStartRegister);
        // btnLogin --> Giriş butonu , btnStartRegister --> Kayıt ol butonu


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUserEmail.getText().toString();
                String password = edtUserPassword.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    LoginUser(email, password);
                }else
                {
                    Toast.makeText(LoginActivity.this, "Tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStartRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

    }

    private void LoginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Intent mainIntent=new Intent(LoginActivity.this,EV.class);
                     startActivity(mainIntent);
                    finish();
                } else
                {
                     Toast.makeText(LoginActivity.this,"Giriş yapılamadı.Kullanıcı bilgilerinizi kontrol ediniz",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
