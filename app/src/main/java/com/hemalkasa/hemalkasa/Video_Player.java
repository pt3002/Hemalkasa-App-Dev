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

//import com.bumptech.glide.Glide;

public class Video_Player extends AppCompatActivity {

    public static final String TAG="pratik";
    String path;
    VideoView videoView;
    ImageButton share_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        share_btn = findViewById(R.id.shareBtn);
        videoView = findViewById(R.id.videoView);

        Intent intent=getIntent();
        if(intent!=null){
            path=intent.getStringExtra("Video");
            Log.d(TAG, "Path of video    " + path);
            videoView.setVideoPath(path);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    MediaController mediaController = new MediaController(Video_Player.this);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    share_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(path);
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("video/*");
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            startActivity(Intent.createChooser(shareIntent, "Share Via "));
                        }
                    });
                }
            });
        }




    }
}