package com.example.tien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class dailyanimate extends AppCompatActivity {

    private DBfunction DBfunction;
    private AnimationDrawable imgtt;

    SQLiteDatabase db;
    static final String db_name ="testDB";
    static final String tb_name3="Daily";//資料表 每日簽到狀態
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.dailyanimate);

        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
        //
        final ImageView img5 = (ImageView)findViewById(R.id.image5);


        //7/15 ADD 按鈕隨螢幕大小而變動
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

        //7/18ADD Menu的onclick
        button1.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(dailyanimate.this, record.class);
                                           startActivity(intent);
                                           dailyanimate.this.finish();
                                       }
                                   }
        );
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(dailyanimate.this, addfirst.class);
                startActivity(intent);
                dailyanimate.this.finish();
            }
        });
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(dailyanimate.this, daily.class);
                startActivity(intent);
                dailyanimate.this.finish();
            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(dailyanimate.this, Mine.class);
                //intent.setClass(record.this, Mine.class);
                //暫時先改成task list
                startActivity(intent);
                dailyanimate.this.finish();
            }
        });
        //到這裡

        //0727add 每日簽到
        Button btn3 = (Button)findViewById(R.id.button3);
        Cursor d =DBfunction.runDailySQL();
        if(d.moveToFirst()){
            String str = d.getString(1);
            int i = Integer.valueOf(str).intValue();
            if(i==1){
                btn3.setBackgroundResource(R.drawable.signone);
            }
            else{
                btn3.setBackgroundResource(R.drawable.menu_long_3);
            }
        }


        //0804把button改掉增倒數
        new CountDownTimer(5200, 1000)
        {
            public void onTick(long millisUnitFinished) {
                img5.setBackgroundResource(R.drawable.animationtwist);
                imgtt = (AnimationDrawable)img5.getBackground();
                imgtt.start();
            }
            //3秒完執行時

            public void onFinish()
            {
                Intent intent = new Intent();
                intent.setClass(dailyanimate.this, dailyaward.class);
                startActivity(intent);
                dailyanimate.this.finish();
                imgtt.stop();
                imgtt = null;
            }
        }.start();

        db= openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(dailyanimate.this, record.class);
        startActivity(intent);
        dailyanimate.this.finish();




        return super.onOptionsItemSelected(item);
    }
}
