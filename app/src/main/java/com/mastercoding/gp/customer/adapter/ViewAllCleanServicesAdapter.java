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

public class ViewAllCleanServicesAdapter extends RecyclerView.Adapter<ViewAllCleanServicesAdapter.ViewAllCleanServicesViewHolder> {

    List<ImageService> images;

    ItemClickListener clickListener;

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ViewAllCleanServicesAdapter(List<ImageService> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ViewAllCleanServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_card_item, parent, false);
        return new ViewAllCleanServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllCleanServicesAdapter.ViewAllCleanServicesViewHolder holder, int position) {
        ImageService imageService = images.get(position);
        holder.imageView.setImageResource(imageService.getImage());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewAllCleanServicesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public ViewAllCleanServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivService);
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
