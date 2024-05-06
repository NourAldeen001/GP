package com.mastercoding.gp.customer.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.data.Service;

import java.util.List;

public class TakeAwayServiceAdapter extends RecyclerView.Adapter<TakeAwayServiceAdapter.TakeAwayServiceViewHolder> {

    List<Service> services;

    OnTakeAwayItemClickListener onTakeAwayItemClickListener;

    public void setOnTakeAwayItemClickListener(OnTakeAwayItemClickListener onTakeAwayItemClickListener) {
        this.onTakeAwayItemClickListener = onTakeAwayItemClickListener;
    }

    public TakeAwayServiceAdapter(List<Service> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public TakeAwayServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_layout, parent, false);
        return new TakeAwayServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeAwayServiceViewHolder holder, int position) {
        Service service = services.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(service.getImage())).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface OnTakeAwayItemClickListener {
        void onTakeAwayItemClick(int serviceId);
    }

    class TakeAwayServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public TakeAwayServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.service_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onTakeAwayItemClickListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    onTakeAwayItemClickListener.onTakeAwayItemClick(services.get(position).getId());
                }
            }
        }
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}

