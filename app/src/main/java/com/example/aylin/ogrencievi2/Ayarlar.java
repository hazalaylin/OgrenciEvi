package com.example.aylin.ogrencievi2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aylin on 3.04.2018.
 */

    /**
     * A simple {@link Fragment} subclass.
     */
    public  class Ayarlar extends Fragment {

        private FirebaseDatabase Db;
        private View mainView;

        public Ayarlar() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            mainView=inflater.inflate(R.layout.f_ayarlar, container, false);

             Button btnParolaKaydet = (Button) mainView.findViewById(R.id.btn_Ayarlar_Kaydet);
             final EditText edtKullaniciAd = (EditText) mainView.findViewById(R.id.edt_kulanici_ad);
             final EditText edtKullaniciSoyad = (EditText) mainView.findViewById(R.id.edt_kullanici_soyad);

            Db = FirebaseDatabase.getInstance();
            DatabaseReference UsersRef = Db.getReference("Evler/"+SData.GetKey()+"/Kullanicilar");
            UsersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.



                    Users usr = dataSnapshot.getValue(Users.class);
                    edtKullaniciAd.setText(usr.getAd());
                    edtKullaniciSoyad.setText(usr.getSoyad());






                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });

            return mainView;


    }

}

