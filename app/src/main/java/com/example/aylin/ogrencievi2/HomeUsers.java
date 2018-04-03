package com.example.aylin.ogrencievi2;

/**
 * Created by aylin on 2.04.2018.
 */

public class HomeUsers {

    private String Ad;
    private String Soyad;
    private String Borc;

    public HomeUsers() {
    }

    public HomeUsers(String ad, String soyad, String borc) {
        Ad = ad;
        Soyad = soyad;
        Borc = borc;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getSoyad() {
        return Soyad;
    }

    public void setSoyad(String soyad) {
        Soyad = soyad;
    }

    public String getBorc() {
        return Borc;
    }

    public void setBorc(String borc) {
        Borc = borc;
    }
}
