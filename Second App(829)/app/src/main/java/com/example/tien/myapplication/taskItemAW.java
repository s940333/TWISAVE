package com.example.tien.myapplication;

/**
 * Created by tien on 2016/8/11.
 */
public class taskItemAW  {

    private String tInfo;
    private String tNum;
    private int tid;
    private String timgpic;
    private DBfunction DBfunction;

    public taskItemAW(){
    }
    public taskItemAW(String tInfo,String tNum,int tid,String timgpic){
        this.tInfo = tInfo;
        this.tNum = tNum;
        this.tid = tid;
        this.timgpic = timgpic;
    }

    public String getInfor(){
        return tInfo;
    }
    public void setInfor(String info){
        this.tInfo = info;
    }

    public String getNum(){
        return tNum;
    }
    public void setNum(String num){ this.tNum = num; }

    public int getid(){
        return tid;
    }
    public void setid(int id){
        this.tid = id;
    }

    public String getimgpic(){
        return timgpic;
    }
    public void setimgpic(String imgpic){
        this.timgpic = imgpic;
    }

}
