package com.mastercoding.gp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder>{

    int[] images = {
            R.drawable.intro1,
            R.drawable.intro2,
            R.drawable.intro3 };

    int[] descriptions = {
            R.string.intro1_desc,
            R.string.intro2_desc,
            R.string.intro3_desc };

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.slides_layout, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.textView.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return descriptions.length;
    }

    static class SlideViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slide_image);
            textView = itemView.findViewById(R.id.slide_desc);
        }
    }
}

