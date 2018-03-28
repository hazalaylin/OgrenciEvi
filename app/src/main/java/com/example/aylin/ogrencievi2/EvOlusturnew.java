package com.example.aylin.ogrencievi2;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class EvOlusturnew extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase Db ;
    private EditText edtEvNickNameOlustur;
    private EditText edtEvAdiOlustur;
    private EditText edtSifreOlustur;
    private Button btnBaglanOlustur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ev_olusturnew);

        mAuth = FirebaseAuth.getInstance();

        // findViewById --> ID ile bul getir
        edtEvNickNameOlustur = (EditText) findViewById(R.id.edtEvNickNameOlustur);
        edtEvAdiOlustur = (EditText) findViewById(R.id.edtEvAdiOlustur);
        edtSifreOlustur = (EditText) findViewById(R.id.edtSifreOlustur);
        btnBaglanOlustur = (Button) findViewById(R.id.btnBaglanOlustur);

        btnBaglanOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String evNickName = edtEvNickNameOlustur.getText().toString();
                final String evAd = edtEvAdiOlustur.getText().toString();
                final String evSifre = edtSifreOlustur.getText().toString();
                CreateHomeusers(EvOlustur(evNickName,evAd,evSifre));
                /*mDatabase.orderByChild("NickName").equalTo(evNickName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Map<String, String> mapAddFriend = (Map<String, String>) postSnapshot.getValue();

                            DatabaseReference friendRef = mDatabase.child(mAuth.getCurrentUser().getUid()).child("friends");
                            friendRef.push().setValue(mapAddFriend.get("NickName"));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
            }
        });
    }
    private void CreateHomeusers(final String Address)
    {
        String uid="";
        String email="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url

            email = user.getEmail();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid = user.getUid();
        }
        //ad soyad için select yapılıyor

// Read from the database
        DatabaseReference UsersRef = Db.getReference("Users/"+uid);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email="";
                if (user != null) {
                    // Name, email address, and profile photo Url
                     email = user.getEmail();

                }
                Users usr = dataSnapshot.getValue(Users.class);

                DatabaseReference DbRefForHomeUsers =Db.getReference(Address);
                KullaniciModel KM=new KullaniciModel(usr.getAd(),email,usr.getSoyad());
                Intent mainIntent = new Intent(EvOlusturnew.this, HOME.class);
                startActivity(mainIntent);
                
                finish();
               // DbRefForHomeUsers.setValue(KM);
              /*  mDatabase.setValue(KM).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Intent mainIntent = new Intent(EvOlusturnew.this, HOME.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Toast.makeText(EvOlusturnew.this, "Kaydedilemedi.Lütfen bilgiler kontrol ediniz.", Toast.LENGTH_LONG).show();
                        }

                    }
                });*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        //

    }
    private String EvOlustur(String evNickName, String evAd, String evSifre) {

        Db = FirebaseDatabase.getInstance();
        //key almak ,ç,n adres göster,ldi
        DatabaseReference DbRef = Db.getReference("Evler");
        String key = DbRef.push().getKey();
        //key Alındı verilewri yazacagımız keyi adrese yazdık.
        DatabaseReference DbRefForInsert = Db.getReference("Evler/"+key);
        //modele parametereler bağlanıp dbye yazılıyor
        EvModel Ev  =new EvModel(evAd,evSifre,evNickName);
        DbRefForInsert.setValue(Ev);
        DatabaseReference DbRefForHomeUsersKey =Db.getReference("Evler/"+key+"/Kullanicilar");
        String UserKey = DbRefForHomeUsersKey.push().getKey();
        String uid="";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url




            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid = user.getUid();
        }
        return "Evler/"+key+"/Kullanicilar/"+uid;
        //key olusturuldu evler altında kullanıcılara kullanıcı eklendı.
        //kullanıcı bilgileri alınıyor


        /*DbRef.setValue(key+"/EvAdi="+evAd);
        HashMap<String, String> evMap = new HashMap<>();
        evMap.put("NickName", evNickName);
        evMap.put("Ad", evAd);
        evMap.put("Sifre", evSifre);

        mDatabase.setValue(evMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Intent mainIntent = new Intent(EvOlusturnew.this, HOME.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Toast.makeText(EvOlusturnew.this, "Kaydedilemedi.Lütfen bilgiler kontrol ediniz.", Toast.LENGTH_LONG).show();
                }

            }
        });*/
    }
}