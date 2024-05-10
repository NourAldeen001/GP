package com.mastercoding.gp.parkingworker.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.data.PackageOnOrderList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PackageOnCheckoutAdapter extends RecyclerView.Adapter<PackageOnCheckoutAdapter.PackageOnCheckoutViewHolder> {

    List<PackageOnOrderList> packages;

    public PackageOnCheckoutAdapter(List<PackageOnOrderList> packages) {
        this.packages = packages;
    }

    @NonNull
    @Override
    public PackageOnCheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.service_on_checkout_item, parent, false);
        return new PackageOnCheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageOnCheckoutViewHolder holder, int position) {
        PackageOnOrderList aPackage = packages.get(position);
        Glide.with(holder.itemView.getContext()).load(convertBase64ToBitmap(aPackage.getImage())).into(holder.serviceImg);
        holder.serviceName.setText(aPackage.getPackageName());
        holder.servicePrice.setText(aPackage.getPackagePrice());
        holder.serviceTime.setText(aPackage.getRequiredTime());
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    class PackageOnCheckoutViewHolder extends RecyclerView.ViewHolder{

        CircleImageView serviceImg;
        TextView serviceName, servicePrice, serviceTime;
        public PackageOnCheckoutViewHolder(@NonNull View itemView) {
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
