package edu.montana.msu.mastermind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Arrays;


public class OptionsActivity extends AppCompatActivity {
    AppData appData;
    GameData gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = new AppData(this);
        appData.setAppTheme();
        gameData = new GameData(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, appData.getThemeOptions());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner themeSpinner = findViewById(R.id.theme_spinner);
        themeSpinner.setAdapter(adapter);
        themeSpinner.setSelection(appData.getAppTheme());
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0: // Use System Default
                        appData.setAppTheme(0);
                        break;
                    case 1: // Light
                        appData.setAppTheme(1);
                        break;
                    case 2: // Dark
                        appData.setAppTheme(2);
                        break;
                }
                appData.savePreferences();
                appData.setAppTheme();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        Switch hapticsSwitch = findViewById(R.id.haptics_switch);
        hapticsSwitch.setChecked(appData.getHaptics());
        hapticsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appData.setHaptics(true);
                } else {
                    appData.setHaptics(false);
                }
                appData.savePreferences();
            }
        });

        Switch bruhSwitch = findViewById(R.id.bruh_switch);
        bruhSwitch.setChecked(appData.getBruhMode());
        bruhSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appData.setBruhMode(true);
                } else {
                    appData.setBruhMode(false);
                }
                appData.savePreferences();
            }
        });

        Switch coolSwitch = findViewById(R.id.cool_switch);
        coolSwitch.setChecked(appData.getCoolMode());
        coolSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appData.setCoolMode(true);
                } else {
                    appData.setCoolMode(false);
                }
                appData.savePreferences();
            }
        });

        SeekBar sfxSeekBar = findViewById(R.id.sfx_seekBar);
        sfxSeekBar.setProgress((int)(appData.getSfxVolume()*100));
        sfxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float maxVolume = 1.0f;
                appData.setSfxVolume((float) progress / seekBar.getMax() * maxVolume);
                appData.savePreferences();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts dragging the SeekBar
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user stops dragging the SeekBar
            }
        });

        SeekBar musicSeekBar = findViewById(R.id.music_seekBar);
        musicSeekBar.setProgress((int)(appData.getMusicVolume()*100));
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float maxVolume = 1.0f;
                appData.setMusicVolume((float) progress / seekBar.getMax() * maxVolume);
                appData.savePreferences();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts dragging the SeekBar
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user stops dragging the SeekBar
            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, appData.getMusicOptions());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner musicSpinner = findViewById(R.id.music_spinner);
        musicSpinner.setAdapter(adapter1);
        musicSpinner.setSelection(appData.getMusicSelection());
        musicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                appData.setMusicSelection(position);
                appData.savePreferences();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        String[] numbers = {"4", "5", "6", "7", "8"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbers);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner guessesSpinner = findViewById(R.id.guesses_spinner);
        guessesSpinner.setAdapter(adapter2);
        guessesSpinner.setSelection(appData.getNumGuesses()-4);
        guessesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                appData.setNumGuesses(Integer.parseInt(numbers[position]));
                appData.savePreferences();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbers);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner colorsSpinner = findViewById(R.id.colors_spinner);
        colorsSpinner.setAdapter(adapter3);
        colorsSpinner.setSelection(appData.getNumColors()-4);
        colorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                appData.setNumColors(Integer.parseInt(numbers[position]));
                appData.savePreferences();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        Button resetButton = (Button)findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appData.deletePreferences();
                gameData.deleteData();

                Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        Button exitButton = (Button)findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appData.savePreferences();
                onBackPressed();
            }
        });

        setVisability();
    }

    private void setVisability() {
        Button resetButton = (Button)findViewById(R.id.reset_button);
        Switch coolSwitch = findViewById(R.id.cool_switch);
        if (appData.getDevMode()) {
            resetButton.setVisibility(View.VISIBLE);
            coolSwitch.setVisibility(View.VISIBLE);
        } else {
            resetButton.setVisibility(View.GONE);
            coolSwitch.setVisibility(View.GONE);
        }
    }
}