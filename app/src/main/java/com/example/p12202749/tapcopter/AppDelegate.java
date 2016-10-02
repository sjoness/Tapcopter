package com.example.p12202749.tapcopter;

import android.app.Application;

import com.example.p12202749.tapcopter.realm.models.Settings;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sam on 17/04/2016.
 */
public class AppDelegate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Realm realm = Realm.getDefaultInstance();
        Settings settings = realm.where(Settings.class).findFirst();

        if (settings == null) {
            realm.beginTransaction();
            settings = realm.createObject(Settings.class);
            settings.setSpeed(1);
            settings.setDifficulty(1);
            realm.commitTransaction();
        }
    }
}
