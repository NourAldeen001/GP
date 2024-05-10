package com.mastercoding.gp.parkingworker.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.data.ServiceOnOrderList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceOnCheckoutAdapter extends RecyclerView.Adapter<ServiceOnCheckoutAdapter.ServiceOnCheckoutViewHolder> {

    List<ServiceOnOrderList> services;

    public ServiceOnCheckoutAdapter(List<ServiceOnOrderList> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public ServiceOnCheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_on_checkout_item, parent, false);
        return new ServiceOnCheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceOnCheckoutViewHolder holder, int position) {
        ServiceOnOrderList service = services.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(service.getImage())).into(holder.serviceImg);
        holder.serviceName.setText(service.getServiceName());
        holder.servicePrice.setText(service.getServicePrice());
        holder.serviceTime.setText(service.getRequiredTime());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class ServiceOnCheckoutViewHolder extends RecyclerView.ViewHolder{

        CircleImageView serviceImg;
        TextView serviceName, servicePrice, serviceTime;
        public ServiceOnCheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceImg = itemView.findViewById(R.id.service_on_checkout_img);
            serviceName = itemView.findViewById(R.id.service_on_checkout_name_txt);
            servicePrice = itemView.findViewById(R.id.service_on_checkout_price_txt);
            serviceTime = itemView.findViewById(R.id.service_on_checkout_time_txt);
        }
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
