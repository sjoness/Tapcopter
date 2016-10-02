package com.example.p12202749.tapcopter.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;

import com.example.p12202749.tapcopter.game.objects.Helicopter;

/**
 * Created by sam on 27/04/2016.
 */
public class DrawUI {
    private final static int TITLE_TEXT_SIZE = 40;
    private final static int SUBTITLE_TEXT_SIZE = 20;
    private final static int SCORE_TEXT_SIZE = 30;

    public static void drawNewGameText(Canvas canvas, Helicopter helicopter, Point screenSize, int best, boolean newGameCreated, boolean reset) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(SCORE_TEXT_SIZE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("DISTANCE: " + (helicopter.getScore() * 3), 10, screenSize.y - 10, paint);
        canvas.drawText("BEST: " + best, screenSize.x - 215, screenSize.y - 10, paint);

        if (!helicopter.getPlaying() && newGameCreated && reset) {
            Paint paint1 = new Paint();
            paint1.setColor(Color.BLACK);
            paint1.setTextSize(TITLE_TEXT_SIZE);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", screenSize.x / 2 - 50, screenSize.y / 2, paint1);

            paint1.setTextSize(SUBTITLE_TEXT_SIZE);
            canvas.drawText("PRESS AND HOLD TO GO UP", screenSize.x / 2 - 50, screenSize.y / 2 + 20, paint1);
            canvas.drawText("RELEASE TO GO DOWN", screenSize.x / 2 - 50, screenSize.y / 2 + 40, paint1);
        }
    }

    public static void drawPauseText(Canvas canvas, Point screenSize) {
        Paint paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setTextSize(TITLE_TEXT_SIZE);
        paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("GAME PAUSED", screenSize.x / 2 - 50, screenSize.y / 2, paint1);

        paint1.setTextSize(SUBTITLE_TEXT_SIZE);
        canvas.drawText("PRESS TO RESUME", screenSize.x / 2 - 50, screenSize.y / 2 + 20, paint1);
    }
}
