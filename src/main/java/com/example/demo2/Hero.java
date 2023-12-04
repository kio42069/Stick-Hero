package com.example.demo2;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Hero extends ImageView {
    private double xCoord;
    private double yCoord;

    private HeroFlipState heroFlipState = HeroFlipState.STRAIGHT;

    public void flip(){

        if(heroFlipState == HeroFlipState.STRAIGHT){
            heroFlipState = HeroFlipState.FLIPPED;
            this.setY(540);
            this.setScaleY(-1);
            this.getTransforms().add(new Translate(0, -60));
        }else{
            this.setY(540);
            heroFlipState = HeroFlipState.STRAIGHT;
            this.setScaleY(1);
            this.getTransforms().add(new Translate(0, 60));
        }
    }

    public void move(){
        TranslateTransition moveTransition = new TranslateTransition(Duration.millis(500), this);
        moveTransition.setByX(100);
        moveTransition.play();
    }
    public void fall(){}
    public void switchSides(){}
    public void grabApple(){}
    public void createStick(){}
    public void increaseScore(){}
    public void detectCollision(){}
    public void detectIncorrectStickSize(){}

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }
}