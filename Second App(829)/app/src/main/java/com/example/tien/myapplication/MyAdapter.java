package com.example.tien.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by roi on 2016/7/28.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<taskItem> data;
    public MyAdapter(Context context, List<taskItem> data){
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
        TextView txtTaskId;
        TextView txtInfo;
        TextView txtTarName;
        TextView txtMoney;
        TextView txtSave;
        TextView txtStatus;
        ImageView imgStatus;

        public ViewHolder(TextView txtTaskId,TextView txtInfo,TextView TarName,TextView txtMoney,TextView txtSave,ImageView imgStatus){
            this.txtTaskId = txtTaskId;
            this.txtInfo = txtInfo;
            this.txtTarName = TarName;
            this.txtMoney = txtMoney;
            this.txtSave = txtSave;
            this.imgStatus = imgStatus;
            this.imgStatus = imgStatus;
            //this.txtStatus = txtStatus;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.listview_task, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.txtTaskId),
                    (TextView) convertView.findViewById(R.id.txtInfo),
                    (TextView) convertView.findViewById(R.id.txtTarName),
                    (TextView) convertView.findViewById(R.id.txtMoney),
                    (TextView) convertView.findViewById(R.id.txtSave),
                    (ImageView) convertView.findViewById(R.id.imgCheck)
                    //(TextView) convertView.findViewById(R.id.txtStatus)
            );
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        taskItem  data = (taskItem)getItem(position);
        //holder.txtTaskId.setText(String.valueOf(data.getTaskId()));
        holder.txtInfo.setText(String.valueOf(data.getInfo()));
        holder.txtTarName.setText(data.getTargetName());
        holder.txtMoney.setText(String.valueOf(data.getMoney()));
        holder.txtSave.setText(String.valueOf(data.getSave()));


        if(data.getStatus()==1) {

            holder.imgStatus.setBackgroundResource( R.drawable.checklist_ok);

        }

        else{

            holder.imgStatus.setBackgroundResource( R.drawable.checklist_no);
        }

        return convertView;

    }
}
