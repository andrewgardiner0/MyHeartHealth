package com.example.andrewgardiner.myhearthealth;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class List extends AppCompatActivity {

    private DatabaseManager retriever;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    java.util.List<String> listDataHeader;
    HashMap<String, java.util.List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retriever = new DatabaseManager(this);
        expListView = (ExpandableListView) findViewById(R.id.listView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    /*
   * Preparing the list data
   */
    private void prepareListData() {
        java.util.List<Profile> profiles = retriever.allData();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, java.util.List<String>>();

        // Adding child data
        // listDataHeader.add("Top 250");
        // listDataHeader.add("Now Showing");
        //listDataHeader.add("Coming Soon..");

        int id = 0;
        for(Profile profile : profiles){
            java.util.List<String> attributes = new ArrayList<String>();
            listDataHeader.add(Integer.toString(id));
            attributes.add("ID : " + Long.toString(profile.getId()));
            attributes.add("Systolic Blood Pressure : " + Integer.toString(profile.getSystalicBP()));
            attributes.add("Diastolic Blood Pressure : " + Integer.toString(profile.getDistolicBP()));
            attributes.add("Weight (lbs): " + Integer.toString(profile.getWeight()));
            attributes.add("Blood Glucose : " + Integer.toString(profile.getBlood_glucose()));
            attributes.add("BPM : " + Integer.toString(profile.getBpm()));
            listDataChild.put(listDataHeader.get(id),attributes);

            id++;
        }




        // Adding child data
      /*  List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
      *///  listDataChild.put(listDataHeader.get(2), comingSoon);


    }

}
