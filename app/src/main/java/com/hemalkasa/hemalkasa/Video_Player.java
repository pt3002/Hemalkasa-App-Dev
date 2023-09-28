package com.hemalkasa.hemalkasa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.File;
import java.util.ArrayList;


public class Video_Player extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG="pratik";
    public static final int SWIPE_REQUEST=111;
    String path,title;
    PlayerView playerView;
    SimpleExoPlayer player;
    TextView videoTitle;
    int position;
    ArrayList<Video_ModalClass> mVideoFiles;
    ConcatenatingMediaSource concatenatingMediaSource;

    private ControlsMode controlsMode;

    public enum ControlsMode {
        LOCK, FULLSCREEN
    }

    ImageView videoBack, lock, unlock, scaling,rotateScreen;
    RelativeLayout root;
    ImageView nextButton, previousButton;

    //swipe and zoom variables
    private int device_height, device_width, brightness, media_volume;
    boolean start = false;
    boolean left, right;
    private float baseX, baseY;
    boolean swipe_move = false;
    private long diffX, diffY;
    public static final int MINIMUM_DISTANCE = 100;
    boolean success = false;
    TextView vol_text, brt_text;
    ProgressBar vol_progress, brt_progress;
    LinearLayout vol_progress_container, vol_text_container, brt_progress_container, brt_text_container;
    ImageView vol_icon, brt_icon;
    AudioManager audioManager;
    private ContentResolver contentResolver;
    private Window window;
    boolean singleTap = false;

        // Zoom Gesture
    RelativeLayout zoomLayout;
    RelativeLayout zoomContainer;
    TextView zoom_perc;
    ScaleGestureDetector scaleGestureDetector;
    private float scale_factor = 1.0f;
    boolean double_tap = false;
    RelativeLayout double_tap_playpause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        getSupportActionBar().hide();
        playerView = findViewById(R.id.exoplayer_view);
        videoTitle = findViewById(R.id.video_title);
        nextButton = findViewById(R.id.exo_next);
        previousButton = findViewById(R.id.exo_prev);
        videoBack = findViewById(R.id.video_back);
        lock = findViewById(R.id.lock);
        unlock = findViewById(R.id.unlock);
        rotateScreen = findViewById(R.id.rotateScreen);
        root = findViewById(R.id.root_layout);
        vol_text = findViewById(R.id.vol_text);
        brt_text = findViewById(R.id.brt_text);
        vol_progress = findViewById(R.id.vol_progress);
        brt_progress = findViewById(R.id.brt_progress);
        vol_progress_container = findViewById(R.id.vol_progress_container);
        brt_progress_container = findViewById(R.id.brt_progress_container);
        vol_text_container = findViewById(R.id.vol_text_container);
        brt_text_container = findViewById(R.id.brt_text_container);
        vol_icon = findViewById(R.id.vol_icon);
        brt_icon = findViewById(R.id.brt_icon);
        zoomLayout = findViewById(R.id.zoom_layout);
        zoom_perc = findViewById(R.id.zoom_percentage);
        zoomContainer = findViewById(R.id.zoom_container);
        double_tap_playpause = findViewById(R.id.double_tap_play_pause);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleDetector());

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            // To Easily write the onCLick using switch Case
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        videoBack.setOnClickListener(this);
        lock.setOnClickListener(this);
        unlock.setOnClickListener(this);
        rotateScreen.setOnClickListener(this);


        Intent intent=getIntent();
        if(intent!=null){
            position = getIntent().getIntExtra("position", 1);
            path=intent.getStringExtra("Video");
            title=intent.getStringExtra("Title");
            mVideoFiles = getIntent().getExtras().getParcelableArrayList("videoArrayList");
            Log.d(TAG, "Path of video    " + path);
            playVideo();
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        device_width = displayMetrics.widthPixels;
        device_height = displayMetrics.heightPixels;
        
        playerView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        playerView.showController();
                        start = true;
                        if (motionEvent.getX() < (device_width / 2)) {
                            left = true;
                            right = false;
                        } else if (motionEvent.getX() > (device_width / 2)) {
                            left = false;
                            right = true;
                        }
                        baseX = motionEvent.getX();
                        baseY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        swipe_move = true;
                        diffX = (long) Math.ceil(motionEvent.getX() - baseX);
                        diffY = (long) Math.ceil(motionEvent.getY() - baseY);
                        double brightnessSpeed = 0.01;
                        if (Math.abs(diffY) > MINIMUM_DISTANCE) {
                            start = true;
                            if (Math.abs(diffY) > Math.abs(diffX)) {
                                boolean value;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    value = android.provider.Settings.System.canWrite(getApplicationContext());
                                    if (value) {
                                        if (left) {
                                            contentResolver = getContentResolver();
                                            window = getWindow();
                                            try {
                                                android.provider.Settings.System.putInt(contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                                                        android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                                                brightness = android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS);
                                            } catch (
                                                    android.provider.Settings.SettingNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            int new_brightness = (int) (brightness - (diffY * brightnessSpeed));
                                            if (new_brightness > 250) {
                                                new_brightness = 250;
                                            } else if (new_brightness < 1) {
                                                new_brightness = 1;
                                            }
                                            double brt_percentage = Math.ceil((((double) new_brightness / (double) 250) * (double) 100));
                                            brt_progress_container.setVisibility(View.VISIBLE);
                                            brt_text_container.setVisibility(View.VISIBLE);
                                            brt_progress.setProgress((int) brt_percentage);

                                            if (brt_percentage < 30) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness_low);
                                            } else if (brt_percentage > 30 && brt_percentage < 80) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness_moderate);
                                            } else if (brt_percentage > 80) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness);
                                            }

                                            brt_text.setText(" " + (int) brt_percentage + "%");
                                            android.provider.Settings.System.putInt(contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                                    (new_brightness));
                                            WindowManager.LayoutParams layoutParams = window.getAttributes();
                                            layoutParams.screenBrightness = brightness / (float) 255;
                                            window.setAttributes(layoutParams);
                                        } else if (right) {
                                            vol_text_container.setVisibility(View.VISIBLE);
                                            media_volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                            int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                            double cal = (double) diffY * ((double) maxVol / ((double) (device_height * 2) - brightnessSpeed));
                                            int newMediaVolume = media_volume - (int) cal;
                                            if (newMediaVolume > maxVol) {
                                                newMediaVolume = maxVol;
                                            } else if (newMediaVolume < 1) {
                                                newMediaVolume = 0;
                                            }
                                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                                    newMediaVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                                            double volPer = Math.ceil((((double) newMediaVolume / (double) maxVol) * (double) 100));
                                            vol_text.setText(" " + (int) volPer + "%");
                                            if (volPer < 1) {
                                                vol_icon.setImageResource(R.drawable.ic_volume_off);
                                                vol_text.setVisibility(View.VISIBLE);
                                                vol_text.setText("Off");
                                            } else if (volPer >= 1) {
                                                vol_icon.setImageResource(R.drawable.ic_volume);
                                                vol_text.setVisibility(View.VISIBLE);
                                            }
                                            vol_progress_container.setVisibility(View.VISIBLE);
                                            vol_progress.setProgress((int) volPer);
                                        }
                                        success = true;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Allow write settings for swipe controls", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                        intent.setData(Uri.parse("package:" + getPackageName()));
                                        startActivityForResult(intent, SWIPE_REQUEST);
                                    }
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        swipe_move = false;
                        start = false;
                        vol_progress_container.setVisibility(View.GONE);
                        brt_progress_container.setVisibility(View.GONE);
                        vol_text_container.setVisibility(View.GONE);
                        brt_text_container.setVisibility(View.GONE);
                        break;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                return super.onTouch(view, motionEvent);
            }

            @Override
            public void onDoubleTouch() {
                super.onDoubleTouch();
                Log.d(TAG, "onDoubleTouch: ");
                if (double_tap) {
                    player.setPlayWhenReady(true);
                    double_tap_playpause.setVisibility(View.GONE);
                    double_tap = false;
                } else {
                    player.setPlayWhenReady(false);
                    double_tap_playpause.setVisibility(View.VISIBLE);
                    double_tap = true;
                }
            }

            @Override
            public void onSingleTouch() {
                super.onSingleTouch();
                Log.d(TAG, "onSingleTouch: ");
                if (singleTap) {
                    playerView.showController();
                    singleTap = false;
                } else {
                    playerView.hideController();
                    singleTap = true;
                }
                if (double_tap_playpause.getVisibility() == View.VISIBLE) {
                    double_tap_playpause.setVisibility(View.GONE);
                }
            }
        });


    }

    private void playVideo() {
        String path=mVideoFiles.get(position).getPath();
        videoTitle.setText(mVideoFiles.get(position).getTitle());
        Uri uri=Uri.parse(path);
        player=new SimpleExoPlayer.Builder(this).build();
        DefaultDataSourceFactory dataSourceFactory= new DefaultDataSourceFactory(
                this, Util.getUserAgent(this, "app"));
        concatenatingMediaSource = new ConcatenatingMediaSource();
        for (int i = 0; i < mVideoFiles.size(); i++) {
            new File(String.valueOf(mVideoFiles.get(i)));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(String.valueOf(uri)));
            concatenatingMediaSource.addMediaSource(mediaSource);
        }
        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        player.prepare(concatenatingMediaSource);
        player.seekTo(position, C.TIME_UNSET);
        playError();
    }

    private void playError() {
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Toast.makeText(Video_Player.this, "Video Playing Error", Toast.LENGTH_SHORT).show();
            }
        });
        player.setPlayWhenReady(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(player.isPlaying()){
            player.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_back:
                if (player != null) {
                    player.release();
                }
                finish();
                break;
            case R.id.lock:
                controlsMode = ControlsMode.FULLSCREEN;
                root.setVisibility(View.VISIBLE);
                lock.setVisibility(View.INVISIBLE);
                break;
            case R.id.unlock:
                controlsMode = ControlsMode.LOCK;
                root.setVisibility(View.INVISIBLE);
                lock.setVisibility(View.VISIBLE);
                break;
            case R.id.exo_next:
                try {
                    player.stop();
                    position++;
                    playVideo();
                } catch (Exception e) {
                    Toast.makeText(this, "No Next Video", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.exo_prev:
                try {
                    player.stop();
                    position--;
                    playVideo();
                } catch (Exception e) {
                    Toast.makeText(this, "No Previous Video", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.rotateScreen:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SWIPE_REQUEST) {
            boolean value;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                value = android.provider.Settings.System.canWrite(getApplicationContext());
                if (value) {
                    success = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Not Granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class ScaleDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale_factor *= detector.getScaleFactor();
            scale_factor = Math.max(0.5f, Math.min(scale_factor, 6.0f));

            zoomLayout.setScaleX(scale_factor);
            zoomLayout.setScaleY(scale_factor);
            int percentage = (int) (scale_factor * 100);
            zoom_perc.setText(" " + percentage + "%");
            zoomContainer.setVisibility(View.VISIBLE);

            brt_text_container.setVisibility(View.GONE);
            vol_text_container.setVisibility(View.GONE);
            brt_progress_container.setVisibility(View.GONE);
            vol_progress_container.setVisibility(View.GONE);

            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            zoomContainer.setVisibility(View.GONE);
            super.onScaleEnd(detector);
        }
    }

}