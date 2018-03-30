package com.example.tien.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AwardList extends AppCompatActivity {
    private ListView list;

    private MyAdapterAW  myAdapterAW;
    private DBfunction DBfunction;
    private int[] arr;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.activity_award_list);

        //7/15 ADD
        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int vWidth = dm.widthPixels;
        button1.getLayoutParams().width=(int)(vWidth*0.25);
        button2.getLayoutParams().width=(int)(vWidth*0.25);
        button3.getLayoutParams().width=(int)(vWidth*0.25);
        button4.getLayoutParams().width=(int)(vWidth*0.25);

        //7/18ADD
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AwardList.this, record.class);
                startActivity(intent);
                AwardList.this.finish();
            }
        }
        );
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AwardList.this, daily.class);
                startActivity(intent);
                AwardList.this.finish();
            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AwardList.this, Mine.class);
                startActivity(intent);
                AwardList.this.finish();

            }
        });
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AwardList.this, addfirst.class);
                startActivity(intent);
                AwardList.this.finish();
            }
        });
        //到這裡

        List<taskItemAW> targetList = new ArrayList<taskItemAW>();
        list=(ListView)findViewById(R.id.listView);
        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
        Cursor s =DBfunction.runAwardSQL();
        arr = new int[s.getCount()];
        int i=0;
        if(s.moveToFirst()){
            do{
                arr[i++]=s.getInt(0);
                targetList.add(new taskItemAW(s.getString(1),s.getString(2),s.getInt(0),s.getString(3)));
            }while(s.moveToNext());
        }
        myAdapterAW = new MyAdapterAW(AwardList.this,targetList);
        list.setAdapter(myAdapterAW);
        list.setAdapter(myAdapterAW);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View view, int position, long id) {
                ListView list=(ListView)arg0 ;
                Global temp = ( Global)getApplicationContext();
                temp .TASKID =arr[position] ;
                //Toast.makeText(AwardList.this,"ID:"+id+"目標："+temp .TASKID,Toast.LENGTH_LONG).show();

            }
        });

        //0805add 連結每日簽到換圖
        Cursor d =DBfunction.runDailySQL();
        if(d.moveToFirst()){
            String str = d.getString(1);
            int y = Integer.valueOf(str).intValue();
            if(y==1){
                button3.setBackgroundResource(R.drawable.signone);
            }
            else{
                button3.setBackgroundResource(R.drawable.menu_long_3);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(AwardList.this, record.class);
        startActivity(intent);
        AwardList.this.finish();
        return super.onOptionsItemSelected(item);
    }
}
