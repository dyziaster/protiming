package com.example.darek.protiming;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darek on 2015-03-29.
 */
public class Controler {

    public void setResults(ResultsActivity results) {
        this.results = results;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    private MainActivity activity;
    private ResultsActivity results;
    private ResultsHolder resultsHolder;
    private BluetoothManager manager;
    private Timer timer;
    private List<Float> splits;
    private int numberOfGates = 1;
    private int sigCount = 1;
    private int numberOfRun ;


    public static Controler instance;

    public static Controler getInstance(){

        if(instance == null){
            instance = new Controler();
            return instance;
        }else
            return instance;
    }

    private Controler(){

        splits = new ArrayList<Float>();
        timer = Timer.getInstance();
        resultsHolder = ResultsHolder.getInstance();
        numberOfRun = 0;

    }


    public void sigRecived(){ // pobierz aktualny czas, wyslij go do results zeby go zapisal.

        float time = 0;

        if(sigCount<numberOfGates) {
            time = timer.getTime();
            splits.add(time);
            sigCount++;
        }
        else
            endOfRun(numberOfRun);

    }

    private void endOfRun(int numberOfRun){

        resultsHolder.addRow(numberOfRun,splits);
        numberOfRun++;
        splits.clear();

    }

   // public String getTime(){
        //return timer.getTime();
   // }


}
