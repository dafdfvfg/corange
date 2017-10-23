package com.example.liang.corange.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.liang.corange.BaseApplication;
import com.example.liang.corange.ui.BaseFragmentActivity;

/**
 * Created by liang on 2017/10/18.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = DBOpenHelper.class.getSimpleName();
    public static DBOpenHelper instance = null;
    public static final String DB_NAME = "corange.db";
    private static final int DB_VERSION_1 = 1;
    private static final int DB_VERSION_2 = 2;
    private static final int DB_VERSION_3 = 3;
    private static final int DB_VERSION_4 = 4;
    private static final int DB_VERSION_5 = 5;
    private static final int DB_VERSION_6 = 6;
    private static final int DB_VERSION_7 = 7;
    private static final int DB_VERSION_8 = 8;
    public static final int DB_VERSION = DB_VERSION_1;

    public static final String TABLE_USER = "user";
    public static final String TABLE_M = "mankind";

    public DBOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static DBOpenHelper getInstance() {
        if (instance == null) {
            synchronized (DBOpenHelper.class) {
                if (instance == null) {
                    instance = new DBOpenHelper(BaseApplication.getInstance(), DB_VERSION);
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        createUserTable(db);
        createFuckTable(db);
        createFuck2Table(db);
        createLiangTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBOpenHelper", "onUpgrade:" + "oldVersion is:" + oldVersion + "   newVersion is:" + newVersion);
        int upgradeVersion = oldVersion;
        if (upgradeVersion == 1) {
            createUserTable(db);
            createMankindTable(db);
            createFuckTable(db);
            createFuck2Table(db);
            createLiangTable(db);
            upgradeVersion = DB_VERSION;
        }

        if (oldVersion < DB_VERSION_4) {
            Log.i(TAG, "oldVersion<DB_VERSION_4");
            createFuckTable(db);
        }
        if (oldVersion < DB_VERSION_5) {
            Log.i(TAG, "oldVersion<DB_VERSION_5");
            createFuck2Table(db);
        }
        if (oldVersion < DB_VERSION_6) {
            Log.i(TAG, "oldVersion<DB_VERSION_6");
        }
        if (oldVersion < DB_VERSION_7) {
            Log.i(TAG, "oldVersion<DB_VERSION_7");
        }
        if(oldVersion < DB_VERSION_8){
            Log.i(TAG, "oldVersion<DB_VERSION_8");
            createLiangTable(db);
        }
    }


    private void createUserTable(SQLiteDatabase db) {
        String str = "create table user(_id integer primary key autoincrement, name varchar,age int,birthday data，profession varchar)";
        db.execSQL(str);
        db.execSQL("insert into user(name,age) values ('大块头',30)");
        db.execSQL("insert into user(name,age) values ('梁大仙',29)");
        db.execSQL("insert into user(name,age) values ('梁小仙',10)");
        db.execSQL("insert into user(name,age) values ('老张',31)");
    }

    private void createMankindTable(SQLiteDatabase db) {
        String str = "create table mankind(_id integer primary key autoincrement, name varchar,age int,birthday data，profession varchar)";
        db.execSQL(str);
        db.execSQL("insert into mankind(name,age) values ('真是神仙呀',999)");
    }

    private void createFuckTable(SQLiteDatabase db) {
        String str = "create table fuck(_id integer primary key autoincrement, name varchar,age int,birthday data，profession varchar)";
        db.execSQL(str);
        db.execSQL("insert into fuck(name,age) values ('fuck cao',999999)");
    }

    private void createFuck2Table(SQLiteDatabase db) {
        String str = "create table fuck2(_id integer primary key autoincrement, name varchar,age int,birthday data，profession varchar)";
        db.execSQL(str);
        db.execSQL("insert into fuck2(name,age) values ('fuck2 ca2o',9999299)");
    }
    private void createLiangTable(SQLiteDatabase db) {
        String str = "create table liang(_id integer primary key autoincrement, name varchar,age int,birthday data，profession varchar)";
        db.execSQL(str);
        db.execSQL("insert into liang(name,age) values ('gaoshou',1000)");
    }
}
