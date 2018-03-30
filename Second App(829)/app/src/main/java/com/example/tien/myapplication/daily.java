package com.example.tien.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class daily extends AppCompatActivity {
     private DBfunction DBfunction;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    Button btnsign;
    AlarmManager am;//設置排程
    TextView textstatus;
    int maxTaskId=0;
    Global temp;
    int taskID;
    int dailycoins;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info,menu);
        setContentView(R.layout.daily);
        temp = (Global)getApplicationContext();
        taskID = temp.TASKID;

        //0815 HINT
        preferences = getSharedPreferences("daily", Context.MODE_PRIVATE);
        //判斷是不是第一次
        if(preferences.getBoolean("dailytstart", true)){
            editor = preferences.edit();
            editor.putBoolean("dailytstart",false);
            editor.commit();
            addGuideImage(R.drawable.dailyhint);
        }

        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
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

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(daily.this, record.class);
                startActivity(intent);
                daily.this.finish();
            }
        });
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(daily.this, addfirst.class);
                startActivity(intent);
                daily.this.finish();
            }
        });
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(daily.this, daily.class);
                startActivity(intent);
                daily.this.finish();
            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(daily.this, Mine.class);
                startActivity(intent);
                daily.this.finish();
            }
        });

        //db= openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        textstatus=(TextView)findViewById(R.id.textView7);
        /*
        //新增每日簽到 table
        String createDailyTable= "CREATE TABLE IF NOT EXISTS "+
                tb_name3+
                "(dailyId INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dStatus INTEGER NOT NULL)";

        db.execSQL(createDailyTable);
        */
        ImageButton imb1 = (ImageButton)findViewById(R.id.imageButton);

        Cursor c =DBfunction.runDailySQL();

        //抓目前 每日簽到狀態
        if(c.moveToFirst()){
            textstatus.setText(c.getString(1));
            //ADD0720 判斷按鈕需要消失還是隱藏
            String str = c.getString(1);
            int i = Integer.valueOf(str).intValue();
            if(i==1){
                imb1.setBackgroundResource(R.drawable.dailymachine);
                //put onclick

                //0808改imagebutton的連結
                imb1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent i = new Intent(daily.this, MyReceiver.class);
                        PendingIntent pi = PendingIntent.getBroadcast(daily.this,0,i,0);
                        Calendar c = Calendar.getInstance();
                        //如果要改成後一天指定時間出現(現在是指定隔天15點0分0秒的MillTime)
                /*c.add(Calendar.DATE,1);
                c.set(Calendar.HOUR_OF_DAY,15);
                c.set(Calendar.MINUTE,0);
                c.set(Calendar.SECOND,0);
                c.set(Calendar.MILLISECOND,0);
                long startTime = c.getTimeInMillis();
                am.set(AlarmManager.RTC_WAKEUP,startTime,pi);*/

                        am.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis()+10000,pi);
                        //另一個btnCancel      am.cancel(pi);
                        DBfunction.upDaily(0); //更新成0
                        Cursor k =DBfunction.runDailySQL();
                        if(k.moveToFirst()){
                            textstatus.setText(k.getString(1));
                        }

                        //計算+1個扭蛋
                        Cursor s =DBfunction.assignTaskSQL(taskID);
                        dailycoins= temp.DAILYCOINS;
                        int random=dailycoins; //暫時先設1
                        if(s.moveToLast()){
                            maxTaskId=s.getInt(0);
                            DBfunction.upExp(s.getInt(6)+random,maxTaskId);
                        }
                        Intent intent = new Intent();
                        intent.setClass(daily.this, dailyanimate.class);
                        startActivity(intent);
                        //daily.this.finish();
                    }
                });

            }
            else{
                imb1.setBackgroundResource(R.drawable.today);
                Toast.showToast("今天已經轉過扭蛋囉!");
            }
        }

        //0727add
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
        addGuideImage(R.drawable.dailyhint);
        //Intent intent = new Intent();
        //intent.setClass(daily.this, record.class);
        //startActivity(intent);
        //daily.this.finish();
        return super.onOptionsItemSelected(item);
    }
    /*
    //新增 每日簽到狀態
    private void addDaily(int dType){
        ContentValues cv=new ContentValues(1);
        cv.put("dStatus",dType);
        db.insert(tb_name3,null,cv);

    }
    //update 每日簽到狀態
    private void upDaily(int dType){
        ContentValues cv=new ContentValues(1);
        cv.put("dStatus", dType);
        db.update(tb_name3,cv,"dailyId="+1,null);
    }
    //update 每日簽到的經驗值
    private void upExp(int num){
        ContentValues cv=new ContentValues(1);
        cv.put("tExp",num);
        db.update(tb_name2,cv,"taskId="+maxTaskId,null);
    }
    */


    //0815屏幕
    private int guideResourceId = 0;// 引导页图片资源id

    //显示引导图片
    public void addGuideImage(int resId) {
        guideResourceId = resId;
        //    psp = new PromptSharedPreferences();
        View view = getWindow().getDecorView().findViewById(
                R.id.daily);// 查找通过setContentView上的根布局
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

