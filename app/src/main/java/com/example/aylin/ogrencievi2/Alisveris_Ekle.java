package com.example.aylin.ogrencievi2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Alisveris_Ekle extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseUser user;

    private FirebaseDatabase Db;
    private View mainView;

    private String urunAd;

    public Alisveris_Ekle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         user = FirebaseAuth.getInstance().getCurrentUser();

        mainView = inflater.inflate(R.layout.f_alisveris_ekle, container, false);

        final Button btnAlisverisEkle = (Button) mainView.findViewById(R.id.btnAlisverisEkle);
        final EditText urunAdi = (EditText) mainView.findViewById(R.id.edtAlinanUrun);
        final EditText urunFiyati = (EditText) mainView.findViewById(R.id.edtUrunFiyat);

        btnAlisverisEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                urunAd = urunAdi.getText().toString();
                String fiyat = urunFiyati.getText().toString();
                Double urunFiyat = Double.parseDouble(fiyat);

                UrunAl(urunAd, urunFiyat);
            }
        });


        return mainView;
    }

    public void UrunAl(String UrunAdi, Double Fiyat) {
        //adress=nickname/ ara

        //insert fiyat & Alan Kisi
        ara(UrunAdi, Fiyat);
    }

    public void ara(String UrunAdi, Double Fiyat) {
        Db = FirebaseDatabase.getInstance();

        DatabaseReference homeRef = Db.getReference("alinacaklar/" + SData.GetNick());
        Query q = homeRef.orderByChild("UrunAd").equalTo(UrunAdi);
        q.addListenerForSingleValueEvent(valueListener(Fiyat));
    }

    private ValueEventListener valueListener(final Double Fiyat) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot gelenler : dataSnapshot.getChildren()) {
                    String key = gelenler.getKey();
                    String fiyat = gelenler.child("Fiyat").getValue().toString();

                    Double dFiyat=Double.parseDouble(fiyat);

                    if (dFiyat >= 0) {
                        //kullanıcı kullanıcılara eklenecek
                        //DatabaseReference ref = Db.getReference("Evler/"+key+"/Kullanicilar/"+uid);
                        String Address = "alinacaklar/" + SData.GetNick() + "/" + key;
                        AlincaklarGuncelle(Address, Fiyat);
                        //kullanıcı adı soyadı ve emaili cekilecek
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Alınacaklar güncellenemedi.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void AlincaklarGuncelle(String Address, Double Fiyat) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = "";
        if (user != null) {

            uid = user.getUid();

        }
        Db = FirebaseDatabase.getInstance();
        DatabaseReference InserStatement = Db.getReference(Address);
        HashMap<String, String> UrunMap = new HashMap<>();

        UrunMap.put("Fiyat", Fiyat.toString());
        UrunMap.put("AlanKisi", uid);
        UrunMap.put("UrunAd",urunAd);
        InserStatement.setValue(UrunMap);

        BorcAta(Fiyat);
    }

    public void BorcAta(Double Fiyat) {
        //kisi sayısı
        //iyat/kisisayisi=borc
        Db = FirebaseDatabase.getInstance();

        DatabaseReference homeRef = Db.getReference("Evler/" + SData.GetKey() + "/Kullanicilar");
        homeRef.addValueEventListener(BorcAtaVal(Fiyat));

    }

    private ValueEventListener BorcAtaVal(final Double Fiyat) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                String key="";
                for (DataSnapshot gelenler : dataSnapshot.getChildren()) {
                    i++;
                    key = gelenler.getKey();
                    //kisi sayısı belli (i) fiyat/ks borca yaz
                }
                borcyaz(key, Fiyat, i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void borcyaz(String key, Double Fiyat, int Kisi)
    {
        Double borc = Fiyat / Kisi;
        Db = FirebaseDatabase.getInstance();
        DatabaseReference InserStatement = Db.getReference("Evler/" + SData.GetKey() + "/Kullanicilar/" + key);
        HashMap<String, String> UrunMap = new HashMap<>();
        UrunMap.put("Borc", borc.toString());
        if (key.equals(user.getUid()))
        {
            Double alacak=borc*(Kisi-1);
            UrunMap.put("Alacak",alacak.toString());


        }
        InserStatement.setValue(UrunMap);
    }

}
