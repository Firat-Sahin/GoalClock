package com.firatsahin.goalclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView counterTextView;
    private Button giveUpButton;
    private long startTime;
    private boolean isCounterRunning = false;
    private String addiction;
    private static final String PREFS_NAME = "GoalClockPrefs";
    private static final String START_TIME_KEY = "startTime_";
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        counterTextView = findViewById(R.id.counterTextView);
        giveUpButton = findViewById(R.id.startButton); // Button id remains the same for reference

        addiction = getIntent().getStringExtra("addiction");
        if (addiction == null) {
            addiction = "";
        }

        startTime = sharedPreferences.getLong(START_TIME_KEY + addiction, -1);

        if (startTime != -1) {
            isCounterRunning = true;
            giveUpButton.setText("Give Up");
            updateCounter();
        } else {
            counterTextView.setText("00:00:00");
            giveUpButton.setText("Start");
        }

        giveUpButton.setOnClickListener(v -> {
            if (isCounterRunning) {
                // Reset counter and button text
                counterTextView.setText("00:00:00");
                sharedPreferences.edit().remove(START_TIME_KEY + addiction).apply();
                isCounterRunning = false;
                giveUpButton.setText("Start");

                // Show toast message
                Toast.makeText(MainActivity3.this, "Believe yourself and try again!", Toast.LENGTH_LONG).show();
            } else {
                // Start counter
                startTime = System.currentTimeMillis();
                sharedPreferences.edit().putLong(START_TIME_KEY + addiction, startTime).apply();
                isCounterRunning = true;
                giveUpButton.setText("Give Up");
                updateCounter();
            }
        });
    }

    private void updateCounter() {
        if (isCounterRunning) {
            long elapsedMillis = System.currentTimeMillis() - startTime;
            long hours = (elapsedMillis / (1000 * 60 * 60)) % 24;
            long minutes = (elapsedMillis / (1000 * 60)) % 60;
            long seconds = (elapsedMillis / 1000) % 60;

            counterTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

            handler.postDelayed(this::updateCounter, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCounterRunning) {
            updateCounter();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCounterRunning) {
            sharedPreferences.edit().putLong(START_TIME_KEY + addiction, startTime).apply();
        }
    }
}
