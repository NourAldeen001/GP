package com.mastercoding.gp.customer.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.data.ServiceOnOrderList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceOnOrderStatusAdapter extends RecyclerView.Adapter<ServiceOnOrderStatusAdapter.ServiceOnOrderStatusViewHolder>{

    List<ServiceOnOrderList> services;

    public ServiceOnOrderStatusAdapter(List<ServiceOnOrderList> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public ServiceOnOrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_order_status, parent, false);
        return new ServiceOnOrderStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceOnOrderStatusViewHolder holder, int position) {
        ServiceOnOrderList service = services.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(service.getImage())).into(holder.circleImageView);
        holder.serviceNameTxt.setText(service.getServiceName());
        holder.servicePriceTxt.setText(String.format("$ %s", service.getServicePrice()));
        holder.serviceTimeTxt.setText(String.format("%s minute", service.getRequiredTime()));
        switch (service.getProgressStatus()) {
            case "WAITING":
                holder.serviceStatusImg.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.waiting_icon));
                break;
            case "ON_WORKING":
                holder.serviceStatusImg.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.onworking_icon));
                break;
            case "FINISHED":
                holder.serviceStatusImg.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.complete_icon));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class ServiceOnOrderStatusViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView serviceNameTxt, servicePriceTxt, serviceTimeTxt;
        ImageView serviceStatusImg;
        public ServiceOnOrderStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.item_order_status_service_img);
            serviceNameTxt = itemView.findViewById(R.id.item_order_status_name_txt);
            servicePriceTxt = itemView.findViewById(R.id.item_order_status_price_txt);
            serviceTimeTxt = itemView.findViewById(R.id.item_order_status_time_txt);
            serviceStatusImg = itemView.findViewById(R.id.item_order_status_img);
        }
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
