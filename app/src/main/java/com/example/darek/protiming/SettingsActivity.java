package com.example.darek.protiming;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Darek on 2015-03-21.
 */
public class SettingsActivity extends Activity{
    private BluetoothManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) { // must enable BT first
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        manager = BluetoothManager.getInstance();


        NumberPicker np = (NumberPicker)findViewById(R.id.number_picker);
        np.setMinValue(1);// restricted number to minimum value i.e 1
        np.setMaxValue(11);// restricked number to maximum value i.e. 31
        np.setWrapSelectorWheel(true);
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
