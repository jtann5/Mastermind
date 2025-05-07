package edu.montana.msu.mastermind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    AppData appData;

    public static final String PLAY_NUMBER_OF_PEGS = "PLAY_PEGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = new AppData(this);
        appData.setAppTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = (Button)findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                //intent.putExtra( some extra, value);
                //intent.putExtra(PLAY_NUMBER_OF_PEGS, 4);
                startActivity(intent);
            }
        });

        Button statsButton = (Button)findViewById(R.id.stats_button);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                //intent.putExtra( some extra, value);
                //intent.putExtra(PLAY_NUMBER_OF_PEGS, 4);
                startActivity(intent);
            }
        });

        Button aboutButton = (Button)findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                //intent.putExtra( some extra, value);
                //intent.putExtra(PLAY_NUMBER_OF_PEGS, 4);
                startActivity(intent);
            }
        });

        Button optionsButton = (Button)findViewById(R.id.options_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                //intent.putExtra( some extra, value);
                //intent.putExtra(PLAY_NUMBER_OF_PEGS, 4);
                startActivity(intent);
            }
        });

        Button loveButton = (Button)findViewById(R.id.love_button);
        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer love = MediaPlayer.create(MainActivity.this, R.raw.patrick_love);
                love.start();
            }
        });
        setVisability();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appData.loadPreferences();
        setVisability();
    }

    private void setVisability() {
        Button loveButton = (Button)findViewById(R.id.love_button);
        if (appData.getCoolMode()) {
            loveButton.setVisibility(View.VISIBLE);
        } else {
            loveButton.setVisibility(View.GONE);
        }
    }
}