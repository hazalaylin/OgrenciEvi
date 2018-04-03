package com.example.aylin.ogrencievi2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Alisveris_Listesi_Olustur extends Fragment {

    private FirebaseDatabase Db ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText edtUrunAdi;
    private ListView listeUrun;
    private Button btnListeyeEkle;
    private View mainView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    public Alisveris_Listesi_Olustur() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainView=inflater.inflate(R.layout.f_alisveris_listesi_olustur, container, false);

        edtUrunAdi=(EditText)mainView.findViewById(R.id.edtUrunAdi);
        listeUrun=(ListView)mainView.findViewById(R.id.listeUrun);
        Button btnListeyeEkle=(Button)mainView.findViewById(R.id.btnListeyeEkle);
        String[] items={"Apple","Banana","Grape"};

        arrayList=new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,R.id.txtitem,arrayList);
        listeUrun.setAdapter(adapter);

        listeyiGöster();

        btnListeyeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem=edtUrunAdi.getText().toString();
                arrayList.add(newItem);
                adapter.notifyDataSetChanged();
                listeyeEkle();

            }
        });

        // Inflate the layout for this fragment
        return mainView;


    }

    public void listeyeEkle()//nutton click bunu calıstıracak
    {
        String evNickName=SData.GetNick();//alan doldurulacak
        Db = FirebaseDatabase.getInstance();
        DatabaseReference DbRef = Db.getReference("alinacaklar/"+evNickName);
        String key = DbRef.push().getKey();
        DatabaseReference InserStatement = Db.getReference("alinacaklar/"+evNickName+"/"+key);
        HashMap<String, String> UrunMap = new HashMap<>();
        UrunMap.put("UrunAd",edtUrunAdi.getText().toString());
        UrunMap.put("Fiyat","0");
        UrunMap.put("AlanKisi","0");
        InserStatement.setValue(UrunMap);

    }
    public void listeyiGöster()
    {

        String evNickName=SData.GetNick();//alan doldurulacak
        Db = FirebaseDatabase.getInstance();
        DatabaseReference UsersRef = Db.getReference("alinacaklar/"+evNickName);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot gelenler : dataSnapshot.getChildren())
                {
                    String key = gelenler.getKey();
                    String ad=gelenler.child("UrunAd").getValue(String.class);

                    String ab="B";

                    arrayList.add(ad);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

}
