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
import com.mastercoding.gp.customer.data.PackageOnOrderList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PackageOnOrderListAdapter extends RecyclerView.Adapter<PackageOnOrderListAdapter.PackageOnOrderListViewHolder> {

    List<PackageOnOrderList> packages;

    OnPackageDeleteItemClickListener onPackageDeleteItemClickListener;

    public PackageOnOrderListAdapter(List<PackageOnOrderList> packages) {
        this.packages = packages;
    }

    public void setOnPackageDeleteItemClickListener(OnPackageDeleteItemClickListener onPackageDeleteItemClickListener) {
        this.onPackageDeleteItemClickListener = onPackageDeleteItemClickListener;
    }

    @NonNull
    @Override
    public PackageOnOrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_on_order_item, parent, false);
        return new PackageOnOrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageOnOrderListViewHolder holder, int position) {
        PackageOnOrderList aPackage = packages.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(aPackage.getImage())).into(holder.circleImageView);
        holder.serviceNameTxt.setText(aPackage.getPackageName());
        holder.servicePriceTxt.setText(String.format("$ %s", aPackage.getPackagePrice()));
        holder.serviceTimeTxt.setText(String.format("%s minute", aPackage.getRequiredTime()));
        if(aPackage.getAvailableInBranch()){
            holder.availableImg.setVisibility(View.INVISIBLE);
        }
        else {
            holder.availableImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public interface OnPackageDeleteItemClickListener {
        void OnPackageDeleteItemClick(int packageId);
    }

    class PackageOnOrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView circleImageView;
        TextView serviceNameTxt, servicePriceTxt, serviceTimeTxt;
        ImageView availableImg;
        ImageButton deleteBtn;
        public PackageOnOrderListViewHolder(@NonNull View itemView) {
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
            if (onPackageDeleteItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onPackageDeleteItemClickListener.OnPackageDeleteItemClick(packages.get(position).getId());
                }
            }
        }
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}

