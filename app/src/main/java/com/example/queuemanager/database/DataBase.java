package com.example.queuemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "myDataBase", null, 1);
    }

    public static void makePost(Context context, int userId, int id, String title, String body) {
        ContentValues cv = new ContentValues();
        cv.put("userId", userId);
        cv.put("id", id);
        cv.put("title", title);
        cv.put("body", body);

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.insert("posts", null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table posts ("
                + "userId integer,"
                + "id integer primary key,"
                + "title text,"
                + "body text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
