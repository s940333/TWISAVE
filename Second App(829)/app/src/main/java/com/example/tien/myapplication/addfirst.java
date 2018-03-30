package com.example.tien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tien on 2016/7/14.
 */
public class addfirst extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private  int clo = 0;
    private DBfunction DBfunction;
    ImageView imgTarget1,imgTarget2,imgTarget3,imgTarget4,imgTarget5,imgTarget6,imgTarget7,imgTarget8,imgTarget9;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.addfirst);

        //0809second
        //addGuideImage(R.drawable.addfirstcir);

        //0811TEST
        preferences = getSharedPreferences("addfirst", Context.MODE_PRIVATE);
        //判斷是不是第一次
        if(preferences.getBoolean("firststart", true)){
            editor = preferences.edit();
            editor.putBoolean("firststart",false);
            editor.commit();
            addGuideImage(R.drawable.addfirstcir);
        }

        //7/15 ADD
        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int vWidth = dm.widthPixels;
        int vHeight = dm.widthPixels;
        button1.getLayoutParams().width=(int)(vWidth*0.25);
        button2.getLayoutParams().width=(int)(vWidth*0.25);
        button3.getLayoutParams().width=(int)(vWidth*0.25);
        button4.getLayoutParams().width=(int)(vWidth*0.25);
        //LinearLayout big = (LinearLayout)findViewById(R.id.big);
        //big.getLayoutParams().width=(int)(vWidth);
        //big.getLayoutParams().height=(int)(vHeight);

        //0727add
        DBfunction = new DBfunction(getApplicationContext());


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
        d.close();
        DBfunction.close();

        //7/18ADD
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(addfirst.this, record.class);
                startActivity(intent);
                addfirst.this.finish();
            }
        }
        );
        button2.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(addfirst.this, addfirst.class);
                                           startActivity(intent);
                                           addfirst.this.finish();
                                       }
                                   }
        );
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(addfirst.this, daily.class);
                startActivity(intent);
                addfirst.this.finish();
            }
        }
        );
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(addfirst.this, Mine.class);
                startActivity(intent);
                addfirst.this.finish();
            }
        }
        );
        //到這裡

        imgTarget1 = (ImageView)findViewById(R.id.imgTarget1);
        imgTarget2 = (ImageView)findViewById(R.id.imgTarget2);
        imgTarget3 = (ImageView)findViewById(R.id.imgTarget3);
        imgTarget4 = (ImageView)findViewById(R.id.imgTarget4);
        imgTarget5 = (ImageView)findViewById(R.id.imgTarget5);
        imgTarget6 = (ImageView)findViewById(R.id.imgTarget6);
        imgTarget7 = (ImageView)findViewById(R.id.imgTarget7);
        imgTarget8 = (ImageView)findViewById(R.id.imgTarget8);
        imgTarget9 = (ImageView)findViewById(R.id.imgTarget9);

        imgTarget1.setOnClickListener(ChooseTarget);
        imgTarget2.setOnClickListener(ChooseTarget);
        imgTarget3.setOnClickListener(ChooseTarget);
        imgTarget4.setOnClickListener(ChooseTarget);
        imgTarget5.setOnClickListener(ChooseTarget);
        imgTarget6.setOnClickListener(ChooseTarget);
        imgTarget7.setOnClickListener(ChooseTarget);
        imgTarget8.setOnClickListener(ChooseTarget);
        imgTarget9.setOnClickListener(ChooseTarget);


        return super.onCreateOptionsMenu(menu);
    }

    private View.OnClickListener ChooseTarget =new View.OnClickListener(){
        int chooseTemp;
        public void onClick(View v) {
            if(imgTarget1.getId() == v.getId()){
                chooseTemp=1;
            }
            else if(imgTarget2.getId() == v.getId()){
                chooseTemp=2;
            }
            else if(imgTarget3.getId() == v.getId()){
                chooseTemp=3;
            }
            else if(imgTarget4.getId() == v.getId()){
                chooseTemp=4;
            }
            else if(imgTarget5.getId() == v.getId()){
                chooseTemp=5;
            }
            else if(imgTarget6.getId() == v.getId()){
                chooseTemp=6;
            }
            else if(imgTarget7.getId() == v.getId()){
                chooseTemp=7;
            }
            else if(imgTarget8.getId() == v.getId()){
                chooseTemp=8;
            }
            else if(imgTarget9.getId() == v.getId()){
                chooseTemp=9;
            }
            Intent intent = new Intent();
            intent.setClass(addfirst.this,add.class);
            intent.putExtra("Target_Number",chooseTemp);
            startActivity(intent);
            addfirst.this.finish();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(addfirst.this, record.class);
        startActivity(intent);
        addfirst.this.finish();
        return super.onOptionsItemSelected(item);
    }



    //0809屏幕
    private int guideResourceId = 0;// 引导页图片资源id

    //显示引导图片
    public void addGuideImage(int resId) {
        guideResourceId = resId;
        //    psp = new PromptSharedPreferences();
        View view = getWindow().getDecorView().findViewById(
                R.id.addfirst);// 查找通过setContentView上的根布局
        if (view == null)
            return;
        //if (psp.takeSharedPreferences(this)) {
        // 有过功能引导，就跳出
        //    return;
        //}
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            final FrameLayout frameLayout = (FrameLayout) viewParent;
            if (guideResourceId != 0) {
                // 设置了引导图片
                final ImageView guideImage = new ImageView(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                guideImage.setLayoutParams(params);
                //改透明度而
                guideImage.setImageAlpha(200);
                guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
                guideImage.setImageResource(guideResourceId);
                guideImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除引导图片
                        frameLayout.removeView(guideImage);
                        //保存记录
                        //                   psp.saveSharedPreferences(PromptAct.this, "启动了");
                    }
                });

                frameLayout.addView(guideImage);// 添加引导图片

            }
        }
    }

}