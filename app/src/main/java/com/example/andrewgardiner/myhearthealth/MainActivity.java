package com.example.andrewgardiner.myhearthealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnvitals;
    private Button btnqs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnvitals = (Button) findViewById(R.id.btnVitals);
        btnqs = (Button) findViewById(R.id.btnQuestions);
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
    }
}
