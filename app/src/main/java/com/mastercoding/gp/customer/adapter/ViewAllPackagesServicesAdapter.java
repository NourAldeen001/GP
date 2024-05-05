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
import com.mastercoding.gp.customer.data.Package;

import java.util.List;

public class ViewAllPackagesServicesAdapter extends RecyclerView.Adapter<ViewAllPackagesServicesAdapter.ViewAllPackagesServicesViewHolder> {

    List<Package> packages;

    OnPackageItemClickListener onPackageItemClickListener;

    public void setOnPackageItemClickListener(OnPackageItemClickListener onPackageItemClickListener) {
        this.onPackageItemClickListener = onPackageItemClickListener;
    }

    public ViewAllPackagesServicesAdapter(List<Package> packages) {
        this.packages = packages;
    }

    @NonNull
    @Override
    public ViewAllPackagesServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_card_item, parent, false);
        return new ViewAllPackagesServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllPackagesServicesAdapter.ViewAllPackagesServicesViewHolder holder, int position) {
        Package aPackage = packages.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(aPackage.getImage())).into(holder.imageView);
        holder.textView.setText(aPackage.getName());
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public interface OnPackageItemClickListener {
        void onPackageItemClick(int packageId);
    }

    class ViewAllPackagesServicesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        Button button;
        public ViewAllPackagesServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivService);
            textView = itemView.findViewById(R.id.txtService);
            button = itemView.findViewById(R.id.btnService);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onPackageItemClickListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    onPackageItemClickListener.onPackageItemClick(packages.get(position).getId());
                }
            }
        }
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
