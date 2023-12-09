package com.example.demo2;

import java.io.Serializable;

public class SaveData implements Serializable {
    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCherries() {
        return cherries;
    }

    public void setCherries(int cherries) {
        this.cherries = cherries;
    }

    private int maxScore = 0;
    private int currentScore = 0;

    private int cherries = 0;

    SaveData(int maxScore, int currentScore, int cherries){
        this.cherries = cherries;
        this.maxScore = maxScore;
        this.currentScore = currentScore;
    }

}
