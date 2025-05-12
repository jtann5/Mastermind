package edu.montana.msu.mastermind;

import android.content.Context;
import android.content.SharedPreferences;

public class GameData {
    public static final String GAME_DATA = "game_data";
    public static final String NUM_GAMES = "num_games";
    public static final String NUM_WINS = "num_wins";
    public static final String BEST_TIME = "best_time";
    public static final String TOTAL_TIME  = "total_time";
    private int num_games;
    private int num_wins;
    private long best_time;
    private long total_time;
    private Context context;
    public void setContext(Context c) {this.context = c;}

    GameData(Context context) {
        this.context = context;

        loadData();
    }

    public int getNumGames() {return num_games;}
    public int getNumWins() {return num_wins;}
    public long getBestTime() {return best_time;}
    public long getTotalTime() {return total_time;}

    public void saveGame() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_DATA, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUM_GAMES, num_games);
        editor.putInt(NUM_WINS, num_wins);
        editor.putLong(BEST_TIME, best_time);
        editor.putLong(TOTAL_TIME, total_time);
        editor.apply();
    }

    public void addGame(boolean win, long time) {
        if (win) {
            num_wins++;
            if (best_time > time || best_time == 0) best_time = time;
        }
        num_games++;
        total_time+= time;

        saveData();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_DATA, context.MODE_PRIVATE);
        num_games = sharedPreferences.getInt(NUM_GAMES, 0);
        num_wins = sharedPreferences.getInt(NUM_WINS, 0);
        best_time = sharedPreferences.getLong(BEST_TIME, 0);
        total_time = sharedPreferences.getLong(TOTAL_TIME, 0);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_DATA, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUM_GAMES, num_games);
        editor.putInt(NUM_WINS, num_wins);
        editor.putLong(BEST_TIME, best_time);
        editor.putLong(TOTAL_TIME, total_time);
        editor.apply();
    }

    public void deleteData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_DATA, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        loadData();
    }
}
