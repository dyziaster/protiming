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
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private Map<String,BluetoothDevice> map;

    @Override
    public void onCreate(Bundle savedInstanceState) { // must enable BT first
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        manager = BluetoothManager.getInstance();
        map = new HashMap<String,BluetoothDevice>();
        devices = new HashSet<BluetoothDevice>();
        if(!manager.bluetoothEnabled()){
            Log.i("SettingsActivity","bluetooth turned off");
            return;
        }

        discoverButton = (Button)findViewById(R.id.discoverButton);
        discoverButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                try{
                discover();
                }catch ( Exception e) {
                    Log.i("","discover error");
                }

              //  Toast.makeText(getApplicationContext(), "discovering", Toast.LENGTH_LONG).show();
            }
        });

        listView = (ListView) findViewById(R.id.listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String  itemValue    = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Position :"+position+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
                manager.runConnectThread(map.get(itemValue));
            }




        });

        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
    }

    private void discover(){

        if (manager.isDiscovering()) {
            manager.cancelDiscovery();
        }
        devices.clear();
        manager.startDiscovery();

    }
    private void populateList(){
        for(BluetoothDevice d : devices){
            map.put(d.getName(),d);
        }
        String[] keySet = map.keySet().toArray(new String[map.keySet().size()]);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,keySet);
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {



        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
                Toast.makeText(getApplicationContext(), "adding device", Toast.LENGTH_SHORT).show();
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                populateList();
                Toast.makeText(getApplicationContext(), "finished discovery", Toast.LENGTH_LONG).show();
            }
        }
    };


}
