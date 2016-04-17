package com.example.p12202749.tapcopter.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by sam on 17/04/2016.
 */
public class ScoreAdapter extends RealmBaseAdapter {

    public ScoreAdapter(Context context, RealmResults realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
