package com.example.darek.protiming;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Darek on 2015-03-29.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> laptopCollections;
    private List<String> laptops;

    public ExpandableAdapter(Activity context, List<String> laptops,Map<String, Set<String>> laptopCollections) {
        this.context = context;
        this.laptopCollections = laptopCollections;
        this.laptops = laptops;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.laptop);

       /* ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<String> child =
                                        laptopCollections.get(laptops.get(groupPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });*/

        item.setText(laptop);
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return laptops.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return laptops.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}