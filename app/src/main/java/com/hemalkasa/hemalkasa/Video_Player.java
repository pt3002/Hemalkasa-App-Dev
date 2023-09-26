package com.hemalkasa.hemalkasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

//import com.bumptech.glide.Glide;

public class Video_Player extends AppCompatActivity {

    public static final String TAG="pratik";
    String path,title;
    PlayerView playerView;
    SimpleExoPlayer player;
    VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        videoView = findViewById(R.id.videoView);
//        playerView = findViewById(R.id.exoplayer_view);

        Intent intent=getIntent();
        if(intent!=null){
            path=intent.getStringExtra("Video");
            title=intent.getStringExtra("Title");
            Log.d(TAG, "Path of video    " + path);
            videoView.setVideoPath(path);
            MediaController mediaController = new MediaController(Video_Player.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    videoView.requestFocus();
                }
            });
        }




    }
}