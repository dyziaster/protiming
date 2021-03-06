package com.example.darek.protiming;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Darek on 2015-03-23.
 */
public class BluetoothManager { // added comment from przymorze

    // ocmment from master
    private BluetoothAdapter adapter;

    public Set<BluetoothDevice> getDevices() {
        return devices;
    }

    private Set<BluetoothDevice> devices;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private static BluetoothManager instance;



    public static BluetoothManager getInstance(){

        if(instance == null){
            instance = new BluetoothManager();
            return instance;
        }else
            return instance;
    }

    private BluetoothManager(){

        adapter = BluetoothAdapter.getDefaultAdapter();
        devices = new HashSet<BluetoothDevice>();
        if(adapter == null){
            Log.i("BluetoothManager","adapter is null");
        }

    }

    public boolean bluetoothEnabled(){
        return adapter.isEnabled();
    }


    public Set<BluetoothDevice> getBondedDevices(){
        return adapter.getBondedDevices();
    }

    public void runConnectedThread(){
        connectedThread.run();

    }

    private void manageConnectedSocket(BluetoothSocket socket){
        connectedThread = new ConnectedThread(socket);

    }

    public void runConnectThread(BluetoothDevice d) {

        new ConnectThread(d).run();

    }

    public void startDiscovery() {
        adapter.startDiscovery();
    }

    public boolean isDiscovering() {
        return adapter.isDiscovering();
    }

    public void cancelDiscovery() {
        adapter.cancelDiscovery();
    }

    public void add(BluetoothDevice device) {
        devices.add(device);
    }

    public void clearDevices() {
        devices.clear();
    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) { Log.i("",e.toString()); }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            adapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
           // manageConnectedSocket(mmSocket);
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }


    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    //mHandler.obtainMessage(0, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}
