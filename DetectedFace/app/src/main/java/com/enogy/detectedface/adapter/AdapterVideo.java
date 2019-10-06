package com.enogy.detectedface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enogy.detectedface.R;
import com.enogy.detectedface.listener.OnVideoListener;

import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.VideoHolder> {
    private LayoutInflater inflater;
    private OnVideoListener listener;
    private Context context;

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_video, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        holder.linearVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtTime;
        private TextView txtDay;
        private LinearLayout linearVideo;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgViewVideo);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDay = itemView.findViewById(R.id.txtDayVideo);
            linearVideo = itemView.findViewById(R.id.linearVideo);
        }
    }
}
