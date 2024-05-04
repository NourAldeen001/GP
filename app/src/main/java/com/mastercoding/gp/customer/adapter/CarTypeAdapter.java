package com.mastercoding.gp.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mastercoding.gp.R;

import java.util.List;

public class CarTypeAdapter extends BaseAdapter {

    private Context context;
    private List<String> carTypeList;

    public CarTypeAdapter(Context context, List<String> carTypeList) {
        this.context = context;
        this.carTypeList = carTypeList;
    }

    @Override
    public int getCount() {
        return carTypeList.size();
    }

    @Override
    public String getItem(int position) {
        return carTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car_type, parent, false);
            holder = new ViewHolder();
            holder.carTypeTxt = convertView.findViewById(R.id.carTypetxt);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.carTypeTxt.setText(carTypeList.get(position));

        return convertView;
    }

    static class ViewHolder {
        TextView carTypeTxt;
    }
}
