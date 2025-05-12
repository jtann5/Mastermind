package edu.montana.msu.mastermind;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AboutActivity extends AppCompatActivity {
    AppData appData;
    int clickCount = 0;
    Toast currentToast;

    boolean first_time;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = new AppData(this);
        appData.setAppTheme();
        first_time = !appData.getDevMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView versionText = findViewById(R.id.version_text);
        versionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;

                if (clickCount < 10 && !appData.getDevMode()) {
                    int clicksLeft = 10 - clickCount;
                    showToast(clicksLeft + " clicks left to become a developer");
                } else if (clickCount >= 10 && first_time) {
                    appData.setDevMode(true);
                    showToast("You are now a developer");
                } else if (!first_time) {
                    showToast("You are already a developer");
                }
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
    }

    private void showToast(final String message) {
        if(currentToast != null)
        {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(AboutActivity.this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}