package com.example.tien.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by roi on 2016/7/22.
 */
public class DBfunction {

    // 資料庫物件
    public static SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public DBfunction(Context context) {
        db = DBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }
    //**以上幾乎是固定的
    //**資料庫中資料表的Table與欄位

    //資料表 目標的類別(targetItemId,targetName)
    static final String tb_name="TargetItem";
    //資料表  目標任務(taskId,targetItemId,tDateline,tMoney,tInfo,tSave,tExp)
    static final String tb_name2="Task";
    //資料表  每日簽到
    static final String tb_name3="Daily";
    //資料表  每日簽到
    static final String tb_name4="Award";

    //新增 targetTable  的 SQL
    public static final String createTargetTable= "CREATE TABLE IF NOT EXISTS "+
            tb_name+
            "(targetItemId INTEGER,"+
            "targetName VARCHAR)";
    //新增 taskTable  的 SQL
    public static final String createTaskTable="CREATE TABLE IF NOT EXISTS "+
            tb_name2+
            "(taskId INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "targetItemId INTEGER NOT NULL,"+
            "tDateline VARCHAR ,"+
            "tMoney INTEGER NOT NULL,"+
            "tInfo VARCHAR NOT NULL,"+
            "tSave INTEGER NOT NULL,"+
            "tExp INTEGER NOT NULL,"+
            "tLevel INTEGER NOT NULL,"+
            "tStatus INTEGER NOT NULL)"; //0為未完成 1為完成
    //新增 DailyTable 的 SQL
    public static final String createDailyTable= "CREATE TABLE IF NOT EXISTS "+
            tb_name3+
            "(dailyId INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "dStatus INTEGER NOT NULL)";

    //新增 Award 的 SQL
    public static final String createAwardTable= "CREATE TABLE IF NOT EXISTS "+
            tb_name4+
            "(aId INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "aName VARCHAR NOT NULL,"+
            "aPic VARCHAR NOT NULL,"+
            "aCoupon VARCHAR NOT NULL)";



    //新增TargetTable的內容 若無資料就新增
    public  static void checkTargetTable() {
        Cursor c =db.rawQuery("SELECT * FROM "+tb_name,null);
        if(c.getCount()==0){
            addData(1,"旅遊");
            addData(2,"教育");
            addData(3,"3C");
            addData(4,"食");
            addData(5,"衣");
            addData(6,"住");
            addData(7,"行");
            addData(8,"育");
            addData(9,"樂");
        }
    }
    // 新增TargetTable的資料的 function
    public static void addData(int tarId, String tarName){
        ContentValues cv=new ContentValues(2);
        cv.put("targetItemId",tarId);
        cv.put("targetName",tarName);
        db.insert(tb_name,null,cv);
    }



    //印TargetTable 中的資料 測試用
    public static String printData(){
        String data="";
        Cursor cursor=db.rawQuery("SELECT * FROM "+tb_name,null);
        if(cursor.moveToFirst()){
            do{
                data+=cursor.getString(0)+"\n";
                data+=cursor.getString(1)+"\n";
                //data+=c.getString(3)+"\n";
                //data+=c.getString(4)+"\n";
                data+="============\n";

            }while(cursor.moveToNext());
        }
        return data;
    }

    //新增 獎勵
    public void addAward(String name,String coupon,String pic){
        ContentValues cv=new ContentValues(3);
        cv.put("aName",name);
        cv.put("aPic",coupon);
        cv.put("aCoupon",pic);
        db.insert(tb_name4,null,cv);
    }




    //新增任務項目.金額.註解.function
    public void addTask(int tarId,int taskMoney,String taskInfo,int save){
        ContentValues cv=new ContentValues(7);
        cv.put("targetItemId",tarId);
        cv.put("tMoney",taskMoney);
        cv.put("tInfo",taskInfo);
        cv.put("tSave",save);
        cv.put("tExp",0);
        cv.put("tLevel",1);
        cv.put("tStatus",0);
        db.insert(tb_name2,null,cv);
    }

    //0805執行  查詢 TaskTable SQL 指定的ＳＱＬ

    public Cursor assignTaskSQL(int id){
        String sql="SELECT * FROM "+tb_name2+" NATURAL JOIN "+tb_name+"  WHERE taskId='"+id+"' ORDER BY taskId ASC";
        Cursor cv=db.rawQuery(sql,null);
        return cv;
    }

    //執行  查詢 TaskTable SQL
    public Cursor runTaskSQL(){
        String sql="SELECT * FROM "+tb_name2+" NATURAL JOIN "+tb_name+" ORDER BY taskId ASC";
        Cursor cv=db.rawQuery(sql,null);
        return cv;
    }
    //執行  查詢 DailyTable SQL
    public Cursor runDailySQL(){
        String sql="SELECT * FROM "+tb_name3+" NATURAL JOIN "+tb_name;
        Cursor cv=db.rawQuery(sql,null);
        return cv;
    }

    public Cursor runAwardSQL(){
        String sql="SELECT * FROM "+tb_name4;
        Cursor cv=db.rawQuery(sql,null);
        return cv;
    }

    /*
    private static taskItem a(){
        Cursor cv=db.rawQuery("SELECT * FROM "+tb_name2,null);
        //Cursor cv=db.rawQuery("SELECT * FROM "+tb_name2+" NATURAL JOIN "+tb_name,null);
        taskItem item=new taskItem();
        if(cv.moveToLast()){

            item= new taskItem(
                    cv.getInt(0),
                    cv.getInt(1),
                    cv.getString(2),
                    cv.getInt(3),
                    cv.getString(4),
                    cv.getInt(5),
                    cv.getInt(6));
        }
        return item;
    }*/

    //update 每日簽到的經驗值
    public void upExp(int num,int maxTaskId){
        ContentValues cv=new ContentValues(1);
        cv.put("tExp",num);
        db.update(tb_name2,cv,"taskId="+maxTaskId,null);
    }
    //存錢

    public void addMoney(int money,int maxTaskId){
        ContentValues cv=new ContentValues(2);
        Cursor s= assignTaskSQL(maxTaskId);
        if(s.moveToLast()){
            //當目標與已存金額相同時 把狀態改為 1完成

            if(s.getInt(3)<=s.getInt(5)+money){
                cv.put("tStatus",1);
            }
        }

        cv.put("tSave",money);
        db.update(tb_name2,cv,"taskId="+maxTaskId,null);
    }
    //新增 每日簽到狀態
    public void addDaily(int dType){
        ContentValues cv=new ContentValues(1);
        cv.put("dStatus",dType);
        db.insert(tb_name3,null,cv);
    }

    //update 每日簽到狀態
    public void upDaily(int dType){
        ContentValues cv=new ContentValues(1);
        cv.put("dStatus", dType);
        db.update(tb_name3,cv,"dailyId="+1,null);
    }
    //扭蛋機等級
    public void upLevel(int level,int maxTaskId){
        if(level==5){
        }
        else {
            ContentValues cv = new ContentValues(1);
            cv.put("tLevel", level + 1);
            db.update(tb_name2, cv, "taskId=" + maxTaskId, null);
        }
    }


    /*//0805Test
    public Cursor nullornot(){

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM "+tb_name2, null);
        //int i = c.getCount();
        return c;
    }*/


    //設置Toast
    public void setToast(Context  c){
        //Toast
        Toast.setContext(c);
        Toast.setPosition(Toast.Position.CENTER_BOTTOM);
        ToastView tv = new ToastView(c);
        //可由Toastview 中預設值指定
        //tv.setTextSize(20);
        //tv.setBackgroundColor(Color.rgb(242,243,175));
        //tv.setRadius(40);
        Toast.setToastView(tv);

    }
}