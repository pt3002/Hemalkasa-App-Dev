package com.hemalkasa.hemalkasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Video_MainScreen extends AppCompatActivity {

    public static final String TAG="pratik";
    public static final int FOLDER_REQUEST_CODE=101;
    boolean permissonGranted;
    RecyclerView videoRecycler;
    ArrayList<Video_ModalClass> videoArray;
    Video_Adapter videoAdapter;
    TextView EmptyList;
    ConstraintLayout ActivityView;
    private Button NextButton,BackButton;
    private boolean pendingTransaction = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_main_screen);
        EmptyList=findViewById(R.id.EmptyList);
        NextButton = findViewById(R.id.NextButton);
        BackButton = findViewById(R.id.BackButton);

        if(permissonGranted){
            checkPermission();
        }
        videoArray=new ArrayList<Video_ModalClass>();
        videoRecycler=findViewById(R.id.VideoRecycler);
        videoRecycler.setHasFixedSize(true);
        videoRecycler.setLayoutManager(new LinearLayoutManager(Video_MainScreen.this));
        getVideos();


        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
//                ActivityOptions options = ActivityOptions.makeCustomAnimation(Video_MainScreen.this,R.anim.slide_from_left, R.anim.slide_to_right );
                Intent intent = new Intent(Video_MainScreen.this, Trimester.class);
//                pendingTransaction = false;
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
//                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
//                startActivity(intent, options.toBundle());
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityOptions options = ActivityOptions.makeCustomAnimation(Video_MainScreen.this,R.anim.slide_from_right, R.anim.slide_to_left);
                Intent intent = new Intent(Video_MainScreen.this, Emergency_Contact.class);
                startActivity(intent);
//                startActivity(intent, options.toBundle());
            }
        });

        ActivityView = findViewById(R.id.ActivityView);
        //noinspection AndroidLintClickableViewAccessibility
        ActivityView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                Log.d(TAG, "RIGHTTTTT: ");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(Video_MainScreen.this,R.anim.slide_from_right, R.anim.slide_to_left );
                Intent intent = new Intent(Video_MainScreen.this, Emergency_Contact.class);
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Log.d(TAG, "LEFTTTTTTT: ");
                ActivityOptions options = ActivityOptions.makeCustomAnimation(Video_MainScreen.this,R.anim.slide_from_left, R.anim.slide_to_right);
                Intent intent = new Intent(Video_MainScreen.this, Trimester.class);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void getVideos() {
        videoArray.clear();
        File path=getExternalFilesDir("Videos");
        Log.d(TAG, "Path Directory " + path.getAbsolutePath());
        if(!path.exists()){
            path.mkdir();
        }
        File[] files=path.listFiles();
        if(files!=null){
            for(File file:files){
                if(file.getPath().endsWith(".mp4")){
                    videoArray.add(new Video_ModalClass(file.getPath(),file.getName()));
                }
            }
        }

        videoAdapter=new Video_Adapter(Video_MainScreen.this, videoArray);
        videoRecycler.setAdapter(videoAdapter);
        if(videoArray.isEmpty()){
            EmptyList.setVisibility(View.VISIBLE);
        }
        else{
            EmptyList.setVisibility(View.GONE);
        }
    }

    private String readFromFile(File file) {
        FileInputStream fis=null;
        int read;
        StringBuilder sb=new StringBuilder();
        try{
            fis=new FileInputStream(file);
            while((read=fis.read())!=-1){
                sb.append((char)read);
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
            Log.d(TAG, exception.getMessage());
        }
        finally {
            if(fis!=null){
                try {
                    fis.close();
                }
                catch (Exception exception){
                    Log.d(TAG, exception.getMessage());
                }
            }
        }
        return sb.toString();
    }

    private void writeToFile(File file, String string){
        FileOutputStream fos=null;
        try{
            fos=new FileOutputStream(file);
            fos.write(string.getBytes());
            Log.d(TAG, "Wriiten " + string + file.getName() + "####" + file.getPath());
        }
        catch (Exception exception){
            Log.d(TAG,"1 :" +  exception.getMessage());
        }
        finally {
            if(fos!=null){
                try{
                    fos.close();
                }
                catch (Exception e){
                    Log.d(TAG, "2 :" + e.getMessage());
                }
            }
        }
    }

    private boolean checkPermission(){
        if(!isExternalStorageReadable() || !isExternalStorageWriteable()){
            Log.d(TAG, "This works only on usable external device");
            return false;
        }

        int permissionCheck=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE );
        if(permissionCheck==PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , FOLDER_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private boolean isExternalStorageWriteable() {
        String state=Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ;
    }

    private boolean isExternalStorageReadable() {
        String state=Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case FOLDER_REQUEST_CODE:
                if(grantResults.length>0 &&
                grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    permissonGranted=true;
                    Log.d(TAG, "External Storage Permission granted");
                }
                else{
                    Log.d(TAG, "Denied");
                    Toast.makeText(this, "Please Grant Permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                            , FOLDER_REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
//        if (pendingTransaction) {
//            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVideos();
//        pendingTransaction = true;
    }
}