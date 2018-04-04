package com.tagcor.tagcorproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Navanath on 3/13/2018.
 */

public class SessionManager {
    private SharedPreferences sharedpreferences;
    private  SharedPreferences.Editor editor= null;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences("LoginAppPreferences",
                Context.MODE_PRIVATE);
    }
    public String isUserExists(){
        if(sharedpreferences.getString("sid",null) != null)
            return sharedpreferences.getString("sid",null) ;
        else
            return null;
    }

    public void createUserSession(String sid){
        editor = sharedpreferences.edit();
        editor.putString("sid",sid);
        editor.commit();

    }

}
