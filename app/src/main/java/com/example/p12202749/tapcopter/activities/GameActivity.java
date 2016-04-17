package com.example.p12202749.tapcopter.activities;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.example.p12202749.tapcopter.utils.Movement;
import com.example.p12202749.tapcopter.views.GameSurfaceView;

import io.realm.Realm;

public class GameActivity extends AppCompatActivity {

    private GameSurfaceView surfaceView;
    private Realm realm = Realm.getDefaultInstance();


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    protected void onStop() {
        super.onStop();
        realm.close();
    }
}
