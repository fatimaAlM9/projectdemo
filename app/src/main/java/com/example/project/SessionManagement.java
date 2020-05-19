package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManagement {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
//    final private String firstlogin="Firstlogin";
//    Gson gson;
//    boolean saveuserinfo=false;



    public SessionManagement(Context context) {

        this.sharedpreferences = context.getSharedPreferences("loger", Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
//        gson=new Gson();

    }


    public void setid(String id){
        editor.putString("id",id);
        editor.commit();

    }

    public String getid(){
        String language=sharedpreferences.getString("id","0");
        return language;

    }

    public boolean clear()
    {
         return sharedpreferences.edit().clear().commit();

    }



}
