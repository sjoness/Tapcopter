package com.example.p12202749.tapcopter.realm.models;

import io.realm.RealmObject;

/**
 * Created by sam on 17/04/2016.
 */
public class Settings extends RealmObject {
    private int speed;
    private int difficulty;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
