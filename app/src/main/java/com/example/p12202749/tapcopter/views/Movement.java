package com.example.p12202749.tapcopter.views;

import com.example.p12202749.tapcopter.game.objects.Helicopter;

/**
 * Created by sam on 16/04/2016.
 */
public class Movement {
    public static void moveY(Helicopter heli, boolean up) {
        heli.setUp(up);
    }
}
