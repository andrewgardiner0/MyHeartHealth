package com.example.andrewgardiner.myhearthealth;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew gardiner on 24/02/2016.
 */
public class AnalyseQuestions {
    private ArrayList<Boolean> answers;


    public int result(HashMap<Integer,Boolean> answers){
        int score=0;
        for (int key : answers.keySet()) {
            boolean answer = answers.get(key);

            if(answer && key == 1)
                score =+ 5;
            else if(answer)
                    score++;
        }

        return score;

    }


}
