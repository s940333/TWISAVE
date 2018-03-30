package com.example.tien.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tien on 2016/8/11.
 */
public class MyAdapterAW extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<taskItemAW> data;
    public MyAdapterAW(Context context, List<taskItemAW> data){
        myInflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public int getCount() {
        return  data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return data.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return data.indexOf(getItem(position));
    }

    private class ViewHolder {
        TextView txtid;
        TextView txtInfor;
        TextView txtNum;
        ImageView imgPic;

        public ViewHolder(TextView txtid,TextView txtInfor,TextView txtNum,ImageView imgPic){
            this.txtid = txtid;
            this.txtInfor = txtInfor;
            this.txtNum = txtNum;
            this.imgPic = imgPic;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.awardlist_task, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.txtid),
                    (TextView) convertView.findViewById(R.id.txtInfor),
                    (TextView) convertView.findViewById(R.id.txtNum),
                    (ImageView) convertView.findViewById(R.id.imgPic)

            );
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        taskItemAW  data = (taskItemAW)getItem(position);
        holder.txtInfor.setText(data.getInfor());
        holder.txtNum.setText("序號: "+data.getNum());

        String resid = data.getimgpic();
        switch(resid){

            case "fish":
                holder.imgPic.setBackgroundResource(R.drawable.fish);
                break;
            case "eategg":
                holder.imgPic.setBackgroundResource(R.drawable.eategg);
                break;
            case "chicken":
                holder.imgPic.setBackgroundResource(R.drawable.chicken);
                break;
            case "tooth":
                holder.imgPic.setBackgroundResource(R.drawable.tooth);
                break;
            case "discount":
                holder.imgPic.setBackgroundResource(R.drawable.discount);
                break;
            case "carrot":
                holder.imgPic.setBackgroundResource(R.drawable.carrot);
                break;
            case "gas":
                holder.imgPic.setBackgroundResource(R.drawable.gas);
                break;
            case "wine":
                holder.imgPic.setBackgroundResource(R.drawable.wine);
                break;
            case "noodle":
                holder.imgPic.setBackgroundResource(R.drawable.noodle);
                break;
            default:
                holder.imgPic.setBackgroundResource(R.drawable.beer);
                break;
        }
        return convertView;
    }
}


