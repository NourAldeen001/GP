package com.mastercoding.gp.shareddata.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mastercoding.gp.R;
import com.mastercoding.gp.shareddata.data.Notification;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    List<Notification> notifications;

    OnNotificationItemClickListener onNotificationItemClickListener;

    public NotificationsAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setOnNotificationItemClickListener(OnNotificationItemClickListener onNotificationItemClickListener) {
        this.onNotificationItemClickListener = onNotificationItemClickListener;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_item_layout, parent, false);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.notificationTitle.setText(notification.getNotificationTitle());
        holder.notificationContent.setText(notification.getNotificationContent());

        switch(notification.getStatus()) {
            case "OPENED":
                holder.notificationStatusImg.setVisibility(View.INVISIBLE);
                break;
            case "NOT_OPENED":
                holder.notificationStatusImg.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public interface OnNotificationItemClickListener {
        void OnNotificationDeleteItemClick(int notificationId);
        void OnNotificationItemClick(int notificationId);
    }

    class NotificationsViewHolder extends RecyclerView.ViewHolder{
        ImageView notificationStatusImg;
        TextView notificationTitle, notificationContent;
        ImageButton notificationDeleteBtn;
        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationStatusImg = itemView.findViewById(R.id.notification_open_status_img);
            notificationTitle = itemView.findViewById(R.id.notification_title_txt);
            notificationContent = itemView.findViewById(R.id.notification_content_txt);
            notificationDeleteBtn = itemView.findViewById(R.id.notification_delete_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNotificationItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onNotificationItemClickListener.OnNotificationItemClick(notifications.get(position).getId());
                        }
                    }
                }
            });

            notificationDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNotificationItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onNotificationItemClickListener.OnNotificationDeleteItemClick(notifications.get(position).getId());
                        }
                    }
                }
            });

        }
    }
}
