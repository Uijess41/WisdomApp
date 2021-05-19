package com.maestros.wisomnursingclass.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPref {
    static SharedPreferences prefs;
    public static final String NAME="name";
    public static final String USERID="userid";
    public static final String EMAIL="email";
    public static final String MOBILE="mobile";
    public static final String DISTRICT="dist";
    public static final String STATE="State";
    public static final String IMAGE="image";
    public static final String PATH="path";

    Context context;

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(String.valueOf(prefs),Context.MODE_PRIVATE);
    }
    public static void setData(Context context,String key,String value){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static void insertStringSet(Context context, String key, Set<String> value){
        getPrefs(context).edit().putStringSet(key,value).commit();
    }
    public static String getData(Context context,String key){
        return getPrefs(context).getString(key,"");
    }
    public static Set<String> getStringSet(Context context, String key){
        return getPrefs(context).getStringSet(key,null);
    }
    public static void logout(Context context,String key){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.remove(key);
        editor.commit();
    }
}