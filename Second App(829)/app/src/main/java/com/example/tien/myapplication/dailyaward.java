package com.example.tien.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by tien on 2016/7/18.
 */
public class dailyaward extends AppCompatActivity {
    int randomNum;
    Global temp;
    ImageView  imgAward;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool,menu);
        setContentView(R.layout.dailyaward);

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
                                           intent.setClass(dailyaward.this, record.class);
                                           startActivity(intent);
                                           dailyaward.this.finish();
                                       }
                                   }
        );
        button2.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(dailyaward.this, addfirst.class);
                                           startActivity(intent);
                                           dailyaward.this.finish();
                                       }
                                   }
        );
        button3.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(dailyaward.this, daily.class);
                                           startActivity(intent);
                                           dailyaward.this.finish();
                                       }
                                   }
        );
        button4.setOnClickListener(new Button.OnClickListener(){
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent();
                                           intent.setClass(dailyaward.this, Mine.class);
                                           startActivity(intent);
                                           dailyaward.this.finish();
                                       }
                                   }
        );
        //到這裡

        imgAward = (ImageView) findViewById(R.id.imgAward);
        temp = (Global)getApplicationContext();

        if (temp.DAILYCOINS==1){
            imgAward.setImageResource(R.drawable.dailyaward);
            temp.DAILYCOINS=2;
        }
        else{
            imgAward.setImageResource(R.drawable.dailyaward2);
            temp.DAILYCOINS=1;
        }


        imgAward.setOnClickListener(new Button.OnClickListener(){
                                        @Override
             public void onClick(View v) {
                  Intent intent = new Intent();
                  intent.setClass(dailyaward.this, record.class);
                  startActivity(intent);
                  dailyaward.this.finish();
              }
        }
        );

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent();
        intent.setClass(dailyaward.this, record.class);
        startActivity(intent);
        dailyaward.this.finish();
        return super.onOptionsItemSelected(item);
    }
}
