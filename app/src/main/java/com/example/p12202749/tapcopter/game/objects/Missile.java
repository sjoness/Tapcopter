package com.example.p12202749.tapcopter.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.p12202749.tapcopter.utils.Animation;

import java.util.Random;

/**
 * Created by sam on 16/04/2016.
 */
public class Missile extends Entity {
    private int speed;

    private final static int BASE_SPEED = 7;
    private final static int MAX_SPEED = 60;
    private final static int NUM_FRAMES = 13;

    public Missile(Bitmap spritesheet, int x, int y, int w, int h, int score) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        // As the score increases (the player is doing well) the speed of the missile increases.
        // This makes the game increasingly difficult.
        speed = BASE_SPEED + (int) (new Random().nextDouble() * score / 30);

        //cap missile speed
        if (speed > MAX_SPEED) speed = MAX_SPEED;

        Bitmap[] image = new Bitmap[NUM_FRAMES];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100 - speed);
    }

    public void update() {
        x -= speed;
        animation.update();
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        } catch (Exception e) {
        }
    }

    @Override
    public int getWidth() {
        // offset slightly for more realistic collision detection,
        // if the tail of the helicopter collides with the tail of a missile,
        // the player should not die.
        return width - 10;
    }
}
