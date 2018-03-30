package com.example.tien.myapplication;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by tien on 2016/7/21.
 */
public class MyReceiver extends BroadcastReceiver {

    SQLiteDatabase db;
    static final String db_name ="mydata";
    static final String tb_name3="Daily";//資料表 每日簽到狀態

    @Override
    public void onReceive(Context context, Intent intent)
    {
        db = context.openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        //確認有沒有定時跳提醒
        //Toast.makeText(context, "每日簽到 已新增",Toast.LENGTH_LONG).show();
        upDaily(1); //更新成1
    }

    private void upDaily(int  dType){
        ContentValues cv=new ContentValues(1);
        cv.put("dStatus", dType);
        db.update(tb_name3,cv,"dailyId="+1,null);
    }
}