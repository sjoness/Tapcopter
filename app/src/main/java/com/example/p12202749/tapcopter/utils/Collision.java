package com.example.p12202749.tapcopter.utils;

import android.graphics.Point;
import android.graphics.Rect;

import com.example.p12202749.tapcopter.game.objects.Entity;

/**
 * Created by sam on 16/04/2016.
 */
public class Collision {
    public static boolean checkRectIntersection(Entity a, Entity b) {
        return Rect.intersects(a.getRect(), b.getRect());
    }

    public static boolean withTop(Entity a) {
        return a.getY() < 0;
    }

    public static boolean withBottom(Entity a, Point screenSize) {
        return a.getY() > screenSize.y - 60;
    }
}
