package com.example.p12202749.tapcopter.realm.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;

/**
 * Created by sam on 17/04/2016.
 */
public class Score extends RealmObject {
    private int value;
    private String date;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        try {
            Date dateObj = format.parse(date);
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "N/A";
    }

    public void setDate(Date date) {
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(date);
    }
}
