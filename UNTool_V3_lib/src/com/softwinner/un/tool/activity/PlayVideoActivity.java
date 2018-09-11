package com.softwinner.un.tool.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dl7.player.media.IjkPlayerView;
import com.softwinner.un.tool.R;
import com.softwinner.un.tool.util.FileUtils;

import java.io.IOException;

/**
 * Created by Jeremy on 2017/6/2.
 */

public class PlayVideoActivity extends AppCompatActivity {

    private IjkPlayerView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_video);

        String pictureDir = "";
        try {
            pictureDir = FileUtils.getFolderDir(FileUtils.PICTURE_DIR).getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }


        player = (IjkPlayerView) findViewById(R.id.player);
        player.init()
                .setTitle(" ")
                .alwaysFullScreen()   // keep fullscreen
                .setVideoPath(getIntent().getData())
                .setSaveDir(pictureDir)
                .start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.onPause();
    }

    @Override
    protected void onDestroy() {
        player.onDestroy();
        super.onDestroy();
    }
}