package com.example.tien.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private DBfunction DBfunction;
    Cursor initial;
    Global temp;
    ProgressBar pbr;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.activity_main);
        //pbr=(ProgressBar)findViewById(R.id.ProgressBar);
        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
        initial=DBfunction.runTaskSQL();


        //抓目前資料庫最新的資料 將最新的目標id指派給全域變數
        if(initial.moveToLast()){
            temp = (Global)getApplicationContext();
            temp.TASKID=initial.getInt(0);
        }

        //0804改成自動跳轉
        new CountDownTimer(5000, 1000)
        {
            public void onTick(long millisUnitFinished) {
                //Do nothing
            }
            //3秒完執行時
            public void onFinish()
            {
                Intent intent = new Intent();
                //如果沒有目標任務 則先跳轉去 新增目標
                if(initial.getCount()==0){
                    intent.setClass(MainActivity.this, addfirst.class);
                    startActivity(intent);
                    initial.close();
                    DBfunction.close();
                   // pbr.setVisibility(View.GONE);
                   // pbr.dismiss();
                    MainActivity.this.finish();

                }
                //有目標任務 則先跳轉去  首頁
                else {
                    intent.setClass(MainActivity.this, record.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                   // pbr.setVisibility(View.GONE);
                    initial.close();
                    DBfunction.close();
                }
            }
        }.start();


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

        Button button = (Button)findViewById(R.id.button2);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, addfirst.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
}
        );

        //7/18ADD
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, record.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, daily.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Mine.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, record.class);
        startActivity(intent);
        MainActivity.this.finish();
        return super.onOptionsItemSelected(item);
    }
}