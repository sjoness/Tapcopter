package com.example.p12202749.tapcopter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.p12202749.tapcopter.R;
import com.example.p12202749.tapcopter.realm.models.Score;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ScoreboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Score> scores = realm.where(Score.class).findAllSorted("value", Sort.DESCENDING);
        TextView currentBestValueLabel = (TextView) findViewById(R.id.current_high_score_value_label);
        TextView currentBestDateLabel = (TextView) findViewById(R.id.current_high_score_date_label);
        TextView previousScoreOneLabel = (TextView) findViewById(R.id.previous_score_one_label);
        TextView previousScoreTwoLabel = (TextView) findViewById(R.id.previous_score_two_label);

        currentBestValueLabel.setText(getString(R.string.scoreboard_current_best_score_value, 0));
        currentBestDateLabel.setText(getString(R.string.scoreboard_current_best_score_date, "N/A"));

        previousScoreOneLabel.setText(getString(R.string.scoreboard_previous_best_score_value_date, 0, "N/A"));
        previousScoreTwoLabel.setText(getString(R.string.scoreboard_previous_best_score_value_date, 0, "N/A"));

        if (scores.size() > 0) {
            Score highestScore = scores.first();
            currentBestValueLabel.setText(getString(R.string.scoreboard_current_best_score_value, highestScore.getValue()));
            currentBestDateLabel.setText(getString(R.string.scoreboard_current_best_score_date, highestScore.getDate()));
        }

        try {
            previousScoreOneLabel.setText(getString(R.string.scoreboard_previous_best_score_value_date, scores.get(1).getValue(), scores.get(1).getDate()));
            previousScoreTwoLabel.setText(getString(R.string.scoreboard_previous_best_score_value_date, scores.get(2).getValue(), scores.get(2).getDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
