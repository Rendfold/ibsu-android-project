package com.example.leo_pc.android_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Game extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        final FrameLayout game = new FrameLayout(this);
        final GameSurface gameView = new GameSurface (this);
        final LinearLayout gameWidgets = new LinearLayout(this);

        Button addCharacter = new Button(this);
        addCharacter.setWidth(300);
        addCharacter.setText("Add character");
        gameWidgets.addView(addCharacter);
        game.addView(gameView);
        game.addView(gameWidgets);


        this.setContentView(game);
        addCharacter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gameView.setChibiList();
            }
        });
    }
}
