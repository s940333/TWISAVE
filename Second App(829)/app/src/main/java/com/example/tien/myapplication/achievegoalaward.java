package com.example.tien.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class achievegoalaward extends AppCompatActivity {

    private Random random = new Random();
    private DBfunction DBfunction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.activity_achievegoalaward);

        //0728萬惡的Random ADD
        ImageButton imb2 = (ImageButton)findViewById(R.id.imageButton2);
        Random random = new Random();
        int i = random.nextInt(10); //要查一下是0-9還是0-10還是1-10  //應該是0-9

        //可以確認是否隨機有沒有跑掉
        //TextView tv = (TextView)findViewById(R.id.textView15);
        //tv.setText("Random number is :"+i);

        //0727add
        DBfunction = new DBfunction(getApplicationContext());
        Button btn3 = (Button)findViewById(R.id.button3);
        Cursor d =DBfunction.runDailySQL();
        if(d.moveToFirst()){
            String str = d.getString(1);
            int a = Integer.valueOf(str).intValue();
            if(a==1){
                btn3.setBackgroundResource(R.drawable.signone);
            }
            else{
                btn3.setBackgroundResource(R.drawable.menu_long_3);
            }
        }

        TextView tv8 = (TextView)findViewById(R.id.textView8);

        //如果要寫成九大類裡面不同的優惠
        //再使用隨機來產生圖片
        int cateid = random.nextInt(3); //應該是0-2
        String nameId = "R.id.eat"+cateid;
        //setBackground(nameId);
        //恭喜你得到某類的優惠

        //隨機出數字，可以切換到不同的icon圖案
        //i可能就要get 項目的id
        switch(i)
        {
            case 1:
                imb2.setBackgroundResource(R.drawable.eategg);
                tv8.setText("恭喜你獲得 全聯 雞蛋 : 第二盒半價，優惠碼是 AD5F277GX");
                DBfunction.addAward("雞蛋 : 第二盒半價","AD5F277GX","eategg");
                break;
            case 2:
                imb2.setBackgroundResource(R.drawable.chicken);
                tv8.setText("恭喜你獲得 肯德基 消費就送一塊炸雞，優惠碼是 AQ6F286GX");
                DBfunction.addAward("肯德基 消費就送一塊炸雞","AQ6F286GX","chicken");
                break;
            case 3:
                imb2.setBackgroundResource(R.drawable.fish);
                tv8.setText("恭喜你獲得 魚媽媽 折抵20元，優惠碼是 AC6F2864B");
                DBfunction.addAward("魚媽媽 折抵20元","AC6F2864B","fish");
                break;
            case 4:
                imb2.setBackgroundResource(R.drawable.tooth);
                tv8.setText("恭喜你獲得 馬偕醫院減免掛號費，優惠碼是 ACDO03D64B");
                DBfunction.addAward("馬偕醫院減免掛號費","ACDO03D64B","tooth");
                break;
            case 5:
                imb2.setBackgroundResource(R.drawable.discount);
                tv8.setText("恭喜你獲得 新光三越全面9折，優惠碼是 AF4O03D647");
                DBfunction.addAward("新光三越全面9折","AF4O03D647","discount");
                break;
            case 6:
                imb2.setBackgroundResource(R.drawable.carrot);
                tv8.setText("恭喜你獲得 松青 買1000折100，優惠碼是 AF3O03DDV4");
                DBfunction.addAward("松青 買1000折100","AF3O03DDV4","carrot");
                break;
            case 7:
                imb2.setBackgroundResource(R.drawable.gas);
                tv8.setText("恭喜你獲得 加油每公升降0.2元，優惠碼是 AF3O0D0573");
                DBfunction.addAward("加油每公升降0.2元","AF3O0D0573","gas");
                break;
            case 8:
                imb2.setBackgroundResource(R.drawable.wine);
                tv8.setText("恭喜你獲得 免費紅酒一瓶，優惠碼是 AF3LT90321");
                DBfunction.addAward("免費紅酒一瓶","AF3LT90321","wine");
                break;
            case 9:
                imb2.setBackgroundResource(R.drawable.noodle);
                tv8.setText("恭喜你獲得 消費就送一碗炸醬麵，優惠碼是 AF3LT90874");
                DBfunction.addAward("消費就送一碗炸醬麵","AF3LT90874","noodle");
                break;
            default:
                imb2.setBackgroundResource(R.drawable.beer);
                tv8.setText("恭喜你獲得 台灣啤酒買一送一，優惠碼是 AF3LT9Y642");
                DBfunction.addAward("台灣啤酒買一送一","AF3LT9Y642","beer");
        }
        //0728到這裡

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
                                           intent.setClass(achievegoalaward.this, record.class);
                                           startActivity(intent);
                                           achievegoalaward.this.finish();
                                       }
                                   }
        );
        button2.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(achievegoalaward.this, addfirst.class);
                                           startActivity(intent);
                                           achievegoalaward.this.finish();
                                       }
                                   }
        );
        button3.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(achievegoalaward.this, daily.class);
                                           startActivity(intent);
                                           achievegoalaward.this.finish();
                                       }
                                   }
        );
        button4.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(achievegoalaward.this, Mine.class);
                                           startActivity(intent);
                                           achievegoalaward.this.finish();
                                       }
                                   }
        );
        //到這裡

        ImageButton imageButtonbutton1 = (ImageButton) findViewById(R.id.imageButton2);
        imageButtonbutton1.setOnClickListener(new Button.OnClickListener(){
                                                  @Override
                                                  public void onClick(View v) {
                                                      Intent intent = new Intent();
                                                      intent.setClass(achievegoalaward.this,TaskList.class);
                                                      startActivity(intent);
                                                      achievegoalaward.this.finish();
                                                      //point;
                                                  }
                                              }
        );

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(achievegoalaward.this, record.class);
        startActivity(intent);
        achievegoalaward.this.finish();
        return super.onOptionsItemSelected(item);
    }
}