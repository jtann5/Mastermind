package edu.montana.msu.mastermind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.content.res.ColorStateList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import android.os.Vibrator;
import java.util.Random;
import java.util.Arrays;
import android.content.Context;
import android.content.SharedPreferences;


public class PlayActivity extends AppCompatActivity {
    AppData appData;
    GameData gameData;
    private int currRow;
    private int pegPlacement[];
    private boolean gameEnded;
    private Chronometer chronometer;
    private long pauseOffset;
    private MediaPlayer mp;
    private SoundPool soundPool;
    private int soundId1; private int soundId2;

    private static final int boardLinearLayoutId[] = {
            R.id.Row0LinearLayout,
            R.id.Row1LinearLayout,
            R.id.Row2LinearLayout,
            R.id.Row3LinearLayout,
            R.id.Row4LinearLayout,
            R.id.Row5LinearLayout,
            R.id.Row6LinearLayout,
            R.id.Row7LinearLayout,
    };
    private static final int boardTextId[] = {
            R.id.PegText_0,
            R.id.PegText_1,
            R.id.PegText_2,
            R.id.PegText_3,
            R.id.PegText_4,
            R.id.PegText_5,
            R.id.PegText_6,
            R.id.PegText_7
    };
    private static final int boardResourceId[][] = {
            {
                R.id.PegImageButton_0_0,
                R.id.PegImageButton_0_1,
                R.id.PegImageButton_0_2,
                R.id.PegImageButton_0_3
            }, {
                R.id.PegImageButton_1_0,
                R.id.PegImageButton_1_1,
                R.id.PegImageButton_1_2,
                R.id.PegImageButton_1_3,
            }, {
                R.id.PegImageButton_2_0,
                R.id.PegImageButton_2_1,
                R.id.PegImageButton_2_2,
                R.id.PegImageButton_2_3
            }, {
                R.id.PegImageButton_3_0,
                R.id.PegImageButton_3_1,
                R.id.PegImageButton_3_2,
                R.id.PegImageButton_3_3
            } , {
                R.id.PegImageButton_4_0,
                R.id.PegImageButton_4_1,
                R.id.PegImageButton_4_2,
                R.id.PegImageButton_4_3
            } , {
                R.id.PegImageButton_5_0,
                R.id.PegImageButton_5_1,
                R.id.PegImageButton_5_2,
                R.id.PegImageButton_5_3
            } , {
                R.id.PegImageButton_6_0,
                R.id.PegImageButton_6_1,
                R.id.PegImageButton_6_2,
                R.id.PegImageButton_6_3
            } , {
                R.id.PegImageButton_7_0,
                R.id.PegImageButton_7_1,
                R.id.PegImageButton_7_2,
                R.id.PegImageButton_7_3
            }
    };
    private static final int boardResultsId[][] = {
            {
                R.id.PegResults_0_0,
                R.id.PegResults_0_1,
                R.id.PegResults_0_2,
                R.id.PegResults_0_3
            }, {
                R.id.PegResults_1_0,
                R.id.PegResults_1_1,
                R.id.PegResults_1_2,
                R.id.PegResults_1_3,
            }, {
                R.id.PegResults_2_0,
                R.id.PegResults_2_1,
                R.id.PegResults_2_2,
                R.id.PegResults_2_3
            }, {
                R.id.PegResults_3_0,
                R.id.PegResults_3_1,
                R.id.PegResults_3_2,
                R.id.PegResults_3_3
            } , {
                R.id.PegResults_4_0,
                R.id.PegResults_4_1,
                R.id.PegResults_4_2,
                R.id.PegResults_4_3
            } , {
                R.id.PegResults_5_0,
                R.id.PegResults_5_1,
                R.id.PegResults_5_2,
                R.id.PegResults_5_3
            } , {
                R.id.PegResults_6_0,
                R.id.PegResults_6_1,
                R.id.PegResults_6_2,
                R.id.PegResults_6_3
            } , {
                R.id.PegResults_7_0,
                R.id.PegResults_7_1,
                R.id.PegResults_7_2,
                R.id.PegResults_7_3
            }
    };

    @Override
    public void onBackPressed() {
        if (!gameEnded) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Trying to leave");
            builder.setMessage("Are you sure you want to exit during a game?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mp == null) {
            mp = MediaPlayer.create(this, appData.getMusicSelectionResource());
            mp.setVolume(appData.getMusicVolume(), appData.getMusicVolume());
            mp.setLooping(true);
        }
        mp.start();

        if (soundPool == null) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(4).build();
            // Deprecated method: soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
            soundId1 = soundPool.load(this, R.raw.bruh, 1);
            soundId2 = soundPool.load(this, R.raw.click, 1);
        }

