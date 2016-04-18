package com.example.p12202749.tapcopter.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.p12202749.tapcopter.utils.Animation;

/**
 * Created by sam on 17/04/2016.
 */
public class Explosion extends Entity {
    private final static int NUM_FRAMES = 12;

    public Explosion(Bitmap spritesheet, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        Bitmap[] image = new Bitmap[NUM_FRAMES];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
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

    @Override
    public int getHeight() {
        return height;
    }
}
