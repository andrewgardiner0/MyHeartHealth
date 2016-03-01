package com.example.andrewgardiner.myhearthealth;

/**
 * Created by Andrew gardiner on 26/02/2016.
 */
public class Drug {
private long id;
    private String drugName;
    private String drugStrength;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugStrength() {
        return drugStrength;
    }

    public void setDrugStrength(String drugStrength) {
        this.drugStrength = drugStrength;
    }
}
