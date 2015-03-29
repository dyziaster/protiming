package com.example.darek.protiming;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {


    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Button startButton;
    private Button pauseButton;
    private Button settingsButton;
    private boolean paused;
    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private BluetoothManager manager;
    private Parser parser;
    private Controler controler;
    private Timer t;

    public float getTime() {
        return time;
    }

    private float time;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parser = new Parser(this);
        setContentView(R.layout.main);
        manager = BluetoothManager.getInstance();
        controler = Controler.getInstance();

        if(manager.bluetoothEnabled())
            Toast.makeText(getBaseContext(), "bt", Toast.LENGTH_LONG).show();

        paused = true;
        timerValue = (TextView)findViewById(R.id.timerValue);

        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
              //  manager.runConnectedThread(); // before set bluetooth start must be disabled
                t.startTimer();
            }
        });

        pauseButton = (Button)findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                t.pauseTimer();
                Toast.makeText(getApplicationContext(),t.getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }


    public void startSettings(View view){

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startResults(View view){

        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }


    public void setText(String text) {
        timerValue.setText(text);
    }
}
