package com.example.andrewgardiner.myhearthealth;

/**
 * Created by Andrew gardiner on 01/03/2016.
 */
public class Profile {
    private long id;
    private int bpm;
    private int SystalicBP;
    private int DistolicBP;
    private int blood_glucose;
    private int weight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getSystalicBP() {
        return SystalicBP;
    }

    public void setSystalicBP(int systalicBP) {
        SystalicBP = systalicBP;
    }

    public int getDistolicBP() {
        return DistolicBP;
    }

    public void setDistolicBP(int distolicBP) {
        DistolicBP = distolicBP;
    }

    public int getBlood_glucose() {
        return blood_glucose;
    }

    public void setBlood_glucose(int blood_glucose) {
        this.blood_glucose = blood_glucose;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
