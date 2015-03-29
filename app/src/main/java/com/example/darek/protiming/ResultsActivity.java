package com.example.darek.protiming;

import android.app.Activity;

/**
 * Created by Darek on 2015-03-29.
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class ResultsActivity extends Activity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;
    ResultsHolder resultsHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        createGroupList();
        createCollection();
        resultsHolder.getInstance();

        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final ExpandableAdapter expListAdapter = new ExpandableAdapter(this,  resultsHolder.getGroupList(), null);
        expListView.setAdapter(expListAdapter);
        //setGroupIndicatorToRight();

    }

    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Run 1");
    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] hpModels = {"0:12","0:55","1:22"};

        laptopCollection = new LinkedHashMap<String, List<String>>();

        for (String laptop : groupList) {
            if (laptop.equals("Run 1")) {
                loadChild(hpModels);
            }

            laptopCollection.put(laptop, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
                /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }
*/
}