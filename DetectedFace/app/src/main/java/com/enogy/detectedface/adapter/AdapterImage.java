package com.enogy.detectedface.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enogy.detectedface.R;
import com.enogy.detectedface.listener.OnImageListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ImageHolder> {
    private Context context;
    private LayoutInflater inflater;
    private OnImageListener listener;
    private List<Integer> list;
    private int height;

    public AdapterImage(Context context, OnImageListener listener) {
        this.context = context;
        this.listener = listener;
        list = getList();
    }

    private List<Integer> getList(){
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.test);
        list.add(R.drawable.test2);
        list.add(R.drawable.test3);
        list.add(R.drawable.test4);
        list.add(R.drawable.test5);
        return list;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_image, parent, false);
        height = parent.getHeight();
        view.getLayoutParams().width = height;
        view.getLayoutParams().width = height;
        view.requestLayout();
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, final int position) {
        final Integer image = list.get(position);
        Log.e("TAG", "image " + image);
//        Glide.with(context).load(image).into(holder.imgView);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), image);
        holder.imgView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,
                height, height, false));
//        holder.imgView.setImageResource(image);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageClicked(image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private ImageView imgView;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgViewItemImage);
            linearLayout = itemView.findViewById(R.id.linearItemImage);
        }
    }


}
