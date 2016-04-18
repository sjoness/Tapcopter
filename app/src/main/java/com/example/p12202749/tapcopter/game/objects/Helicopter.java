package com.example.p12202749.tapcopter.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.p12202749.tapcopter.realm.models.Settings;
import com.example.p12202749.tapcopter.utils.Animation;
import com.example.p12202749.tapcopter.utils.Collision;

import io.realm.Realm;

/**
 * Created by sam on 16/04/2016.
 */
public class Helicopter extends Entity {
    private int score;
    private boolean up;
    private boolean playing;
    private long startTime;
    private int heliSpeed;

    private final static int NUM_FRAMES = 3;

    public Helicopter(Bitmap spritesheet, int w, int h, Point screenSize) {
        x = 100;
        y = screenSize.x / 2;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[NUM_FRAMES];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

        heliSpeed = Realm.getDefaultInstance().where(Settings.class).findFirst().getSpeed();
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void update() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;

        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }

        animation.update();

        if (up) {
            if (!Collision.withTop(this)) {
                dy -= heliSpeed;
            }
        } else {
            dy += 1;
        }

        if (dy > 14) dy = 14;
        if (dy < -14) dy = -14;

        y += dy * 2;
        dy = 0;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public int getScore() {
        return score;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void resetDY() {
        dy = 0;
    }

    public void resetScore() {
        score = 0;
    }
}
