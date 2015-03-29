package com.example.darek.protiming;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

/**
 * Created by Darek on 2015-03-26.
 */
public class Parser {

    private MainActivity activity;

    public Parser(MainActivity a){
        activity = a;
    }

    public void parse(String message){

        switch(message){

            case "a":
                Log.i("Parser", message);
              //  activity.startTimer();
                break;
            case "b":
                Log.i("Parser", message);
              //  activity.getTime();
                break;
        }

    }

}
