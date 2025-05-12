package edu.montana.msu.mastermind;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class StatsActivity extends AppCompatActivity {
    AppData appData;
    GameData gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = new AppData(this);
        appData.setAppTheme();
        gameData = new GameData(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        TextView numGames = (TextView)findViewById(R.id.NumGames);
        numGames.setText(Integer.toString(gameData.getNumGames()));

        TextView numWins = (TextView)findViewById(R.id.NumWins);
        numWins.setText(Integer.toString(gameData.getNumWins()));

        Chronometer bestTime = (Chronometer)findViewById(R.id.BestTime);
        bestTime.setBase(SystemClock.elapsedRealtime() - gameData.getBestTime());

        Chronometer totalTime = (Chronometer)findViewById(R.id.TotalTime);
        totalTime.setBase(SystemClock.elapsedRealtime() - gameData.getTotalTime());

        Button exitButton = (Button)findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appData.savePreferences();
                onBackPressed();
            }
        });
    }
}