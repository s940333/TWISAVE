package com.example.tien.myapplication;

/**
 * Created by roi on 2016/7/22.
 */
public class taskItem {
    private int taskId;
    private int targetItemId;
    private String tDateline;//暫時無用
    private int tMoney;
    private String tInfo;
    private int tSave;
    private int tExp;
    private int tLevel;
    private int tStatus;

    private String targetName;
    private DBfunction DBfunction;

    public taskItem(){

    }
    public taskItem(int taskId,int targetItemId, String tDateline,int tMoney,String tInfo,int tSave,int tExp,int tLevel,int tStatus,String targetName){
        this.taskId=taskId;
        this.targetItemId=targetItemId;
        this.tDateline=tDateline;
        this.tMoney=tMoney;
        this.tInfo=tInfo;
        this.tSave=tSave;
        this.tExp=tExp;
        this.tLevel=tLevel;
        this.tStatus=tStatus;
        this.targetName=targetName;
    }

    public int getTaskId(){
        return taskId;
    }
    public void setTaskId(int id){
        this.taskId = id;
    }

    public int getTargetItemId(){
        return targetItemId;
    }
    public void setTargetItemId(int id){
        this.targetItemId = id;
    }

    public String getDateline(){
        return tDateline;
    }
    public void setDateline(String time){
        this.tDateline = time;
    }

    public int getMoney(){
        return tMoney;
    }
    public void setMoney(int money){
        this.tMoney = money;
    }

    public String getInfo(){
        return tInfo;
    }
    public void setInfo(String info){
        this.tInfo = info;
    }

    public int getSave(){
        return tSave;
    }
    public void setSave(int save){
        this.tSave = save;
    }

    public int getExp(){
        return tExp;
    }
    public void setExp(int exp){
        this.tExp = exp;
    }

    public int getLevel(){
        return tLevel;
    }
    public void setLevel(int level){
        this.tLevel = level;
    }

    public int getStatus(){
        return tStatus;
    }
    public void setStatus(int level){
        this.tStatus = level;
    }

    public String getTargetName(){
        return targetName;
    }
    public void setTargetName(String name){
        this.targetName = name;
    }
}
