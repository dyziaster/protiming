package com.example.darek.protiming;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Darek on 2015-03-29.
 */
public class ResultsHolder {


    public static ResultsHolder instance;

    public static ResultsHolder getInstance(){

        if(instance == null){
            instance = new ResultsHolder();
            return instance;
        }else
            return instance;
    }

    private ResultsHolder(){


    }

    private Map<String,List<String>> results;


    public void addRow(Integer run, List<Float> splits){

        List<String> list = new ArrayList<>();

        for(Float x : splits){
            list.add(x.toString());
        }

        results.put(run.toString(),list);

    }


    public List<String> getGroupList(){
        return results.keySet();
    }



}
