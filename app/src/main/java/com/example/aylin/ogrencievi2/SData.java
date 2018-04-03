package com.example.aylin.ogrencievi2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aylin on 30.03.2018.
 */

public final  class SData {
    private static String NickName;
    private static String Key;

    public static void SetNick(String NewNickName) {
        NickName = NewNickName;
    }

    public static String GetNick() {
        return NickName;
    }

    public static void SetKey(String NewKey) {
        Key = NewKey;
    }

    public static String GetKey() {
        return Key;
    }
}

