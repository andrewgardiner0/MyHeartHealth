package com.example.andrewgardiner.myhearthealth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class graphviews extends AppCompatActivity {

    private RadioButton sys;
    private RadioButton dia;
    private RadioButton we;
    private RadioButton bl;
    private RadioButton bpm;
    private DatabaseManager retriever;
    java.util.List<Profile> profiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sys = (RadioButton) findViewById(R.id.sysr);
        dia  = (RadioButton) findViewById(R.id.diar);
        we   = (RadioButton) findViewById(R.id.wer);
        bpm =   (RadioButton) findViewById(R.id.BPMR);
        bl = (RadioButton) findViewById(R.id.bcr);
        retriever = new DatabaseManager(this);
         profiles = retriever.allData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<Integer> val = new ArrayList<>();
        switch (view.getId()) {
            case  R.id.sysr:
                for(Profile prof : profiles){
                    id.add((int)(long)prof.getId());
                    val.add(prof.getSystalicBP());
                }
                setGraph(id, val);
                id.clear();
                val.clear();
            case R.id.diar:
                for(Profile prof : profiles){
                    id.add((int)(long)prof.getId());
                    val.add(prof.getDistolicBP());
                }
                setGraph(id, val);
                id.clear();
                val.clear();
            case R.id.wer:
                for(Profile prof : profiles){
                    id.add((int)(long)prof.getId());
                    val.add(prof.getWeight());
                }
                setGraph(id, val);
                id.clear();
                val.clear();
            case R.id.bcr:
                for(Profile prof : profiles){
                    id.add((int)(long)prof.getId());
                    val.add(prof.getBlood_glucose());
                }
                setGraph(id, val);
                id.clear();
                val.clear();
            case R.id.BPMR:
                for(Profile prof : profiles){
                    id.add((int)(long)prof.getId());
                    val.add(prof.getBpm());
                }
                setGraph(id, val);
                id.clear();
                val.clear();


        }
        }



    public void setGraph(ArrayList<Integer> id, ArrayList<Integer> val){

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dp = new DataPoint[10];
       // BarGraphSeries<DataPoint> series;

        for(int i=0;i<10;i++){
            int x,y;
            x = id.get(i);
            y = val.get(i);
            DataPoint v = new DataPoint(x,y);
            dp[i] = v;

        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dp);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(5);

// draw values on top
       // series.setDrawValuesOnTop(true);
       // series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);



        graph.addSeries(series);

    }

}
