package com.example.darek.protiming;

import android.os.Handler;
import android.os.SystemClock;

/**
 * Created by Darek on 2015-03-29.
 */
public class Timer extends Thread{

    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private float time;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private MainActivity a;

    public static Timer instance;

    public static Timer getInstance(){

        if(instance == null){
            instance = new Timer();
            return instance;
        }else
            return instance;
    }

    public void run() {

        timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

        updatedTime = timeSwapBuff + timeInMilliseconds;

        int secs = (int) (updatedTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (updatedTime % 1000);
        a.setText("" + mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds));

        time = ((float)mins/60 + secs + (float)milliseconds/1000);
        customHandler.postDelayed(this, 0);
    }

    public void startTimer(){
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(this, 0);
    }
    public void pauseTimer(){
            timeSwapBuff += timeInMilliseconds;
            customHandler.removeCallbacks(this);
    }


    public Float getTime() {
        return time;
    }

}
