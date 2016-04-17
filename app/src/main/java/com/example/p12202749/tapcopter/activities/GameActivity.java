package com.example.p12202749.tapcopter.activities;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.p12202749.tapcopter.views.GameSurfaceView;
import com.example.p12202749.tapcopter.utils.Movement;

public class GameActivity extends AppCompatActivity {

    private GameSurfaceView surfaceView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the necessary flags to make the activity fullscreen and hide the title/actionbar etc.
        int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
        getSupportActionBar().hide();

        //Get the height and width of the screen
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        Point screenSize = new Point(widthPixels, heightPixels);

        surfaceView = new GameSurfaceView(this, screenSize);
        setContentView(surfaceView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                // finger touches the screen
                if (!surfaceView.getHelicopter().getPlaying() && surfaceView.isNewGameCreated() && surfaceView.isReset()) {
                    surfaceView.getHelicopter().setPlaying(true);
                    Movement.moveY(surfaceView.getHelicopter(), true);
                } else {
                    if (!surfaceView.isStarted()) {
                        surfaceView.setStarted(true);
                    }

                    surfaceView.setReset(false);
                    Movement.moveY(surfaceView.getHelicopter(), true);
                }

                break;
            case MotionEvent.ACTION_UP:
                // finger leaves the screen
                Movement.moveY(surfaceView.getHelicopter(), false);

                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.resume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
