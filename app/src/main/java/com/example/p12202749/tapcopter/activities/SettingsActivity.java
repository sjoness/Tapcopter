package com.example.p12202749.tapcopter.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.p12202749.tapcopter.R;
import com.example.p12202749.tapcopter.realm.models.Score;
import com.example.p12202749.tapcopter.realm.models.Settings;

import io.realm.Realm;

public class SettingsActivity extends AppCompatActivity {

    private Button btnResetScore;
    private Context mContext;
    private SeekBar speedSeekBar;
    private TextView speedSeekBarTitleLabel;
    private Button btnResetSettings;
    private SeekBar missileSpeedSeekBar;
    private TextView missileSpeedSeekBarTitleLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext = this;

        findViews();
        setActions();

        Realm realm = Realm.getDefaultInstance();
        speedSeekBar.setProgress(realm.where(Settings.class).findFirst().getSpeed());
        missileSpeedSeekBar.setProgress(realm.where(Settings.class).findFirst().getDifficulty());
        speedSeekBarTitleLabel.setText(getString(R.string.settings_helicopter_speed_seekbar_title, speedSeekBar.getProgress()));
        missileSpeedSeekBarTitleLabel.setText(getString(R.string.settings_missle_speed_seekbar_title, missileSpeedSeekBar.getProgress()));
    }

    private void findViews() {
        speedSeekBarTitleLabel = (TextView) findViewById(R.id.speed_bar_title_label);
        missileSpeedSeekBarTitleLabel = (TextView) findViewById(R.id.missle_speed_bar_title_label);
        speedSeekBar = (SeekBar) findViewById(R.id.speed_bar);
        missileSpeedSeekBar = (SeekBar) findViewById(R.id.missile_speed_bar);
        btnResetSettings = (Button) findViewById(R.id.reset_settings_button);
        btnResetScore = (Button) findViewById(R.id.reset_score_button);
    }

    private void setActions() {
        btnResetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedSeekBar.setProgress(1);
                missileSpeedSeekBar.setProgress(1);

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Settings settings = realm.where(Settings.class).findFirst();
                settings.setSpeed(1);
                settings.setDifficulty(1);
                realm.commitTransaction();
            }
        });

        btnResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                realm.where(Score.class).findAll().clear();
                                realm.commitTransaction();

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage("Your scores have been reset")
                                        .setNeutralButton("OK", null)
                                        .show();

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete your best scores?")
                        .setTitle("Warning")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    speedSeekBarTitleLabel.setText(getString(R.string.settings_helicopter_speed_seekbar_title, progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int speed = seekBar.getProgress() > 0 ? seekBar.getProgress() : 1;
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Settings settings = realm.where(Settings.class).findFirst();
                settings.setSpeed(speed);
                realm.commitTransaction();
            }
        });

        missileSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    missileSpeedSeekBarTitleLabel.setText(getString(R.string.settings_missle_speed_seekbar_title, progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int speed = seekBar.getProgress() > 0 ? seekBar.getProgress() : 1;
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Settings settings = realm.where(Settings.class).findFirst();
                settings.setDifficulty(speed);
                realm.commitTransaction();
            }
        });
    }
}
