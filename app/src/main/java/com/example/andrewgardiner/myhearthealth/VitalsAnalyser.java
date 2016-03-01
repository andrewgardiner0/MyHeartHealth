package com.example.andrewgardiner.myhearthealth;

import java.util.ArrayList;

/**
 * Created by Andrew gardiner on 01/03/2016.
 */
public class VitalsAnalyser {
    private final static int BPM_NORM_LOWER = 60;
    private final static int BPM_NORM_UPPER = 79;
    private final static int BPM_MED_RISK_BELOW_AVG_LOWER = 50;
    private final static int BPM_MED_RISK_BELOW_AVG_UPPER = 59;
    private final static int BPM_MED_RISK_ABOVE_AVG_LOWER = 80;
    private final static int BPM_MED_RISK_ABOVE_AVG_UPPER = 99;
    private final static int BPM_HIGH_RISK_BELOW_AVG = 49;
    private final static int BPM_HIGH_RISK_ABOVE_AVG = 100;
    private final static int SYS_NORM_LOWER = 90;
    private final static int SYS_NORM_UPPER = 129;
    private final static int SYS_MED_RISK_BELOW_AVG_LOWER = 80;
    private final static int SYS_MED_RISK_BELOW_AVG_UPPER = 89;
    private final static int SYS_MED_RISK_ABOVE_AVG_LOWER = 130;
    private final static int SYS_MED_RISK_ABOVE_AVG_UPPER = 139;
    private final static int SYS_HIGH_RISK_BELOW_AVG = 79;
    private final static int SYS_HIGH_RISK_ABOVE_AVG = 140;
    private final static int DIA_NORM_LOWER = 60;
    private final static int DIA_NORM_UPPER = 79;
    private final static int DIA_MED_RISK_BELOW_AVG_LOWER = 50;
    private final static int DIA_MED_RISK_BELOW_AVG_UPPER = 59;
    private final static int DIA_MED_RISK_ABOVE_AVG_LOWER = 80;
    private final static int DIA_MED_RISK_ABOVE_AVG_UPPER = 89;
    private final static int DIA_HIGH_RISK_BELOW_AVG = 49;
    private final static int DIA_HIGH_RISK_ABOVE_AVG = 90;
    private final static int BL_NORM_LOWER = 60;
    private final static int BL_NORM_UPPER = 200;
    private final static int BL_MED_RISK_BELOW_AVG_LOWER = 50;
    private final static int BL_MED_RISK_BELOW_AVG_UPPER = 60;
    private final static int BL_MED_RISK_ABOVE_AVG_LOWER = 200;
    private final static int BL_MED_RISK_ABOVE_AVG_UPPER = 240;
    private final static int BL_HIGH_RISK_BELOW_AVG = 50;
    private final static int BL_HIGH_RISK_ABOVE_AVG = 250;


    private final static int CLASSIFICATON_LOW_RISK = 1;
    private final static int CLASSIFICATION_MED_RISK = 2;
    private final static int CLASSIFICATION_HIGH_RISK = 3;


    public int analyse(int bpmval, int sysval, int diaval, int weightval,int prevWeight, int blval){
        ArrayList<Integer> nums =  new ArrayList<Integer>();
        int bpm = analyseBPM(bpmval);
        int sys = analyseSYS(sysval);
        int dia = analyseDIA(diaval);
        int weight = analyseWeight(weightval,prevWeight);
        int bl = analyseBL(blval);
        nums.add(0,bpm);
        nums.add(1,sys);
        nums.add(2,dia);
        nums.add(3,weight);
        nums.add(4,bl);
        int one =0;
        int two=0;
        int three=0;
        for(int val : nums){
            if(val == 1)
                one++;
            else if(val == 2)
                two++;
            else if(val == 3)
                three++;
        }

        if(one > two & one > three){
            return 1;
        }else if(two > one & two > three)
            return 2;
        else if(three > one  & three> two)
            return 3;
        else return 1;




    }

    public int analyseBPM(int bpm){

        System.out.println(bpm);
        if(bpm >= BPM_NORM_LOWER & bpm <=BPM_NORM_UPPER) {
            return 1;
        }
        else if(bpm >= BPM_MED_RISK_BELOW_AVG_LOWER & bpm <= BPM_MED_RISK_BELOW_AVG_UPPER){
            return 2;
        }
        else if(bpm >= BPM_MED_RISK_BELOW_AVG_LOWER & bpm <= BPM_MED_RISK_BELOW_AVG_UPPER){
            return 2;
        }
        else if(bpm >= BPM_MED_RISK_ABOVE_AVG_LOWER & bpm <= BPM_MED_RISK_ABOVE_AVG_UPPER){
            return 2;
        }
        else if(bpm <= BPM_HIGH_RISK_BELOW_AVG || bpm >= BPM_HIGH_RISK_ABOVE_AVG){
            return 3;
        }
        return 1;
    }
    public int analyseSYS(int sys){
        System.out.println(sys);
        if(sys >= SYS_NORM_LOWER & sys <= SYS_NORM_UPPER){
            return 1;
        }else if(sys >= SYS_MED_RISK_BELOW_AVG_LOWER & sys <= SYS_MED_RISK_BELOW_AVG_UPPER){
            return 2;
        }else if(sys >= SYS_MED_RISK_ABOVE_AVG_LOWER & sys <= SYS_MED_RISK_ABOVE_AVG_UPPER){
            return 2;
        }else if(sys <= SYS_HIGH_RISK_BELOW_AVG || sys >= SYS_HIGH_RISK_ABOVE_AVG){
            return 3;
        }

        return 1;
    }
    public int analyseDIA(int dia){
        System.out.println(dia);
        if(dia >=DIA_NORM_LOWER & dia <= DIA_NORM_UPPER ){
            return 1;
        }
        else if( dia >= DIA_MED_RISK_BELOW_AVG_LOWER & dia <= DIA_MED_RISK_BELOW_AVG_UPPER){
            return 2;
        }else if(dia >= DIA_MED_RISK_ABOVE_AVG_LOWER & dia <= DIA_MED_RISK_ABOVE_AVG_UPPER){
            return 2;
        }else if(dia <= DIA_HIGH_RISK_BELOW_AVG || dia >= DIA_HIGH_RISK_ABOVE_AVG){
            return 3;
        }

        return 1;
    }

    public int analyseWeight(int weight, int prevWeight){

        int difference = prevWeight - weight;
        System.out.println(difference +  " Difference");
        if(difference >= -1 & difference <= 1){
            return 1;
        }
        else if(difference >= -1.5 & difference <= -1){
            return 2;
        }
        else if(difference >= 1 & difference <= 1.5){
            return 2;
        }
        else if(difference < -1.5 || difference > 1.5){
            return 3;
        }

        return 1;

    }
    public int analyseBL(int bl){
        System.out.println(bl);
        if(bl >= BL_NORM_LOWER & bl <= BL_NORM_UPPER){
            return 1;
        }else if(bl >= BL_MED_RISK_BELOW_AVG_LOWER & bl <= BL_MED_RISK_BELOW_AVG_UPPER){
            return 2;
        }else if(bl >= BL_MED_RISK_ABOVE_AVG_LOWER & bl <= BL_MED_RISK_ABOVE_AVG_UPPER){
            return 2;
        }else if(bl <= BL_HIGH_RISK_BELOW_AVG || bl >= BL_HIGH_RISK_ABOVE_AVG)
            return 3;

        return 1;
    }
}
