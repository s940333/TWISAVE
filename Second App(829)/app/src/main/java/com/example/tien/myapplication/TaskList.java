package com.example.tien.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends AppCompatActivity {
    private ListView list;
    private MyAdapter adapter;
    private DBfunction DBfunction;
    private int[] arr;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.activity_task_list);
        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
        DBfunction.setToast(this);

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
                intent.setClass(TaskList.this, record.class);
                startActivity(intent);
                TaskList.this.finish();
            }
        }
        );
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TaskList.this, daily.class);
                startActivity(intent);
                TaskList.this.finish();
            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TaskList.this, Mine.class);
                startActivity(intent);
                TaskList.this.finish();

            }
        });
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TaskList.this, addfirst.class);
                startActivity(intent);
                TaskList.this.finish();
            }
        });

        //到這裡



        List<taskItem> targetList = new ArrayList<taskItem>();
        list=(ListView)findViewById(R.id.listView);

        Cursor s =DBfunction.runTaskSQL();
        arr = new int[s.getCount()];
        int i=0;
        if(s.moveToFirst()){
            do{
                arr[i++]=s.getInt(0);
                targetList.add(new taskItem(s.getInt(0),s.getInt(1),s.getString(2),s.getInt(3),s.getString(4),s.getInt(5),s.getInt(6),s.getInt(7),s.getInt(8),s.getString(9)));
            }while(s.moveToNext());
        }
        adapter = new MyAdapter(TaskList.this,targetList);
        list.setAdapter(adapter);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View view, int position, long id) {
                //設置toast
                ListView list=(ListView)arg0 ;
                Global temp = ( Global)getApplicationContext();
                temp .TASKID =arr[position] ;
                //Toast.makeText(TaskList.this,"ID:"+id+"目標："+temp .TASKID,Toast.LENGTH_LONG).show();
                //Log.i("現在點哪個：",String.valueOf(arr[position]));
                Cursor a=DBfunction.assignTaskSQL(arr[position]);
                if(a.moveToLast()){
                    Log.i("跑狀態：",String.valueOf(a.getInt(8)));
                    if(a.getInt(8)==1){
                        Log.i("目標完成囉嚕囉ㄛ",String.valueOf(arr[position]));
                        Toast.showToast("這個目標達到囉！\n趕快去完成下一個");
                    }
                    else {
                        //Log.i("目標還沒達成拉拉拉",String.valueOf(arr[position]));

                        Intent intent = new Intent();
                        intent.setClass(TaskList.this, record.class);
                        startActivity(intent);
                        TaskList.this.finish();

                    }

                }

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
        intent.setClass(TaskList.this, record.class);
        startActivity(intent);
        TaskList.this.finish();
        return super.onOptionsItemSelected(item);
    }
}
