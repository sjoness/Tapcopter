package com.example.p12202749.tapcopter.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.p12202749.tapcopter.views.GameSurfaceView;

/**
 * Created by sam on 16/04/2016.
 */
public class Background {
    private Point screenSize;
    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res, Point screenSize) {
        image = res;
        dx = GameSurfaceView.MOVE_SPEED;
        this.screenSize = screenSize;
    }

    public void update() {
        x += dx;

        if (x < -screenSize.x) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

        if (x < 0) {
            canvas.drawBitmap(image, x + screenSize.x, y, null);
        }
    }
}
