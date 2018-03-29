package com.example.aylin.ogrencievi2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EveBaglan extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private FirebaseDatabase Db ;
    private EditText edtEvNickNameBaglan;
    private EditText edtEvSifreBaglan;
    private Button btnBaglan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eve_baglan);

        mAuth=FirebaseAuth.getInstance();
        // findViewById --> ID ile bul getir
        edtEvNickNameBaglan=(EditText)findViewById(R.id.edtEvNickNameBaglan);
        edtEvSifreBaglan=(EditText)findViewById(R.id.edtEvSifreBaglan);
        btnBaglan=(Button)findViewById(R.id.btnBaglan1);

        btnBaglan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String evNickName = edtEvNickNameBaglan.getText().toString();
                final String evSifre = edtEvSifreBaglan.getText().toString();
                Baglan(evNickName);

            }
        });
    }

    private void Baglan(String Nick)
    {
        //nickname sifre kontorlu
        //dogruysa baglan ve yeni uid ile kullanıcıyı kullanıcılar altına ekle
        Db = FirebaseDatabase.getInstance();
        DatabaseReference homeRef = Db.getReference("Evler");
        Query q = homeRef.orderByChild("NickName").equalTo(Nick);
        q.addListenerForSingleValueEvent(valueListener());
    }

    private ValueEventListener valueListener()
    {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot gelenler : dataSnapshot.getChildren())
                {
                    String key = gelenler.getKey();
                    String sifre=gelenler.child("Sifre").getValue(String.class);
                    EditText tx=(EditText)findViewById(R.id.edtEvSifreBaglan);
                    String UserSifre=tx.getText().toString();
                    if(sifre.equals(UserSifre))
                    {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid="";
                        if (user != null) {

                            uid = user.getUid();
                        }
                        //kullanıcı kullanıcılara eklenecek
                        //DatabaseReference ref = Db.getReference("Evler/"+key+"/Kullanicilar/"+uid);
                        String Address="Evler/"+key+"/Kullanicilar/"+uid;
                        getUserDetails(Address);
                        //kullanıcı adı soyadı ve emaili cekilecek

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
    private void getUserDetails(final String Address)
    {
        String uid="";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


            uid = user.getUid();
        }

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
                Evdeki_Arkadaşlarım.Users usr = dataSnapshot.getValue(Evdeki_Arkadaşlarım.Users.class);

                DatabaseReference DbRefForHomeUsers =Db.getReference(Address);
                KullaniciModel KM=new KullaniciModel(usr.getAd(),email,usr.getSoyad());
                DbRefForHomeUsers.setValue(KM);
                //ana sayfaya yönlendir
                Intent mainIntent = new Intent(EveBaglan.this, HOME.class);
                startActivity(mainIntent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    private void EvUser(final String ad, String sifre) {

        mAuth.createUserWithEmailAndPassword(ad, sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uId = currentUser.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uId);

                            HashMap<String,String> userMap=new HashMap<>();
                            userMap.put("ad",ad);

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Intent mainIntent=new Intent(EveBaglan.this,HOME.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(EveBaglan.this, "Kaydedilemedi.Lütfen bilgiler kontrol ediniz.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {

                            Toast.makeText(EveBaglan.this, "Cannot Sign in.Please check the from and try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
