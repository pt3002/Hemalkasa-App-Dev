package com.hemalkasa.hemalkasa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.Viewholder> {

    public static final String TAG="pratik";
    Context context;
    ArrayList<Video_ModalClass> arrayList;
    OnItemClickListener listener;

    public Video_Adapter(Context context, ArrayList<Video_ModalClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.video_card_item, parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Video_ModalClass video=arrayList.get(position);
        Glide.with(context).load(video.getPath()).into(holder.imageView);
        holder.title.setText(new File(video.getPath()).getName());
        try{
            MediaPlayer mediaPlayer= MediaPlayer.create(context, Uri.parse(video.getPath()));
            holder.duration.setText(getDuration(mediaPlayer.getDuration()));
        }catch (Exception exception){
            exception.printStackTrace();
            Log.d(TAG, exception.getMessage());
            holder.duration.setText("--:--");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    String title=new File(video.getPath()).getName();
                    listener.playVideo(v, video.getPath(),title);
                }
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private String getDuration(int duration) {
        String videoDuration;

        int dur = duration;
        int hrs = (dur / 3600000);
        int min = (dur / 60000) % 6000;
        int sec = (dur % 60000 / 1000);

        if (hrs > 0) {
            videoDuration = String.format("%02d hrs, %02d min, %02d sec", hrs, min, sec);
        } else if (min > 0) {
            videoDuration = String.format("%02d min, %02d sec", min, sec);
        } else {
            videoDuration = String.format("%02d sec", sec);
        } return videoDuration;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title, duration;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ThumbNail);
            title = itemView.findViewById(R.id.Title);
            duration = itemView.findViewById(R.id.Duration);
        }
    }

    public interface OnItemClickListener{
        void playVideo(View view,String path,String title);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
