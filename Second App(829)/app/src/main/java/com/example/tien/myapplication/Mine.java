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
import android.widget.Button;
import android.widget.ImageButton;

public class Mine extends AppCompatActivity {

    private DBfunction DBfunction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.activity_mine);
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
                intent.setClass(Mine.this, record.class);
                startActivity(intent);
                Mine.this.finish();
            }
        }
        );
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, daily.class);
                startActivity(intent);
                Mine.this.finish();
            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, Mine.class);
                startActivity(intent);
                Mine.this.finish();
            }
        });
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, addfirst.class);
                startActivity(intent);
                Mine.this.finish();
            }
        });
        //到這裡

        //0805兩個圖片連結
        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton3);//twist
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton4);//gift

        imageButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, TaskList.class);
                startActivity(intent);
                Mine.this.finish();
            }
        }
        );

        imageButton2.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, AwardList.class);
                startActivity(intent);
                Mine.this.finish();
            }
        }
        );


        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
        //0727 每日簽到的資料庫判斷
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(Mine.this, record.class);
        startActivity(intent);
        Mine.this.finish();
        return super.onOptionsItemSelected(item);
    }
}
