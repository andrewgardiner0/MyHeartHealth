package com.example.andrewgardiner.myhearthealth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.HashMap;

public class QuestionsActivity extends AppCompatActivity {

    private RadioButton q1yes;
    private RadioButton q1no;
    private RadioButton q2yes;
    private RadioButton q2no;
    private RadioButton q3yes;
    private RadioButton q3no;
    private RadioButton q4yes;
    private RadioButton q4no;
    private RadioButton q5yes;
    private RadioButton q5no;
    private RadioButton q6yes;
    private RadioButton q6no;
    private HashMap<Integer,Boolean> answers = new HashMap<Integer, Boolean>();
    private Button submit;
    boolean q1 = false;
    boolean q2 = false;
    boolean q3 = false;
    boolean q4 = false;
    boolean q5 = false;
    boolean q6 = false;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        q1no = (RadioButton)findViewById(R.id.q1no);
        q2yes = (RadioButton)findViewById(R.id.Q1yes);
        q2yes = (RadioButton)findViewById(R.id.q2yes);
        q2no = (RadioButton)findViewById(R.id.q2no);
        q3yes = (RadioButton)findViewById(R.id.q3yes);
        q3no = (RadioButton)findViewById(R.id.q3no);
        q4yes = (RadioButton)findViewById(R.id.q4yes);
        q4no = (RadioButton)findViewById(R.id.q4no);
        q5yes = (RadioButton)findViewById(R.id.q5yes);
        q5no = (RadioButton)findViewById(R.id.q5no);
        q6yes = (RadioButton)findViewById(R.id.q6yes);
        q6no = (RadioButton)findViewById(R.id.q6no);
        submit = (Button)findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            analyse();
            }
        });



    }
    public void analyse(){
        AnalyseQuestions analyse = new AnalyseQuestions();
        answers.put(1,q1);
        answers.put(2,q2);
        answers.put(3,q3);
        answers.put(4,q4);
        answers.put(5,q5);
        answers.put(6,q6);
        int score = analyse.result(answers);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(score > 5) {
            builder.setMessage(R.string.emergency);
        }

           else if(score < 5){
                builder.setMessage(R.string.come_back);

            }


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case  R.id.Q1yes:
                if(checked) {
                    q1 = true;

                }
                else {
                    q1 = false;

                }
            case R.id.q2yes:
                if(checked) {
                    q2 = true;

                }
                    else {
                    q2 = false;

                }

            case R.id.q3yes:
                if(checked) {
                    q3 = true;

                }
                else {
                    q3 = false;

                }
            case R.id.q4yes:
                if(checked) {
                    q4 = true;

                }
                else {
                    q4 = false;

                }
            case R.id.q5yes:
                if(checked) {
                    q5 = true;

                }
                else {
                    q5 = false;

                }
            case R.id.q6yes:
                if(checked) {
                    q6 = true;

                }
                else {
                    q6 = false;

                }


        }


    }
}
