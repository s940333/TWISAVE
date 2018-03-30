package com.example.tien.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class record extends AppCompatActivity {
    private DBfunction DBfunction;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //
    private AnimationDrawable imgtt;

    TextView textTmoney,textHasSaved,textTarget,textWrite;
    ImageView imgDevice;
    int maxTaskId;
    TextView textPoint;
    String[] pic ={"bluegreen_01","purple_01","orange_01","blue_01","yello_01"};
    Cursor c;
    ImageView pgb;
    Global temp;
    int taskID;
    Cursor w;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.activity_record);

        final ImageView img3 = (ImageView)findViewById(R.id.imageView3);
        img3.setVisibility(View.INVISIBLE);

        //final ProgressBar pgb0 = (ProgressBar)findViewById(R.id.ProgressBar0);
        final ImageView img = (ImageView)findViewById(R.id.imageView);
        //pgb0.setVisibility(View.INVISIBLE);
        temp = (Global)getApplicationContext();
        taskID = temp.TASKID;

        //測試目前taskId
        // Toast.makeText(record.this,String.valueOf(taskID) ,Toast.LENGTH_LONG).show();


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
                intent.setClass(record.this, record.class);
                startActivity(intent);
                record.this.finish();
            }
        }
        );
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(record.this, addfirst.class);
                startActivity(intent);
                record.this.finish();
            }
        });
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(record.this, daily.class);
                startActivity(intent);
                record.this.finish();
            }
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(record.this, Mine.class);
                //intent.setClass(record.this, Mine.class);
                //暫時先改成task list
                startActivity(intent);
                record.this.finish();
            }
        });
        //到這裡

        textTmoney = (TextView)findViewById(R.id.textView11);
        textHasSaved =(TextView)findViewById(R.id.textView20);
        textTarget =(TextView)findViewById(R.id.textView14);
        imgDevice=(ImageView)findViewById(R.id.imageView);
        textWrite =(TextView)findViewById(R.id.textView21);
        textPoint=(TextView)findViewById(R.id.textView5);

        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
        //設置toast
        DBfunction.setToast(this);
        c =DBfunction.assignTaskSQL(taskID);
        if(c.moveToLast()){
            textTarget=(TextView)findViewById(R.id.textView11);
            textTarget.setText("目標："+c.getString(3));
            textWrite.setText(" - "+c.getString(4)); //目標的備註
            textHasSaved.setText("存錢："+c.getString(5));

            textPoint.setText(c.getString(6)); //每日簽到 顯示點數
            ((TextView)findViewById(R.id.textView14)).setText(c.getString(9));
            changePic(c.getInt(7)-1);
            Log.i("ID:",c.getString(7));
            //(taskId-0,targetItemId-1,tDateline-2,tMoney-3,tInfo-4,tSave-5,tExp-6,tLevel-7,tStatus-8,targetName-9)
        }

        pgb = (ImageView)findViewById(R.id.ProgressBar01);
        //0728判斷錢有沒有存滿，有的話會跳通知
        if(c.moveToLast()){
            String str = c.getString(3);
            int money = Integer.valueOf(str).intValue();  //目標
            String str1 = c.getString(5);
            int savemoney = Integer.valueOf(str1).intValue(); //已存
            if (money<=savemoney) {
                Toast.showToast("    恭喜完成目標\n可以「點擊」畫面中的扭蛋機囉");

                imgDevice.setVisibility(View.INVISIBLE);//讓扭蛋機圖消失換可以轉動的動畫

                ImageView imv1 = (ImageView)findViewById(R.id.imageView7);
                imv1.setVisibility(View.VISIBLE);
                //然後可以增加扭蛋機按鈕轉動

                pgb.setVisibility(View.VISIBLE);
                //顯示動畫

                imv1.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(record.this, acihevegoalanimation.class);//要到播放動畫~~~打錯字啦啦啦啦GGACHIEVEQQ
                        startActivity(intent);
                        record.this.finish();
                    }
                });
            }
            else
            {
                ImageView imv1 = (ImageView)findViewById(R.id.imageView7);
                imv1.setVisibility(View.INVISIBLE);
                //在還沒有存到錢的時候，按鈕和動畫是隱藏起來的
                pgb.setVisibility(View.INVISIBLE);

                //0811金額的跳屏
                preferences = getSharedPreferences("record", Context.MODE_PRIVATE);
                //判斷是不是第一次
                if(preferences.getBoolean("recordfirststart", true)){
                    editor = preferences.edit();
                    editor.putBoolean("recordfirststart",false);
                    editor.commit();
                    addGuideImage(R.drawable.recordblack);
                    addGuideImage1(R.drawable.addsucess);
                }
            }
        }

        Cursor c =DBfunction.runDailySQL();
        //一開始沒有資料的話就新增每日簽到狀態
        if(c.getCount()==0){
            DBfunction.addDaily(1);
            //預設一開始為可按 1 可按 0 不能按
        }

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

        /*
        //舊的升級按鈕
        final Button btnUp=(Button)findViewById(R.id.button8);
        if(c.moveToLast()){

            // textPoint.setText(s.getString(6)+" Lv."+s.getString(7));
           // maxTaskId=c.getInt(0);

            //升級按鈕 暫時設為有轉就可以直接升級
            if(c.getInt(6)>0){
                if(c.getInt(7)<5){
                    btnUp.setVisibility(View.VISIBLE);
                }
            }
        }
        //舊的升級按鈕 onclick
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBfunction.upExp(0,taskID);
                Cursor w =DBfunction.assignTaskSQL(taskID);
                if(w.moveToLast()){
                    DBfunction.upLevel(w.getInt(7),taskID);

                    changePic(w.getInt(7));
                    textPoint.setText("0  ");
                }
                w.close();
                btnUp.setVisibility(View.INVISIBLE);
            }
        });

*/
        //0810 升級扭蛋機
        LinearLayout layoutexp=(LinearLayout)findViewById(R.id.layoutexp);
        layoutexp.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                         w=DBfunction.assignTaskSQL(taskID);
                        if(w.moveToLast()) {
                            //若可以升級 扭蛋機尚未五級
                            if (w.getInt(7) < 5) {
                                Log.i("當低於三級 扭蛋機現在等級：", w.getString(7));
                                Log.i("當低於三級 點數：", w.getString(6));
                                //如果點數大於三 則可以升級
                                if (w.getInt(6) >= 3) {

                                    AlertDialog.Builder dialog = new AlertDialog.Builder(record.this);
                                    dialog.setTitle("是否將扭蛋機升級呢？");
                                    dialog.setMessage("扭蛋機等級越高，扭蛋獎勵越好噢～");
                                    dialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    });
                                    dialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {


                                            //若可以升級 扭蛋機還在五級以內 則有是否升級按鈕
                                            //TODO
                                            DBfunction.upExp(0, taskID);
                                            textPoint.setText("0  ");
                                            DBfunction.upLevel(w.getInt(7), taskID);

                                            //0812改成自動跳轉
                                            new CountDownTimer(3000, 1000)
                                            {
                                                public void onTick(long millisUnitFinished) {
                                                    //pgb0.setVisibility(View.VISIBLE);
                                                    img.setVisibility(View.INVISIBLE);
                                                    img3.setVisibility(View.VISIBLE);
                                                    img3.setBackgroundResource(R.drawable.animationfix);
                                                    imgtt = (AnimationDrawable)img3.getBackground();
                                                    imgtt.start();
                                                }
                                                public void onFinish()
                                                {
                                                    imgtt.stop();
                                                    img3.setVisibility(View.INVISIBLE);
                                                    imgtt = null;
                                                    changePic(w.getInt(7));
                                                    //pgb0.setVisibility(View.INVISIBLE);
                                                    img.setVisibility(View.VISIBLE);

                                                }
                                            }.start();
                                        }

                                    });
                                    /*
                                    dialog.setNeutralButton("取消",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                                    */
                                    dialog.show();

                                }
                                //未集滿三點
                                else {
                                    Toast.showToast("集滿三點即可升級扭蛋機");
                                }
                            }
                            //若可以升級 扭蛋機已經五級 則告知以最大級
                            else {
                                Toast.showToast("扭蛋機已達到最高等級\n      快來完成目標吧!!" );
                            }
                        }
                    }
                }
        );

        // 點擊扭蛋機時
        imgDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBfunction.upExp(0,taskID);
                Cursor w =DBfunction.assignTaskSQL(taskID);
                if(w.moveToLast()){
                    if(w.getInt(3)>w.getInt(5)){
                        Toast.showToast("達到目標才可獲取扭蛋獎勵");
                    }
                }
                w.close();
            }
        });
        //0729跳存入東西的視窗
        final EditText et = new EditText(record.this);
        et.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        //0801增加已存那邊可以按存錢
        TextView tv12 =(TextView)findViewById(R.id.textView12);
        //現在是用大條的可以點選存錢
        tv12.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder albtv = new AlertDialog.Builder(record.this);
                albtv.setTitle("請存錢");
                albtv.setIcon(R.drawable.fish);
                //alb.setView(new EditText(record.this));
                albtv.setView(et);
                albtv.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if("".equals(et.getText().toString().trim())){
                            //如果沒輸入金額的話
                        }
                        else {
                            //這個是輸入的存錢數
                            String newsave = et.getText().toString();
                            int addnewsave = Integer.valueOf(newsave);

                            //就存進去
                            Cursor c = DBfunction.assignTaskSQL(taskID);
                            if (c.moveToLast()) {
                                String presavemoney = c.getString(5);
                                //還要抓取現在資料庫所存的錢
                                //String presavemoney =textHasSaved.getText().toString();
                                int addtemp = Integer.valueOf(presavemoney);
                                //存入資料庫
                                int totalSave = addtemp + addnewsave;
                                DBfunction.addMoney(totalSave, taskID);
                                //這是在已存那邊做更動
                                String change = String.valueOf(totalSave);
                                textHasSaved.setText("存錢：" + change);
                                //重新整理不然存入無法做動畫
                                Intent intent = new Intent();
                                intent.setClass(record.this, record.class);
                                startActivity(intent);
                                record.this.finish();
                            }
                        }
                    }
                } );
                albtv.show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    //判斷扭蛋機等級
    public void changePic(int level){
        String uri="";
        uri = "@drawable/"+pic[level]; //圖片路徑和名稱
        int imageResource = getResources().getIdentifier(uri, null, getPackageName()); //取得圖片Resource位子
        ((ImageView)findViewById(R.id.imageView)).setBackgroundResource(imageResource);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(record.this, record.class);
        startActivity(intent);
        record.this.finish();
        return super.onOptionsItemSelected(item);
    }

    //0809屏幕
    private int guideResourceId = 0;// 引导页图片资源id

    //显示引导图片
    public void addGuideImage(int resId) {
        guideResourceId = resId;
        View view = getWindow().getDecorView().findViewById(R.id.record);// 查找通过setContentView上的根布局
        if (view == null)
            return;
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
                    }
                });

                frameLayout.addView(guideImage);// 添加引导图片
            }
        }
    }



    //0815屏幕(but 不透明
    private int guideResourceId1 = 0;// 引导页图片资源id

    //显示引导图片
    public void addGuideImage1(int resId) {
        guideResourceId1 = resId;
        View view = getWindow().getDecorView().findViewById(R.id.record);// 查找通过setContentView上的根布局
        if (view == null)
            return;
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            final FrameLayout frameLayout = (FrameLayout) viewParent;
            if (guideResourceId1 != 0) {
                // 设置了引导图片
                final ImageView guideImage = new ImageView(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                guideImage.setLayoutParams(params);
                //改透明度而
                guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
                guideImage.setImageResource(guideResourceId1);
                guideImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除引导图片
                        frameLayout.removeView(guideImage);
                    }
                });

                frameLayout.addView(guideImage);// 添加引导图片
            }
        }
    }
}
