package com.example.p12202749.tapcopter.game.objects;

import android.graphics.Rect;

import com.example.p12202749.tapcopter.utils.Animation;

/**
 * Created by sam on 16/04/2016.
 */
public abstract class Entity {
    // All objects in the game will conform to this class. They will all have these properties.

    // The class is abstract for the following reasons:
    //
    // Certainly, any class that contains abstract methods must be abstract, however -- it is
    // allowable to declare an abstract class that contains no abstract methods. Here too, the
    // abstract class is not allowed to be instantiated using the new operator Such classes
    // typically serve as base classes for defining new subclasses.
    //
    // Link : http://www.oxfordmathcenter.com/drupal7/node/35
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;
    protected Animation animation = new Animation();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Rect getRect() {
        return new Rect(x, y, x + width, y + height);
    }
}
