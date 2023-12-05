package com.example.demo2;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

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

    public void move(double height, Stick stick, Rectangle pillar, Rectangle nextPillar) {
        Hero hero = this;
        TranslateTransition moveTransition = new TranslateTransition(Duration.millis(500), this);
        moveTransition.setByX(height);
        moveTransition.play();
        moveTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TranslateTransition moveBack = new TranslateTransition(Duration.millis(500), hero);
                moveBack.setByX(-height);
                moveBack.play();
                TranslateTransition moveNextPillar = new TranslateTransition(Duration.millis(500), nextPillar);
                moveNextPillar.setByX(-480);
                moveNextPillar.play();
                stick.setHeight(10);stick.setWidth(10);
                stick.setX(180);stick.setY(600);
                TranslateTransition movePillar = new TranslateTransition(Duration.millis(500), pillar);
                movePillar.setByX(-height);
                movePillar.play();
                Rotate rotation = new Rotate();
                rotation.setPivotX(180);
                rotation.setPivotY(600);
                stick.getTransforms().add(rotation);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(rotation.angleProperty(), -90)));
                timeline.play();
            }
        });
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