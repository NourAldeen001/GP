package com.mastercoding.gp.customer.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.ItemClickListener;
import com.mastercoding.gp.customer.data.ImageService;
import com.mastercoding.gp.customer.data.Service;

import java.util.List;

public class ViewAllTakeAwayServicesAdapter extends RecyclerView.Adapter<ViewAllTakeAwayServicesAdapter.ViewAllTakeAwayServicesViewHolder> {

    List<Service> services;

    OnTakeAwayItemClickListener onTakeAwayItemClickListener;

    public void setOnTakeAwayItemClickListener(OnTakeAwayItemClickListener onTakeAwayItemClickListener) {
        this.onTakeAwayItemClickListener = onTakeAwayItemClickListener;
    }

    public ViewAllTakeAwayServicesAdapter(List<Service> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public ViewAllTakeAwayServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_card_item, parent, false);
        return new ViewAllTakeAwayServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllTakeAwayServicesAdapter.ViewAllTakeAwayServicesViewHolder holder, int position) {
        Service service = services.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(service.getImage())).into(holder.imageView);
        holder.textView.setText(service.getName());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface OnTakeAwayItemClickListener {
        void onTakeAwayItemClick(int serviceId);
    }

    class ViewAllTakeAwayServicesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        Button button;
        public ViewAllTakeAwayServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivService);
            textView = itemView.findViewById(R.id.txtService);
            button = itemView.findViewById(R.id.btnService);
            button.setOnClickListener(this);
        }

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
