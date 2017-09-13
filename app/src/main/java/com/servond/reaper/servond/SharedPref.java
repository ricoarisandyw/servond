package com.servond.reaper.servond;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Reaper on 8/13/2017.
 */

public class SharedPref extends Application {
    public void saveData(String name, String value){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d(name + " masuk :", value);
        editor.commit();
    }

    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }
}
