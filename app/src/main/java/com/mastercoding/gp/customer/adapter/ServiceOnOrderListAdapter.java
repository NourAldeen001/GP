package com.mastercoding.gp.customer.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.data.ServiceOnOrderList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceOnOrderListAdapter extends RecyclerView.Adapter<ServiceOnOrderListAdapter.ServiceOnOrderListViewHolder> {

    List<ServiceOnOrderList> services;

    OnServiceDeleteItemClickListener onServiceDeleteItemClickListener;

    public ServiceOnOrderListAdapter(List<ServiceOnOrderList> services) {
        this.services = services;
    }

    public void setOnServiceDeleteItemClickListener(OnServiceDeleteItemClickListener onServiceDeleteItemClickListener) {
        this.onServiceDeleteItemClickListener = onServiceDeleteItemClickListener;
    }

    @NonNull
    @Override
    public ServiceOnOrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_on_order_item, parent, false);
        return new ServiceOnOrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceOnOrderListViewHolder holder, int position) {
        ServiceOnOrderList service = services.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(service.getImage())).into(holder.circleImageView);
        holder.serviceNameTxt.setText(service.getServiceName());
        holder.servicePriceTxt.setText(String.format("$ %s", service.getServicePrice()));
        holder.serviceTimeTxt.setText(String.format("%s minute", service.getRequiredTime()));
        if(service.getAvailableInBranch()){
            holder.availableImg.setVisibility(View.INVISIBLE);
        }
        else {
            holder.availableImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface OnServiceDeleteItemClickListener {
        void OnServiceDeleteItemClick(int serviceId);
    }

    class ServiceOnOrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView circleImageView;
        TextView serviceNameTxt, servicePriceTxt, serviceTimeTxt;
        ImageView availableImg;
        ImageButton deleteBtn;
        public ServiceOnOrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.service_on_order_img);
            serviceNameTxt = itemView.findViewById(R.id.service_on_order_name_txt);
            servicePriceTxt = itemView.findViewById(R.id.service_on_order_price_txt);
            serviceTimeTxt = itemView.findViewById(R.id.service_on_order_time_txt);
            availableImg = itemView.findViewById(R.id.service_on_order_available_img);
            deleteBtn = itemView.findViewById(R.id.service_on_order_delete_btn);
            deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onServiceDeleteItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onServiceDeleteItemClickListener.OnServiceDeleteItemClick(services.get(position).getId());
                }
            }
        }
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
