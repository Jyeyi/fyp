package com.example.fyp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context ctx;

    //private static final String is_login = "IsLogin";

    //public static final String KEY_name = "name";
    //public static final String KEY_email = "email";
    //public static final String KEY_ic = "ic";
    //public static final String KEY_phone = "phone";
    //public static final String KEY_pass = "pass";

    public SessionManager(Context ctx){
        this.ctx = ctx;
        usersSession = ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void setLoggedin(boolean loggedin){
        editor.putBoolean("loggedInmode",loggedin);

        //editor.putBoolean(KEY_name,user_name);
        //editor.putString(KEY_ic,user_ic);
        //editor.putString(KEY_phone,user_phone);
        //editor.putString(KEY_pass,user_pass);

        editor.commit();
    }

    public boolean loggedin(){
        return usersSession.getBoolean("loggedInmode",false);
    }

    //public HashMap<String, String> getUserDetailFormSession(){
        //HashMap<String,String> userData = new HashMap<String,String>();

        //userData.put(KEY_name, usersSession.getString(KEY_name, null));
    //userData.put(KEY_ic, usersSession.getString(KEY_ic, null));
    //userData.put(KEY_email, usersSession.getString(KEY_email, null));
    //userData.put(KEY_phone, usersSession.getString(KEY_phone, null));
    //userData.put(KEY_pass, usersSession.getString(KEY_pass, null));

    //return userData;
    //}

    //public boolean checkLogin(){
    //if (usersSession.getBoolean(is_login,false)){
    // return true;
    //}
    // else{
    //    return false;
    //}
    // }

    // public void logout(){
    //  editor.clear();
    // editor.commit();
    //}



}