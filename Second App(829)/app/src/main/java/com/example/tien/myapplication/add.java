package com.example.tien.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by tien on 2016/7/13.
 */
public class add extends AppCompatActivity {
    //TODO 創建資料庫物件 並執行checkTargetTable()
    private DBfunction DBfunction;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    int tarNum; //addfirst 選定目標的傳值
    int money;
    String info;
    String tempMoney;
    EditText editNote,editMoney;
    Global temp;
    int taskID;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.add);


        //0811TEST
        preferences = getSharedPreferences("add", Context.MODE_PRIVATE);
        //判斷是不是第一次
        if(preferences.getBoolean("addfirststart", true)){
            editor = preferences.edit();
            editor.putBoolean("addfirststart",false);
            editor.commit();
            addGuideImage(R.drawable.addwrite1);
        }

        Button button = (Button) findViewById(R.id.button5);
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

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(add.this, record.class);
                startActivity(intent);
                add.this.finish();
            }
        });
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(add.this, addfirst.class);
                startActivity(intent);
                add.this.finish();
            }
        });
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(add.this, daily.class);
                startActivity(intent);
                add.this.finish();}
        });
        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(add.this, Mine.class);
                startActivity(intent);
                add.this.finish();
            }
        });

        setLayout();

        //0810TEST
        editNote.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }

                    //0811TEST
                    preferences = getSharedPreferences("add", Context.MODE_PRIVATE);
                    //判斷是不是第一次
                    if(preferences.getBoolean("addsecond", true)){
                        editor = preferences.edit();
                        editor.putBoolean("addsecond",false);
                        editor.commit();
                        addGuideImage(R.drawable.addmoney1);
                    }
                    editMoney.requestFocus();
                    return true;
                }
                return false;
            }
        });




        //呼叫資料庫
        DBfunction = new DBfunction(getApplicationContext());
        //檢查TargetTable 有無資料 若無就加入資料
        DBfunction.checkTargetTable();

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

        //選擇目標項目BTN ID
        Bundle bundle = this.getIntent().getExtras();
        tarNum =bundle.getInt("Target_Number");

        //設置toast
        DBfunction.setToast(this);
        ImageView imgTarget=(ImageView)findViewById(R.id.imageView2);
        TextView txtTar =(TextView)findViewById(R.id.textView28);

        // 判斷剛剛點了哪個目標類別
        switch(tarNum){
            case 1:
                imgTarget.setBackgroundResource( R.drawable.targetitem_1);
                txtTar.setText("旅遊");
                break;
            case 2:
                imgTarget.setBackgroundResource( R.drawable.targetitem_2);
                txtTar.setText("教育");
                break;
            case 3:
                imgTarget.setBackgroundResource( R.drawable.targetitem_3);
                txtTar.setText("3 C");
                break;
            case 4:
                imgTarget.setBackgroundResource( R.drawable.targetitem_4);
                txtTar.setText("食");
                break;
            case 5:
                imgTarget.setBackgroundResource( R.drawable.targetitem_5);
                txtTar.setText("衣");
                break;
            case 6:
                imgTarget.setBackgroundResource( R.drawable.targetitem_6);
                txtTar.setText("住");
                break;
            case 7:
                imgTarget.setBackgroundResource( R.drawable.targetitem_7);
                txtTar.setText("行");
                break;
            case 8:
                imgTarget.setBackgroundResource( R.drawable.targetitem_8);
                txtTar.setText("育");
                break;
            case 9:
                imgTarget.setBackgroundResource( R.drawable.targetitem_9);
                txtTar.setText("樂");
                break;


        }






        button.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                //新增目標項目.金額.註解   //0805多後面OR的部分
                if("".equals(editMoney.getText().toString().trim()) ||  "".equals(editNote.getText().toString().trim())){
                    //Toast toast = Toast.makeText(add.this,"請輸入目標金額與備註~",Toast.LENGTH_LONG);
                    //toast.show();

                    Toast.showToast("請輸入目標金額與備註~");
                }
                else {
                    tempMoney=editMoney.getText().toString();
                    money=Integer.valueOf(tempMoney);
                    info=editNote.getText().toString();
                    DBfunction.addTask(tarNum,money,info,0);

                    Cursor e=DBfunction.runTaskSQL();
                    if(e.moveToLast()){
                        temp = (Global)getApplicationContext();
                        temp.TASKID=e.getInt(0);
                    }
                    DBfunction.close();
                    Intent intent = new Intent();
                    intent.setClass(add.this, record.class);
                    startActivity(intent);
                    add.this.finish();
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void setLayout(){
        editNote = (EditText)findViewById(R.id.editText);
        editMoney = (EditText)findViewById(R.id.editText2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(add.this, record.class);
        startActivity(intent);
        add.this.finish();
        return super.onOptionsItemSelected(item);
    }


   //0809屏幕
    private int guideResourceId = 0;// 引导页图片资源id

    //显示引导图片
    public void addGuideImage(int resId) {
        guideResourceId = resId;
        //    psp = new PromptSharedPreferences();
        View view = getWindow().getDecorView().findViewById(
                R.id.add);// 查找通过setContentView上的根布局
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