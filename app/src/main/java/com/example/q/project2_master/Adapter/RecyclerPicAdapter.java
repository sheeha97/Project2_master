package com.example.q.project2_master.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.q.project2_master.Activities.GalleryActivity;
import com.example.q.project2_master.R;

import java.io.File;
import java.util.ArrayList;

public class RecyclerPicAdapter extends RecyclerView.Adapter<RecyclerPicAdapter.ImageViewHolder> {

    private ArrayList<String> paths;
    private Context mContext;

    public RecyclerPicAdapter (ArrayList<String> paths, Context context) {
        this.paths = paths;
        mContext = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout, parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);


        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        final String path = this.paths.get(position);

        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.Album.setImageBitmap(myBitmap);
        }

        holder.Album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GalleryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("image_id", position);
                bundle.putStringArrayList("image_path",paths);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                //holder.Album.setImageResource(image_id);
                //holder.pic_name.setText("Image :" + position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.paths.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {

        ImageView Album;

        public ImageViewHolder(View itemView) {
            super(itemView);
            Album = itemView.findViewById(R.id.album);
            //itemView.setClickable(true);
            //itemView.setOnClickListener(this);
        }
    }
}
