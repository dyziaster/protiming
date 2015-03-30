package com.example.darek.protiming;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {


    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
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
            Toast.makeText(getBaseContext(), "bluetooth is enabled", Toast.LENGTH_LONG).show();

        paused = true;
        timerValue = (TextView)findViewById(R.id.timerValue);
        initButtons();

        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

    }

    private void initButtons(){


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

        if(false){

            controler.discoverDevice();
            controler.pairDevice();
            controler.connectDevice();

        }

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


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {



        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                controler.add(device);
                Toast.makeText(getApplicationContext(), "adding device", Toast.LENGTH_SHORT).show();
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if(controler.findHC05()){
                    Toast.makeText(getApplicationContext(), "device is visible", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "could not find the device", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "finished discovery", Toast.LENGTH_LONG).show();
            }
        }
    };
}
