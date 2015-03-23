package com.example.darek.protiming;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by Darek on 2015-03-21.
 */
public class SettingsActivity extends Activity{
    private Button discoverButton;
    private BluetoothManager manager;
    private Set<BluetoothDevice> devices;

    @Override
    public void onCreate(Bundle savedInstanceState) { // must enable BT first
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        manager = new BluetoothManager();

        if(!manager.bluetoothEnabled()){
            Log.i("SettingsActivity","bluetooth turned off");
            return;
        }

        discoverButton = (Button)findViewById(R.id.discoverButton);
        discoverButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                devices = manager.getBondedDevices();
                Toast.makeText(getApplicationContext(), "discovering", Toast.LENGTH_LONG).show();
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


}
