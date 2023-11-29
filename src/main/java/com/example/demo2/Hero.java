package com.example.demo2;

import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Hero extends ImageView {
    private double xCoord;
    private double yCoord;

    private HeroFlipState heroFlipState = HeroFlipState.STRAIGHT;

    public void flip(){

        Translate flipTranslation = new Translate(0,this.getImage().getHeight());
        Rotate verticalFlipRotation = new Rotate(180, Rotate.X_AXIS);

        if(heroFlipState == HeroFlipState.STRAIGHT){
            heroFlipState = HeroFlipState.FLIPPED;
            // TODO: translate downwards
            this.getTransforms().addAll(flipTranslation, verticalFlipRotation);
        }else{
            // TODO: translate upwards
            heroFlipState = HeroFlipState.STRAIGHT;
            this.getTransforms().addAll(flipTranslation, verticalFlipRotation);
        }
    }

    public void move(){}
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