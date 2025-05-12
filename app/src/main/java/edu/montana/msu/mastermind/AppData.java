package edu.montana.msu.mastermind;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class AppData {
    String[] themeOptions = {"Use System Theme", "Light", "Dark"};

    String[] musicOptions = {"Wii", "Never Gonna Give You Up", "Take On Me"};
    int[] musicResource = {R.raw.wii, R.raw.never_gonna_give_you_up, R.raw.take_on_me};

    public static final String SHARED_PREFS = "shared_prefs";

    public static final String APP_THEME = "app_theme";
    private int app_theme;

    public static final String NUM_GUESSES = "num_guesses";
    private int num_guesses;

    public static final String NUM_COLORS = "num_colors";
    private int num_colors;

    public static final String HAPTICS = "haptics";
    private boolean haptics;

    public static final String BRUH_MODE = "bruh_mode";
    private boolean bruh_mode;

    public static final String COOL_MODE = "cool_mode";
    private boolean cool_mode;

    public static final String SFX_VOLUME = "sfx_volume";
    private float sfx_volume;

    public static final String MUSIC_VOLUME = "music_volume";
    private float music_volume;

    public static final String MUSIC_SELECTION = "music_selection";
    private int music_selection;

    public static final String DEV_MODE = "dev_mode";
    private boolean dev_mode;

    public String[] getThemeOptions() {return themeOptions;}
    public String[] getMusicOptions() {return musicOptions;}
    public int[] getMusicResource() {return musicResource;}

    public int getAppTheme() {return app_theme;}
    public void setAppTheme(int val) {this.app_theme = val;}

    public int getNumGuesses() {return num_guesses;}
    public void setNumGuesses(int val) {this.num_guesses = val;}

    public int getNumColors() {return num_colors;}
    public void setNumColors(int val) {this.num_colors = val;}

    public boolean getHaptics() {return haptics;}
    public void setHaptics(boolean val) {this.haptics = val;}

    public boolean getBruhMode() {return bruh_mode;}
    public void setBruhMode(boolean val) {this.bruh_mode = val;}

    public boolean getCoolMode() {return cool_mode;}
    public void setCoolMode(boolean val) {this.cool_mode = val;}

    public float getSfxVolume() {return sfx_volume;}
    public void setSfxVolume(float val) {this.sfx_volume = val;}

    public float getMusicVolume() {return music_volume;}
    public void setMusicVolume(float val) {this.music_volume = val;}

    public boolean getDevMode() {return dev_mode;}
    public void setDevMode(boolean val) {this.dev_mode = val;}

    public int getMusicSelection() {return music_selection;}
    public int getMusicSelectionResource() {return musicResource[music_selection];}
    public void setMusicSelection(int val) {this.music_selection = val;}

    private Context context;
    public void setContext(Context c) {this.context = c;}

    AppData(Context context) {
        this.context = context;
        loadPreferences();
    }
    public void loadPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, context.MODE_PRIVATE);
        app_theme = sharedPreferences.getInt(APP_THEME, 0);
        num_guesses = sharedPreferences.getInt(NUM_GUESSES, 5);
        num_colors = sharedPreferences.getInt(NUM_COLORS, 4);
        haptics = sharedPreferences.getBoolean(HAPTICS, true);
        bruh_mode = sharedPreferences.getBoolean(BRUH_MODE, false);
        cool_mode = sharedPreferences.getBoolean(COOL_MODE, false);
        sfx_volume = sharedPreferences.getFloat(SFX_VOLUME, 0.8f);
        music_volume = sharedPreferences.getFloat(MUSIC_VOLUME, 0.8f);
        music_selection =  sharedPreferences.getInt(MUSIC_SELECTION, 0);
        dev_mode = sharedPreferences.getBoolean(DEV_MODE, false);
    }

    public void savePreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(APP_THEME, app_theme);
        editor.putInt(NUM_GUESSES, num_guesses);
        editor.putInt(NUM_COLORS, num_colors);
        editor.putBoolean(HAPTICS, haptics);
        editor.putBoolean(BRUH_MODE, bruh_mode);
        editor.putBoolean(COOL_MODE, cool_mode);
        editor.putFloat(SFX_VOLUME, sfx_volume);
        editor.putFloat(MUSIC_VOLUME, music_volume);
        editor.putInt(MUSIC_SELECTION, music_selection);
        editor.putBoolean(DEV_MODE, dev_mode);
        editor.apply();
    }

    public void deletePreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        loadPreferences();
    }

    public void setAppTheme() {
        int currTheme = AppCompatDelegate.getDefaultNightMode();
        switch (app_theme) {
            case 0: // Use System Default
                if (currTheme != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                }
                break;
            case 1: // Light
                if (currTheme != AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                break;
            case 2: // Dark
                if (currTheme != AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                break;
        }
    }
}
