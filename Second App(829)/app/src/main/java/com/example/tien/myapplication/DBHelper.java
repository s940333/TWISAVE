package com.example.tien.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roi on 2016/7/22.
 */
public class DBHelper extends SQLiteOpenHelper{

    // 資料庫名稱
    public static final String DATABASE_NAME = "mydata";

    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;

    // 資料庫物件，固定的欄位變數
    public static SQLiteDatabase db;

    //資料表 目標的類別欄(targetItemId,targetName)
    public static final String tb_name="TargetItem";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (db == null || !db.isOpen()) {
            db = new DBHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        }
        return db;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建立應用程式需要的表格
        db.execSQL(DBfunction.createTargetTable);
        db.execSQL(DBfunction.createTaskTable);
        db.execSQL(DBfunction.createDailyTable);
        db.execSQL(DBfunction.createAwardTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 刪除原有的表格
        // 待會再回來完成它

        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }
}
