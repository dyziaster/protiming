package com.example.darek.protiming;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Darek on 2015-03-21.
 */
public class SettingsActivity extends Activity{
    private Button discoverButton;
    private BluetoothManager manager;
    private Set<BluetoothDevice> devices;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    private String[] tmp = new String[]{"awdawda","awdwada"};

    @Override
    public void onCreate(Bundle savedInstanceState) { // must enable BT first
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        manager = BluetoothManager.getInstance();
        /*
        if(!manager.bluetoothEnabled()){
            Log.i("SettingsActivity","bluetooth turned off");
            return;
        }
        */
        discoverButton = (Button)findViewById(R.id.discoverButton);
        discoverButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //devices = manager.getBondedDevices();
                populateList(); // need to implement return from BT devices>tostring>toarrayadapter.
                Toast.makeText(getApplicationContext(), "discovering", Toast.LENGTH_LONG).show();
            }
        });

        listView = (ListView) findViewById(R.id.listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
            }
        });

        //listView.setOnItemClickListener();
    }

    private void populateList(){


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tmp);
        listView.setAdapter(arrayAdapter);

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
