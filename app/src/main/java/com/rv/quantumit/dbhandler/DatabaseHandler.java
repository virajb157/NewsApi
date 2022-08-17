package com.rv.quantumit.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHandler extends SQLiteAssetHelper {
    public static final String db_name = "demo.db";
    public static final int db_ver = 1;

    public DatabaseHandler(Context context) {
        super(context, db_name, null, db_ver);
    }

    public long insertcustomerdetail(ContentValues c){
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert("UserDetails",null,c);
        db.close();
        return id;
    }


    public int getdetails(String email, String password){
        String SQL = "SELECT * FROM UserDetails WHERE Email ='"+email+"' AND Password ='"+password+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            return 1;
        }
        return 0;
    }
}
