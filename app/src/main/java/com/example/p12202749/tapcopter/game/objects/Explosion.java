package com.example.p12202749.tapcopter.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.p12202749.tapcopter.utils.Animation;

/**
 * Created by sam on 17/04/2016.
 */
public class Explosion extends Entity {
    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    private final static int NUM_FRAMES = 25;

    public Explosion(Bitmap res, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        Bitmap[] image = new Bitmap[NUM_FRAMES];

        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            if (i % 5 == 0 && i > 0) row++;
            image[i] = Bitmap.createBitmap(spritesheet, (i - (5 * row)) * width, row * height, width, height);
        }
        animation.setFrames(image);
        animation.setDelay(10);


    }

    public void draw(Canvas canvas) {
        if (!animation.playedOnce()) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }

    }

    public void update() {
        if (!animation.playedOnce()) {
            animation.update();
        }
    }

    public int getHeight() {
        return height;
    }
}
