package com.example.demo2;

import javafx.scene.text.Text;

import java.nio.channels.ShutdownChannelGroupException;

public class CherryCollectorThread extends Thread{

    Cherry cherry = null;
    Hero hero = null;
    int cherryScore;

    CherryCollectorThread(SceneController sc){
        this.cherry = sc.getCherry();
        this.hero = sc.getHeroImage();
        this.cherryScore = sc.getCherryScore();
    }

    @Override
    public void run() {
        if(hero.getHeroFlipState() == HeroFlipState.FLIPPED && hero.getX()+60 < cherry.getX() + 30 && hero.getX() + 60 > cherry.getX()){
            cherry.toBack();
            cherryScore++;
        }
    }
}
