package com.example.liang.corange.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by liang on 2017/10/18.
 */

public class DiscoverDBHelp {

    public static DiscoverDBHelp instance = null;
    private SQLiteDatabase database;


    public DiscoverDBHelp() {

    }

    public static DiscoverDBHelp getInstance() {
        if (instance == null) {
            synchronized (DiscoverDBHelp.class) {
                if (instance == null) {
                    instance = new DiscoverDBHelp();
                    instance.setSQLiteDatabase(DBOpenHelper.getInstance().getWritableDatabase());
                }
            }
        }
        return instance;
    }

    public void setSQLiteDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public void insertUser(){

    }
    public void inquiryUser(){

        Cursor cursor = database.query ("user",null,null,null,null,null,null);
        String str = cursor.getString(cursor.getColumnIndex("name"));
        Log.i("shenmgui","----:"+str);




    }

}
