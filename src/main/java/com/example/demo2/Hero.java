package com.example.demo2;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Hero extends ImageView {
    private double xCoord;
    private double yCoord;

    public HeroFlipState getHeroFlipState() {
        return heroFlipState;
    }

    public void setHeroFlipState(HeroFlipState heroFlipState) {
        this.heroFlipState = heroFlipState;
    }

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

    private void moveBackAndResetPillars(double height, Stick stick, Rectangle pillar, Rectangle nextPillar, SceneController sc, Group grp){

        Hero hero = this;

        TranslateTransition moveBack = new TranslateTransition(Duration.millis(500), this);
        moveBack.setByX(-height);
        moveBack.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sc.resetPillars(grp);
                sc.setGameState(GameState.HERO_IDLE);

                if(hero.getHeroFlipState() == HeroFlipState.FLIPPED)
                    hero.flip();

                // stick stuff
                stick.setHeight(10);stick.setWidth(10);
                stick.setX(180);stick.setY(600);
                Rotate rotation = new Rotate();
                rotation.setPivotX(180);
                rotation.setPivotY(600);
                stick.getTransforms().add(rotation);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                        new KeyFrame(Duration.millis(50), new KeyValue(rotation.angleProperty(), -90)));
                timeline.play();

            }
        });
        moveBack.play();


        // TODO: fix
        TranslateTransition moveNextPillar = new TranslateTransition(Duration.millis(500), nextPillar);
        moveNextPillar.setByX(-480);
        moveNextPillar.play();

        //
        TranslateTransition movePillar = new TranslateTransition(Duration.millis(500), pillar);
        movePillar.setByX(-height);
        movePillar.play();


    }


    public void move(double height, Stick stick, Rectangle pillar, Rectangle nextPillar, SceneController sc, Group grp) {
        sc.setGameState(GameState.HERO_MOVING);
        Hero hero = this;
        TranslateTransition moveTransition = new TranslateTransition(Duration.millis(500), this);
        moveTransition.setByX(height);
        moveTransition.play();
        moveTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sc.setGameState(GameState.ANIMATION);
                moveBackAndResetPillars(height, stick, pillar, nextPillar, sc, grp);
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