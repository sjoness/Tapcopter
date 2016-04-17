package com.example.p12202749.tapcopter.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.p12202749.tapcopter.views.GameSurfaceView;

/**
 * Created by sam on 17/04/2016.
 */
public class Border extends Entity {
    private Bitmap image;

    public Border(Bitmap res, int x, int y, int h, Boolean isTop) {
        height = isTop ? h : 200;
        width = 20;

        this.x = x;
        this.y = y;

        dx = GameSurfaceView.MOVE_SPEED;
        image = Bitmap.createBitmap(res, 0, 0, width, height);
    }

    public void update() {
        x += dx;
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(image, x, y, null);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
