package com.example.andrewgardiner.myhearthealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnvitals;
    private Button btnqs;
    private Button btnlist;
    private Button btnGraphs;
    private DatabaseManager retriever;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the listview
        btnlist = (Button) findViewById(R.id.btnList);
        btnvitals = (Button) findViewById(R.id.btnVitals);
        btnqs = (Button) findViewById(R.id.btnQuestions);
        btnGraphs = (Button) findViewById(R.id.btngraphs);

        btnGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent activity = new Intent(v.getContext(),graphviews.class);
                startActivity(activity);
            }
        });
        btnvitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent activity = new Intent(v.getContext(), ConnectEnterActivity.class);
                startActivity(activity);

            }
        });
        btnqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent activity = new Intent(v.getContext(), QuestionsActivity.class);
                startActivity(activity);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent activoty = new Intent(v.getContext(), com.example.andrewgardiner.myhearthealth.List.class);
                startActivity(activoty);
            }
        });

    }

    public static int safeLongToInt( long longNumber )
    {
        if ( longNumber < Integer.MIN_VALUE || longNumber > Integer.MAX_VALUE )
        {
            throw new IllegalArgumentException( longNumber + " cannot be cast to int without changing its value." );
        }
        return (int) longNumber;
    }

}