        // Place in buttons to play sound soundPool.play(soundId1, sfx_volume, sfx_volume, 0, 0, 1.0f);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.pause();
        chronometer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mp != null) {
            mp.release();
            mp = null;
        }
        if (soundPool != null) {
            soundPool.release();
        }

        chronometer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = new AppData(this);
        appData.setAppTheme();
        gameData = new GameData(this);

        currRow = 0;
        pegPlacement = generateGuess();
        gameEnded = false;
        pauseOffset = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        for (int i=0; i< boardResourceId.length; i++) {
            for (int j=0; j<boardResourceId[i].length; j++) {
                final int finalI = i; final int finalJ = j;
                findViewById(boardResourceId[i][j]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playButtonSound();
                        if (appData.getHaptics()) {
                            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            long[] pattern = {0, 100, 50, 0};
                            // Vibrate for 500 milliseconds (0.5 seconds)
                            vibrator.vibrate(pattern, -1);
                        }
                        cycleColor(findViewById(boardResourceId[finalI][finalJ]));
                    }
                });
                findViewById(boardResourceId[i][j]).setClickable(false);
            }
        }
        newRow(0);

        Button guessButton = (Button)findViewById(R.id.guess_button);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameEnded) {
                    checkGuess();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                    builder.setTitle("Game Ended");
                    builder.setMessage("How dare thee try to guess again")
                            .setPositiveButton("I'm lost", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Handle the "OK" button click
                                    onBackPressed();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        Button copyButton = (Button)findViewById(R.id.copy_button);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currRow > 0) copyRow(currRow-1, currRow);
            }
        });
        Button infoButton = (Button)findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
                builder.setTitle("How to play");
                if (appData.getCoolMode()) {
                    builder.setMessage("Tap a peg to switch between colors\n\n" +
                                    "Green indicates that a peg is the correct color and in the correct position\n\n" +
                                    "Red indicates that a peg is the correct color but in the wrong position\n\n" +
                                    "The answer is: " + Arrays.toString(pegPlacement))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                } else {
                    builder.setMessage("Tap a peg to switch between colors\n\n" +
                                    "Green indicates that a peg is the correct color and in the correct position\n\n" +
                                    "Red indicates that a peg is the correct color but in the wrong position")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Button quitButton = (Button)findViewById(R.id.quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button winButton = (Button)findViewById(R.id.win_button);
        winButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < pegPlacement.length; i++) {
                    setColorByInt(findViewById(boardResourceId[currRow][i]), pegPlacement[i]);
                    (findViewById(boardResultsId[currRow][i])).setBackgroundColor(Color.GREEN);
                }
                win();
            }
        });
        Button loseButton = (Button)findViewById(R.id.lose_button);
        loseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lose();
            }
        });
        setVisability();
    }

    private void setVisability() {
        LinearLayout coolButtons = (LinearLayout)findViewById(R.id.cool_buttons);
        if (appData.getCoolMode()) {
            coolButtons.setVisibility(View.VISIBLE);
        } else {
            coolButtons.setVisibility(View.GONE);
        }
        for (int i=0; i<boardLinearLayoutId.length; i++) {
            if (i >= appData.getNumGuesses()) {
                LinearLayout ll = (LinearLayout)findViewById(boardLinearLayoutId[i]);
                ll.setVisibility(View.GONE);
            }
        }
    }

    private void playButtonSound() {
        if (appData.getBruhMode()) {
            soundPool.play(soundId1, appData.getSfxVolume(), appData.getSfxVolume(), 0, 0, 1.0f);
        } else {
            soundPool.play(soundId2, appData.getSfxVolume(), appData.getSfxVolume(), 0, 0, 1.0f);
        }
    }

    private void disableRow(int i) {
        for (int j = 0; j < boardResourceId[i].length; j++) {
            findViewById(boardResourceId[i][j]).setClickable(false);
            ((TextView)findViewById(boardTextId[i])).setTextColor(Color.GRAY);
        }
    }
    private void newRow(int i) {
            if (i > 0) {
                disableRow(i-1);
            }
            for (int j = 0; j < boardResourceId[i].length; j++) {
                findViewById(boardResourceId[i][j]).setClickable(true);
                findViewById(boardResourceId[i][j]).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));

                TypedValue typedValue = new TypedValue();
                getTheme().resolveAttribute(R.attr.colorTextPrimary, typedValue, true);
                ((TextView)findViewById(boardTextId[i])).setTextColor(ContextCompat.getColor(this, typedValue.resourceId));
            }
    }
    private void cycleColor(Button button) {
        ColorStateList color = button.getBackgroundTintList();
        if (getColorAsInt(button) >= appData.getNumColors()) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.red))) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.yellow))) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.blue))) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.green))) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.purple))) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.orange))) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cyan)));
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.cyan))) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }
    }

    private void copyRow(int src, int dest) {
        for (int i = 0; i < boardResourceId[src].length; i++) {
            setColorByInt(findViewById(boardResourceId[dest][i]), getColorAsInt(findViewById(boardResourceId[src][i])));
        }
    }

    private void setColorByInt(Button button, int color) {
        if (color == 1) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }else if (color == 2) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
        } else if (color == 3) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        } else if (color == 4) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else if (color == 5) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
        } else if (color == 6) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        } else if (color == 7) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cyan)));
        } else if (color == 8) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
        }
    }

    private int getColorAsInt(Button button) {
        ColorStateList color = button.getBackgroundTintList();
        if (color == ColorStateList.valueOf(getResources().getColor(R.color.red))) {
            return 1;
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.yellow))) {
            return 2;
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.blue))) {
            return 3;
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.green))) {
            return 4;
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.purple))) {
            return 5;
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.orange))) {
            return 6;
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.cyan))) {
            return 7;
        } else if (color == ColorStateList.valueOf(getResources().getColor(R.color.pink))) {
            return 8;
        } else {
            return 0;
        }
    }

    // 0 none, 1 red, 2 yellow, 3 blue, 4 green, 5 purple, 6 orange, 7 cyan, 8 pink
    private int[] generateGuess() {
        Random random = new Random();
        int guess[] = new int[4];
        for (int i=0; i<4; i++) {
            guess[i] = random.nextInt(appData.getNumColors())+1;
        }
        return guess;
    }

    private int[] getGuess(int i) {
        int pegGuess[] = new int[4];
        for (int j=0; j<4; j++) {
            pegGuess[j] = getColorAsInt(findViewById(boardResourceId[i][j]));
        }
        return pegGuess;
    }
    private void checkGuess() {
        int[] pegGuess = getGuess(currRow);
        int count = 0;
        if (contains(pegGuess, 0)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bruh fr?");
            builder.setMessage("Please make sure all pegs have a color\n")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            for (int i = 0; i < 4; i++) {
                if (pegGuess[i] == pegPlacement[i]) {
                    (findViewById(boardResultsId[currRow][i])).setBackgroundColor(Color.GREEN);
                    count++;
                } else if (contains(pegPlacement, pegGuess[i])) {
                    (findViewById(boardResultsId[currRow][i])).setBackgroundColor(Color.RED);
                } else {
                    (findViewById(boardResultsId[currRow][i])).setBackgroundColor(Color.GRAY);
                }
            }
            if (count == 4) {
                win();
            } else if (currRow == appData.getNumGuesses()-1) {
                lose();
            }else {
                currRow++;
                newRow(currRow);
            }
        }
    }

    private boolean contains(int[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                return true;
            }
        }
        return false;
    }

    private void win() {
        gameEnded = true;
        disableRow(currRow);
        chronometer.stop();
        gameData.addGame(true, (SystemClock.elapsedRealtime() - chronometer.getBase()));

        long elapsedTimeMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        int minutes = (int) (elapsedTimeMillis / 60000);
        int seconds = (int) ((elapsedTimeMillis % 60000) / 1000);
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You win");
        builder.setMessage("Winner winner!!!! Your time is: " + formattedTime)
                .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();
                    }
                })
                .setNegativeButton("Quit to menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the "OK" button click
                        onBackPressed();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void lose() {
        gameEnded = true;
        disableRow(currRow);
        chronometer.stop();
        gameData.addGame(false, (SystemClock.elapsedRealtime() - chronometer.getBase()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You lose");
        builder.setMessage("Well well well, how the turntables... turn")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();
                    }
                })
                .setNegativeButton("Quit to menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the "OK" button click
                        onBackPressed();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}