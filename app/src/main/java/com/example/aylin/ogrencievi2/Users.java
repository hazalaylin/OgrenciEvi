package com.example.aylin.ogrencievi2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aylin on 27.03.2018.
 */

public class Users {
    private String ad;
    private String soyad;

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
