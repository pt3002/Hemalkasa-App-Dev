package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

public class Video_Player extends AppCompatActivity {

    public static final String TAG="pratik";
    String path;
    VideoView videoView;
    ImageButton pausePlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);

        videoView = findViewById(R.id.videoView);
        pausePlay = findViewById(R.id.pausePlay);

        Intent intent=getIntent();
        if(intent!=null){
            path=intent.getStringExtra("Video");
            Log.d(TAG, "Path of video    " + path);
            videoView.setVideoPath(path);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    pausePlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(videoView.isPlaying()){
                                videoView.pause();
                                Glide.with(Video_Player.this).load(R.drawable.ic_baseline_play_circle_24).into(pausePlay);
                            }
                            else {
                                videoView.resume();
                                Glide.with(Video_Player.this).load(R.drawable.ic_baseline_pause_circle_24).into(pausePlay);
                            }
                        }
                    });
                }
            });
        }




    }
}