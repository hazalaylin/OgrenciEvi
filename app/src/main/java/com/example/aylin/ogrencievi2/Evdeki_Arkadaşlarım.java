package com.example.aylin.ogrencievi2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Evdeki_Arkadaşlarım extends Fragment {

    private Object ad; private Object soyad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.F_Evdeki_Arkadaslarim, container, false);


        Users(String ad,soyad);

    }

    /**
     * A simple {@link Fragment} subclass.
     */

    public static class Users {
      public String ad;
        public String soyad;

        public  Users()
        {

        }

        public Users(String ad, String soyad) {
            this.ad = ad;
            this.soyad = soyad;
        }

        public void setAd(String ad) {
            this.ad = ad;
        }

        public String getAd() {
            return ad;
        }

        public void setSoyad(String soyad) {
            this.soyad = soyad;
        }

        public String getSoyad() {
            return soyad;
        }
    }
}
