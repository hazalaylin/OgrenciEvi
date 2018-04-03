package com.example.aylin.ogrencievi2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Evdeki_Arkadaşlarım extends Fragment {

    private View mainView;
    private RecyclerView homeUserList;

    private FirebaseDatabase Db ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.f_evdeki_arkadaslarim, container, false);

        homeUserList=(RecyclerView)mainView.findViewById(R.id.homeUserList);

        Db = FirebaseDatabase.getInstance();
        DatabaseReference UsersRef = Db.getReference("Evler/"+SData.GetKey()+"/Kullanicilar");
        FirebaseRecyclerAdapter<HomeUsers,UsersViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<HomeUsers, UsersViewHolder>(
                HomeUsers.class,
                R.layout.user_single_layout,
                UsersViewHolder.class,
                UsersRef
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, HomeUsers model, int position) {

                viewHolder.setFullName(model.getAd()+model.getSoyad());
                viewHolder.setBorc(model.getBorc());
            }

        };

        homeUserList.setAdapter(firebaseRecyclerAdapter);

        return mainView;
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public  UsersViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        public void setFullName(String name)
        {
            TextView userName=(TextView)mView.findViewById(R.id.txtSingleUserFullName);
            userName.setText(name);
        }
        public void setBorc(String borc)
        {
            TextView userBorc=(TextView)mView.findViewById(R.id.txtSingleUserBorc);
            userBorc.setText(borc);
        }
    }
    private void getUsers()
    {
        Db = FirebaseDatabase.getInstance();
        DatabaseReference UsersRef = Db.getReference("Evler/"+SData.GetKey()+"/Kullanicilar");
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot gelenler : dataSnapshot.getChildren())
                {
                    Users usr = dataSnapshot.getValue(Users.class);
                    String key =gelenler.getKey();
                    String adi = gelenler.child("Ad").getValue(String.class);
                    String Soyad = gelenler.child("Soyad").getValue(String.class);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        //
    }

}
