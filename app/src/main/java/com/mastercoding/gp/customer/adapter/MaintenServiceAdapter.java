package com.mastercoding.gp.customer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.ItemClickListener;
import com.mastercoding.gp.customer.data.ImageService;

import java.util.List;

public class MaintenServiceAdapter extends RecyclerView.Adapter<MaintenServiceAdapter.MaintenServiceViewHolder> {

    List<ImageService> images;

    ItemClickListener clickListener;

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MaintenServiceAdapter(List<ImageService> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public MaintenServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_layout, parent, false);
        return new MaintenServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenServiceViewHolder holder, int position) {
        ImageService imageService = images.get(position);
        holder.imageView.setImageResource(imageService.getImage());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class MaintenServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public MaintenServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.service_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null){
                clickListener.onClick(images.get(getLayoutPosition()));
            }
        }
    }
}

